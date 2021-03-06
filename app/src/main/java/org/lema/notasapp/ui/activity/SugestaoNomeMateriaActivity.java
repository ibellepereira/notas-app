package org.lema.notasapp.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.greenrobot.eventbus.Subscribe;
import org.lema.notasapp.R;
import org.lema.notasapp.domain.dao.AlunoDao;
import org.lema.notasapp.domain.model.*;
import org.lema.notasapp.domain.service.SugestaoService;
import org.lema.notasapp.infra.app.NotasAppAplication;
import org.lema.notasapp.infra.dagger.component.BoletimComponent;
import org.lema.notasapp.infra.error.APIError;
import org.lema.notasapp.infra.event.APIErrorEvent;
import org.lema.notasapp.infra.event.ThrowableEvent;
import org.lema.notasapp.infra.listener.OnRetryListener;
import org.lema.notasapp.infra.listener.OnCancelListener;
import org.lema.notasapp.infra.oauth2.model.AccessToken;
import org.lema.notasapp.infra.retrofit.callback.OAuthCallback;
import org.lema.notasapp.ui.utils.DialogMessage;
import org.lema.notasapp.ui.utils.DialogUtils;
import retrofit2.Call;
import retrofit2.Response;

import javax.inject.Inject;

/**
 * Created by leonardocordeiro on 16/12/16.
 */
public class SugestaoNomeMateriaActivity extends OAuthActivity {
    private MateriaDto materiaDto;
    private Materia materia;
    private TextView mNomeMateria;
    private String mNomeSugerido;

    AlunoDao alunos = new AlunoDao(this);

    @Inject
    SugestaoService sugestaoService;

    AlunoDto aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carregando_fragment);

        inject();

        this.materiaDto = (MateriaDto) getIntent().getSerializableExtra("materia");
        this.mNomeSugerido = (String) getIntent().getSerializableExtra("nomeSugerido");

        mapeiaAluno();
        mapeiaMateria();

        sugerir();
        //mNomeMateria = (TextView) findViewById(R.id.sugestao_nome);
        //mNomeMateria.setText(materiaDto.getNome());

        //mNomeSugerido = (EditText) findViewById(R.id.sugestao_nome_sugerido);

       // Button botaoSugerir = (Button) findViewById(R.id.btn_enviar_sugestao);
        //botaoSugerir.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        sugerir();
        //    }
        //});

    }

    private void mapeiaMateria() {
        Materia materia = new Materia();
        materia.setNome(materiaDto.getNome());
        materia.setAno(materiaDto.getAno());
        materia.setCodigo(materiaDto.getCodigo());
        this.materia = materia;
    }

    private void mapeiaAluno() {
        AlunoDto alunoDto = new AlunoDto();

        Aluno aluno = alunos.obterAlunoDoLogin();

        alunoDto.setNome(aluno.getNome());
        alunoDto.setMatricula(aluno.getMatricula());

        this.aluno = alunoDto;
    }

    private void inject() {
        NotasAppAplication app = (NotasAppAplication) getApplication();
        BoletimComponent component = app.getComponent();
        component.inject(this);
    }

    @Subscribe
    public void onReceiveAccessToken(AccessToken accessToken) {
        sugerir();
    }

    public void sugerir() {

        //String nomeSugerido = mNomeSugerido.getText().toString();

        SugestaoDeNomeDeMateria sugestao = new SugestaoDeNomeDeMateria(materia, mNomeSugerido);
        sugestao.setAluno(aluno);

        sugestaoService.enviar(sugestao).enqueue(new OAuthCallback<SugestaoDeNomeDeMateria>() {
            @Override
            public void handle(Call<SugestaoDeNomeDeMateria> call, Response<SugestaoDeNomeDeMateria> response) {
                mostrarConfirmacao();
                //Toast.makeText(SugestaoNomeMateriaActivity.this, "Sugestão enviada com sucesso!", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Subscribe
    public void handle(ThrowableEvent event) {
        new DialogUtils(this).showCancel(new DialogMessage(event.exception.getMessage(), new OnRetryListener() {
            @Override
            public void onRetry() {
                sugerir();
            }
        }, new OnCancelListener() {
            @Override
            public void onCancel() {
                finish();
            }
        }));
    }

    @Subscribe
    public void handle(APIErrorEvent event) {
        new DialogUtils(this).showCancel(new DialogMessage("Falha ao tentar enviar sugestão.", new OnRetryListener() {
            @Override
            public void onRetry() {
                sugerir();
            }
        }, new OnCancelListener() {
            @Override
            public void onCancel() {
                finish();
            }
        }));
    }

    private Aluno obterAlunoLogado() {
        return alunos.obterAlunoDoLogin();
    }



    public void mostrarConfirmacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_materia_response_message_sucess)
                .setTitle(R.string.dialog_materia_response_title_sucess)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        finish();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

package org.lema.notasapp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.greenrobot.eventbus.Subscribe;
import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.Materia;
import org.lema.notasapp.domain.model.Sugestao;
import org.lema.notasapp.domain.model.factory.SugestaoFactory;
import org.lema.notasapp.domain.service.SugestaoService;
import org.lema.notasapp.infra.app.NotasAppAplication;
import org.lema.notasapp.infra.dagger.component.BoletimComponent;
import org.lema.notasapp.infra.event.APIErrorEvent;
import org.lema.notasapp.infra.event.ThrowableEvent;
import org.lema.notasapp.infra.listener.OnRetryListener;
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
    private Materia materia;
    private TextView mNomeMateria;
    private EditText mNomeSugerido;

    @Inject
    SugestaoService sugestaoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sugestao_nome_materia_activity);

        inject();

        this.materia = (Materia) getIntent().getSerializableExtra("materia");

        mNomeMateria = (TextView) findViewById(R.id.sugestao_nome);
        mNomeMateria.setText(materia.getNome());

        mNomeSugerido = (EditText) findViewById(R.id.sugestao_nome_sugerido);
        Button botaoSugerir = (Button) findViewById(R.id.btn_enviar_sugestao);
        botaoSugerir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sugerir();
            }
        });

    }

    private void inject() {
        NotasAppAplication app = (NotasAppAplication) getApplication();
        BoletimComponent component = app.getComponent();
        component.inject(this);
    }

    @Override
    public void onReceiveAccessToken(AccessToken accessToken) {
        sugerir();
    }

    public void sugerir() {
        String nomeSugerido = mNomeSugerido.getText().toString();
        Sugestao sugestao = SugestaoFactory.criarSugestaoDeNomeParaMateria(materia, nomeSugerido);

        sugestaoService.enviar(sugestao).enqueue(new OAuthCallback<Sugestao>() {
            @Override
            public void handle(Call<Sugestao> call, Response<Sugestao> response) {
                Toast.makeText(SugestaoNomeMateriaActivity.this, "Sugestão enviada com sucesso!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Subscribe
    public void handle(ThrowableEvent event) {
        new DialogUtils(this).show(new DialogMessage(event.exception.getMessage(), new OnRetryListener() {
            @Override
            public void onRetry() {
                sugerir();
            }
        }));
    }

    @Subscribe
    public void handle(APIErrorEvent event) {
        new DialogUtils(this).show(new DialogMessage("Falha ao tentar enviar sugestão. Tente novamente!", new OnRetryListener() {
            @Override
            public void onRetry() {
                sugerir();
            }
        }));
    }
}

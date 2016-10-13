package org.lema.notasapp.activity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.lema.notasapp.R;
import org.lema.notasapp.adapter.MateriasAdapter;
import org.lema.notasapp.dao.AlunoDao;
import org.lema.notasapp.domain.service.BoletimService;
import org.lema.notasapp.fragment.CarregandoFragment;
import org.lema.notasapp.fragment.NotasFragment;
import org.lema.notasapp.modelo.Aluno;
import org.lema.notasapp.modelo.Boletim;
import org.lema.notasapp.modelo.Materia;

import java.util.ArrayList;
import java.util.List;

import br.com.mytho.mobi.activity.BaseActivity;
import br.com.mytho.mobi.activity.OAuthActivity;
import br.com.mytho.mobi.activity.utils.DialogUtils;
import br.com.mytho.mobi.exception.ConnectionErrorException;
import br.com.mytho.mobi.exception.HTTPUnauthorizedException;
import br.com.mytho.mobi.exception.UnavailableException;
import br.com.mytho.mobi.infra.retrofit.DefaultServiceCallback;
import br.com.mytho.mobi.oauth2.client.OAuth2AccessTokenClient;
import br.com.mytho.mobi.oauth2.model.AccessToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BoletimActivity extends OAuthActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notas_activity);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.boletim, new CarregandoFragment());
        tx.commit();

        buscaBoletim();
    }

    private Aluno getAluno() {
        return (Aluno) getIntent().getSerializableExtra("aluno");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas, menu);
        return true;
    }

    public void colocarNaTela(Boletim boletim) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();

        Bundle arguments = new Bundle();

        ArrayList<Materia> materias = (ArrayList<Materia>) boletim.getMaterias();
        arguments.putParcelableArrayList("materias", materias);

        NotasFragment notasFragment = new NotasFragment();
        notasFragment.setArguments(arguments);

        tx.replace(R.id.boletim, notasFragment);
        tx.commit();
    }

    public void buscaBoletim() {
        BoletimService.BoletimServiceContract boletimService = (BoletimService.BoletimServiceContract) new BoletimService().context(this).clazz(BoletimService.BoletimServiceContract.class).build();
        boletimService.getBoletim(getAluno()).enqueue(new DefaultServiceCallback<Boletim>(new DefaultServiceCallback.OnResponse<Boletim>() {
            @Override
            public void onResponse(Call<Boletim> call, Response<Boletim> response) {
                colocarNaTela(response.body());
            }
        }));
    }

    private void salvarCredenciais() {
        AlunoDao alunoDao = new AlunoDao(this);
        alunoDao.salvar(getAluno());
    }

    private boolean entrarAutomaticamente() {
        return getIntent().getBooleanExtra("entrar-automaticamente", false);
    }

    @Subscribe
    public void handle(UnavailableException exception) {
        buscaBoletim();
    }

    @Subscribe
    public void handle(ConnectionErrorException exception) {
        dialogUtils.showConnectionError(new DialogUtils.OnRetryListener() {
            @Override
            public void onRetry() {
                buscaBoletim();
            }
        });
    }

    @Subscribe
    public void onReceiveAccessToken(AccessToken accessToken) {
        buscaBoletim();
    }

}
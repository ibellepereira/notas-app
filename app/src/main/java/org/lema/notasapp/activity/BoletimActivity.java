package org.lema.notasapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import org.greenrobot.eventbus.Subscribe;
import org.lema.notasapp.R;
import org.lema.notasapp.adapter.BoletimAdapter;
import org.lema.notasapp.domain.service.BoletimService;
import org.lema.notasapp.modelo.Aluno;
import org.lema.notasapp.modelo.Boletim;
import org.lema.notasapp.modelo.Materia;

import java.util.ArrayList;

import br.com.mytho.mobi.activity.OAuthActivity;
import br.com.mytho.mobi.activity.utils.DialogUtils;
import br.com.mytho.mobi.exception.ConnectionErrorException;
import br.com.mytho.mobi.exception.UnavailableException;
import br.com.mytho.mobi.infra.retrofit.DefaultServiceCallback;
import br.com.mytho.mobi.oauth2.model.AccessToken;
import retrofit2.Call;
import retrofit2.Response;


public class BoletimActivity extends OAuthActivity {

    private RecyclerView recyclerViewBoletim;
    private ArrayList<Materia> materias;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletim);

        carregarPreferencias();

        preencheReferencias();

        buscaBoletim();


    }
    private void carregarPreferencias() {

        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_user_key), Context.MODE_PRIVATE);

        String matricula = sharedPreferences.getString(getString(R.string.preference_matricula), "");
        String senha = sharedPreferences.getString(getString(R.string.preference_password), "");

        aluno = new Aluno(matricula, senha);
    }

    private void preencheReferencias(){
        recyclerViewBoletim = (RecyclerView) findViewById(R.id.lv_materias);
        materias = new ArrayList<>();
    }

    public void buscaBoletim() {
        BoletimService.BoletimServiceContract boletimService = (BoletimService.BoletimServiceContract) new BoletimService().context(this).clazz(BoletimService.BoletimServiceContract.class).build();
        boletimService.getBoletim(aluno).enqueue(new DefaultServiceCallback<Boletim>(new DefaultServiceCallback.OnResponse<Boletim>() {
            @Override
            public void onResponse(Call<Boletim> call, Response<Boletim> response) {
                preencheLista(response.body());
            }
        }));
    }

    public void preencheLista(Boletim boletim) {

        materias = (ArrayList<Materia>) boletim.getMaterias();

        recyclerViewBoletim.setAdapter(new BoletimAdapter(this, materias));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewBoletim.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas, menu);
        return true;
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
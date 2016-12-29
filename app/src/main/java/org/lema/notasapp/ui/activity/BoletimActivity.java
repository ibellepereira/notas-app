package org.lema.notasapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.Subscribe;
import org.lema.notasapp.R;
import org.lema.notasapp.adapter.BoletimAdapter;
import org.lema.notasapp.domain.dao.AlunoDao;
import org.lema.notasapp.domain.model.Aluno;
import org.lema.notasapp.domain.model.Materia;
import org.lema.notasapp.domain.service.BoletimService;
import org.lema.notasapp.ui.fragment.LoadingFragment;
import org.lema.notasapp.infra.app.NotasAppAplication;
import org.lema.notasapp.infra.dagger.component.BoletimComponent;
import org.lema.notasapp.infra.event.APIErrorEvent;
import org.lema.notasapp.infra.event.BoletimEvent;
import org.lema.notasapp.infra.event.ThrowableEvent;
import org.lema.notasapp.infra.listener.OnRetryListener;
import org.lema.notasapp.infra.oauth2.model.AccessToken;
import org.lema.notasapp.infra.retrofit.callback.BoletimCallback;
import org.lema.notasapp.ui.utils.DialogMessage;

import javax.inject.Inject;
import java.util.ArrayList;


public class BoletimActivity extends OAuthActivity {

    private RecyclerView recyclerViewBoletim;
    private Toolbar mToolbar;
    private ArrayList<Materia> materias;
    private Aluno aluno;
    private AlunoDao alunoDao = new AlunoDao(this);


    private static String LOADING_FRAGMENT_TAG = "carregando";
    private LoadingFragment loadingFragment;

    @Inject
    BoletimService boletimService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletim);

        mostrarCarregando();

        preparaToolbar();

        carregaAluno();

        preencheReferencias();

        prepararInjecao();

        buscaBoletim();

    }

    private void prepararInjecao() {
        NotasAppAplication app = (NotasAppAplication) getApplication();
        BoletimComponent component = app.getComponent();
        component.inject(this);
    }

    private void preparaToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar_boletim);
        mToolbar.setTitle(R.string.activity_boletim_name);
        setSupportActionBar(mToolbar);
    }

    private void carregaAluno() {
        aluno = alunoDao.obterAlunoLogado();
    }

    private void preencheReferencias(){
        recyclerViewBoletim = (RecyclerView) findViewById(R.id.lv_materias);
        materias = new ArrayList<>();
    }

    public void buscaBoletim() {

        Log.i("erro", "buscando..." + aluno.getMatricula());
        boletimService.getBoletimDo(aluno).enqueue(new BoletimCallback());
    }

    public void mostrarCarregando() {
        loadingFragment = new LoadingFragment();
        getFragmentManager().beginTransaction().add(android.R.id.content, loadingFragment, LOADING_FRAGMENT_TAG).commit();
    }

    public void ocultarCarregando() {
        getFragmentManager().beginTransaction().remove(loadingFragment).commit();
    }

    @Subscribe
    public void preencheLista(BoletimEvent event) {
        ocultarCarregando();

        materias = (ArrayList<Materia>) event.boletim.getMaterias();
        aluno = event.boletim.getAluno();

        salvaAlunoCompleto(aluno);

        recyclerViewBoletim.setAdapter(new BoletimAdapter(this, materias));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewBoletim.setLayoutManager(layoutManager);
    }

    public void salvaAlunoCompleto(Aluno aluno) {
        alunoDao.salvarAluno(aluno);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Subscribe
    public void onReceiveAccessToken(AccessToken accessToken) {
        buscaBoletim();

    }

    @Subscribe
    public void handle(ThrowableEvent event) {
        Log.i("erro", event.exception.toString());
        dialogUtils.show(new DialogMessage(event.exception.getMessage(), new OnRetryListener() {
            @Override
            public void onRetry() {
                buscaBoletim();
            }
        }));
    }

    @Subscribe
    public void handle(APIErrorEvent event) {
        Log.i("erro", event.error.toString());
        dialogUtils.show(new DialogMessage(event.error.getMessage(), new OnRetryListener() {
            @Override
            public void onRetry() {
                finish();
            }
        }));

    }

}
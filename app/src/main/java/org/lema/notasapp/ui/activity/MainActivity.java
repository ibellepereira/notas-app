package org.lema.notasapp.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.Subscribe;
import org.lema.notasapp.R;
import org.lema.notasapp.domain.dao.AlunoDao;
import org.lema.notasapp.domain.model.Aluno;
import org.lema.notasapp.domain.model.MateriaDto;
import org.lema.notasapp.domain.service.BoletimService;
import org.lema.notasapp.infra.app.NotasAppAplication;
import org.lema.notasapp.infra.dagger.component.BoletimComponent;
import org.lema.notasapp.infra.event.APIErrorEvent;
import org.lema.notasapp.infra.event.BoletimEvent;
import org.lema.notasapp.infra.event.ThrowableEvent;
import org.lema.notasapp.infra.listener.OnRetryListener;
import org.lema.notasapp.infra.oauth2.model.AccessToken;
import org.lema.notasapp.infra.retrofit.callback.BoletimCallback;
import org.lema.notasapp.ui.adapter.ViewPagerAdapter;
import org.lema.notasapp.ui.fragment.BoletimFragment;
import org.lema.notasapp.ui.fragment.FeedFragment;
import org.lema.notasapp.ui.fragment.LoadingFragment;
import org.lema.notasapp.ui.utils.DialogMessage;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends OAuthActivity {

    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ArrayList<MateriaDto> materias;

    private Aluno aluno;
    private AlunoDao alunoDao = new AlunoDao(this);

    private static String LOADING_FRAGMENT_TAG = "carregando";
    private LoadingFragment loadingFragment;

    @Inject
    BoletimService boletimService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mostrarCarregando();

        preparaToolbar();

        carregaAluno();

        preencheReferencias();

        prepararInjecao();

        buscaBoletim();
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


    private void prepararInjecao() {
        NotasAppAplication app = (NotasAppAplication) getApplication();
        BoletimComponent component = app.getComponent();
        component.inject(this);
    }

    private void preparaToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void carregaAluno() {
        aluno = alunoDao.obterAlunoDoLogin();
    }

    private void preencheReferencias() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        materias = new ArrayList<>();
    }

    public void buscaBoletim() {

        Log.i("erro", "buscando..." + aluno.getMatricula());
        boletimService.getBoletimDo(aluno).enqueue(new BoletimCallback());
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putSerializable("materias", materias);

        BoletimFragment boletimFragment = new BoletimFragment();
        boletimFragment.setArguments(bundle);

        adapter.addFragment(new FeedFragment(), "Notícias");
        adapter.addFragment(boletimFragment, "Notas");
        viewPager.setAdapter(adapter);
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

        Aluno comNome = alunoDao.obterAlunoDoLogin();

        materias = (ArrayList<MateriaDto>) event.boletim.getMaterias();
        aluno = event.boletim.getAluno();

        salvaAlunoCompleto(aluno);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

    }

    public void salvaAlunoCompleto(Aluno aluno) {
        alunoDao.salvarAluno(aluno);
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


package org.lema.notasapp.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import org.greenrobot.eventbus.Subscribe;
import org.lema.notasapp.R;
import org.lema.notasapp.adapter.BoletimAdapter;
import org.lema.notasapp.domain.model.Aluno;
import org.lema.notasapp.domain.model.Materia;
import org.lema.notasapp.domain.service.BoletimService;
import org.lema.notasapp.infra.app.NotasAppAplication;
import org.lema.notasapp.infra.dagger.component.BoletimComponent;
import org.lema.notasapp.infra.error.APIError;
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

    @Inject
    BoletimService boletimService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletim);

        preparaToolbar();

        carregarPreferencias();

        preencheReferencias();

        preparaNavigationDrawer();

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
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private Aluno getAluno() {
        return (Aluno) getIntent().getSerializableExtra("aluno");
    }

    public void buscaBoletim() {
        Log.i("erro", "buscando..." + aluno.getMatricula());
        boletimService.getBoletimDo(aluno).enqueue(new BoletimCallback());

    }

    @Subscribe
    public void preencheLista(BoletimEvent event) {

        materias = (ArrayList<Materia>) event.boletim.getMaterias();

        recyclerViewBoletim.setAdapter(new BoletimAdapter(this, materias));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewBoletim.setLayoutManager(layoutManager);
    }

    private void preparaNavigationDrawer() {
        PrimaryDrawerItem notas = new PrimaryDrawerItem().withName(getString(R.string.dashboard_button_grades));
        SecondaryDrawerItem horarios = new SecondaryDrawerItem().withName(getString(R.string.dashboard_button_schedule)).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
//                Intent irParaPerfil = new Intent(activity, PerfilActivity.class);
//                startActivity(irParaPerfil);
                return false;
            }
        });
        SecondaryDrawerItem noticias = new SecondaryDrawerItem().withName(getString(R.string.dashboard_button_news));
        SecondaryDrawerItem uezoBus = new SecondaryDrawerItem().withName(getString(R.string.dashboard_button_bus));
        SecondaryDrawerItem logout = new SecondaryDrawerItem().withName(getString(R.string.dashboard_button_logout));

        IProfile perfil = new ProfileDrawerItem()
                .withEmail(aluno.getMatricula())
                .withName("Leonardo Cordeiro")
                .withIcon(R.drawable.ic_sem_foto);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorPrimaryDark)
                .addProfiles(perfil)
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withToolbar(mToolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(notas, noticias, uezoBus, logout)
                .build();

        preparaHamburguerIcone(result);
    }

    private void preparaHamburguerIcone(Drawer result) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas, menu);
        return true;
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
        dialogUtils.showCancelable(new DialogMessage(event.error.getMessage()));
    }

}
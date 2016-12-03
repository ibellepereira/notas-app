package org.lema.notasapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    private Toolbar mToolbar;
    private ArrayList<Materia> materias;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletim);

        preparaToolbar();

        carregarPreferencias();

        preencheReferencias();

        preparaNavigationDrawer();

        buscaBoletim();


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
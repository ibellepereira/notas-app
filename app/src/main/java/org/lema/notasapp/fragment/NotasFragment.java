package org.lema.notasapp.fragment;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.lema.notasapp.R;
import org.lema.notasapp.activity.BoletimActivity;
import org.lema.notasapp.activity.PerfilActivity;
import org.lema.notasapp.adapter.MateriasAdapter;

/**
 * Created by leonardocordeiro on 01/02/16.
 */
public class NotasFragment extends Fragment {
    private Toolbar toolbar;
    private BoletimActivity activity;
    private View fragment;
    private AccountHeader headerResult;
    private static String matricula;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.fragment = inflater.inflate(R.layout.notas_fragment, null);

        this.activity = (BoletimActivity) getActivity();

        preparaToolBar();
        preparaNavigationDrawer();

        ListView materias = (ListView) fragment.findViewById(R.id.lv_materias);
        materias.setAdapter(new MateriasAdapter(activity, activity.getBoletim()));

        return fragment;
    }


    private void preparaToolBar() {
        toolbar = (Toolbar) fragment.findViewById(R.id.bar);
        activity.setSupportActionBar(toolbar);
    }

    private void preparaNavigationDrawer() {
        PrimaryDrawerItem boletim = new PrimaryDrawerItem().withName("Boletim");
        SecondaryDrawerItem meuPerfil = new SecondaryDrawerItem().withName("Meu Perfil").withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                Intent irParaPerfil = new Intent(activity, PerfilActivity.class);
                startActivity(irParaPerfil);
                return false;
            }
        });
        SecondaryDrawerItem meusRankings = new SecondaryDrawerItem().withName("Meus Rankings");

        IProfile perfil = new ProfileDrawerItem()
                                .withEmail(matricula)
                                .withName("Leonardo Cordeiro")
                                .withIcon(R.drawable.ic_sem_foto);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.color.colorPrimary)
                .addProfiles(perfil)
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withTranslucentStatusBar(true)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(boletim, meuPerfil, meusRankings)
                .build();

        preparaHamburguerIcone(result);
    }

    private void preparaHamburguerIcone(Drawer result) {
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }
    public static String getMatricula(String mat)
    {

        matricula = mat;
        return matricula;
    }
}

package org.lema.notasapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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
import org.lema.notasapp.ui.activity.BoletimActivity;
import org.lema.notasapp.ui.adapter.MateriasAdapter;
import org.lema.notasapp.domain.model.Materia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonardocordeiro on 01/02/16.
 */
public class NotasFragment extends RenderableFragment {
    private Toolbar toolbar;
    private BoletimActivity activity;
    private View fragment;

    private List<Materia> materias = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        this.fragment = inflater.inflate(R.layout.notas_fragment, null);

        this.activity = (BoletimActivity) getActivity();

        preparaToolBar();
        preparaNavigationDrawer();

//        this.materias = getArguments().getParcelableArrayList("materias");

        ListView listView = (ListView) fragment.findViewById(R.id.lv_materias);
        listView.setAdapter(new MateriasAdapter(activity, materias));

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
//                Intent irParaPerfil = new Intent(activity, PerfilActivity.class);
//                startActivity(irParaPerfil);
                return false;
            }
        });
        SecondaryDrawerItem meusRankings = new SecondaryDrawerItem().withName("Meus Rankings");

        IProfile perfil = new ProfileDrawerItem()
                                .withEmail("1413331050")
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

    @Override
    public void render(FragmentActivity activity) {
        FragmentTransaction tx = activity.getSupportFragmentManager().beginTransaction();
//        tx.replace(R.id.boletim, this);
        tx.commit();
    }
}

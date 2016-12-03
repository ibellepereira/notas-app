package org.lema.notasapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
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
import org.lema.notasapp.modelo.Aluno;

import java.util.HashMap;

/**
 * Created by Cadu on 05/11/2016.
 */

public class DashboardActivity extends AppCompatActivity {
    private SliderLayout mSlider;
    private LinearLayout mLayoutNotas;
    private Toolbar mToolbar;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        preencheReferencias();

        carregarPreferencias();

        preparaToolbar();

        preparaNavigationDrawer();

        preaparaSlider();

        ativaBotoesDash();
    }

    private void preparaToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar_dashboard);
        mToolbar.setTitle(R.string.activity_dashboard_name);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void preencheReferencias() {
        mSlider = (SliderLayout)findViewById(R.id.slider);
        mLayoutNotas = (LinearLayout) findViewById(R.id.ll_dashboard_button_grades);
    }

    private void carregarPreferencias() {

        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_user_key), Context.MODE_PRIVATE);

        String matricula = sharedPreferences.getString(getString(R.string.preference_matricula), "");
        String senha = sharedPreferences.getString(getString(R.string.preference_password), "");

        aluno = new Aluno(matricula, senha);
    }

    private void preaparaSlider() {
        HashMap<Integer, Integer> file_maps = new HashMap<Integer, Integer>();
        file_maps.put(R.string.dashboard_slider_news_title_1, R.drawable.temp1);

        for(Integer index : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(getResources().getString(index))
                    .image(file_maps.get(index))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", getResources().getString(index));

            mSlider.addSlider(textSliderView);
            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mSlider.setCustomAnimation(new DescriptionAnimation());
            mSlider.setDuration(4000);
        }
    }

    private void ativaBotoesDash() {
        mLayoutNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, BoletimActivity.class);
                startActivity(intent);
            }
        });

    /*    mLayoutNotas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(DashboardActivity.this, BoletimActivity.class);
                startActivity(intent);
                return true;
            }
        });*/
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
    protected void onStop(){
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mSlider.stopAutoCycle();
        super.onStop();
    }

    /*
    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    */
}

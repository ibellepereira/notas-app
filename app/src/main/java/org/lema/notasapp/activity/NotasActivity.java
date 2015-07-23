package org.lema.notasapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.lema.notasapp.R;
import org.lema.notasapp.adapter.MateriasAdapter;
import org.lema.notasapp.dao.AlunoDao;
import org.lema.notasapp.json.MateriaJsonConverter;
import org.lema.notasapp.modelo.Materia;

import java.util.List;


public class NotasActivity extends ActionBarActivity {

    private ListView mListaDeMaterias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setupActionbar();

        mListaDeMaterias = (ListView) findViewById(R.id.lv_materias);

        String json = (String) getIntent().getSerializableExtra("materias");

        if(json != null) {
            List<Materia> materias = new MateriaJsonConverter(json).toList();

            MateriasAdapter materiasAdapter = new MateriasAdapter(this, materias);
            mListaDeMaterias.setAdapter(materiasAdapter);

            mListaDeMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Materia materia = (Materia) adapterView.getItemAtPosition(i);

                    String notas = "AV1: " + materia.getAv1() + "\n" +
                            "AV2: " + materia.getAv2() + "\n" +
                            "AV3: " + materia.getAv3() + "\n" +
                            "MEDIA: " + materia.getMedia() + "\n";

                    Toast.makeText(NotasActivity.this, notas, Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(R.id.menu_logout == id) {
            SharedPreferences preferences = getSharedPreferences("notas-app", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.clear();

            editor.commit();

            voltarParaLogin();
        }

        return true;
    }

    private void voltarParaLogin() {
        Intent irParaLogin = new Intent(this, LoginActivity.class);
        startActivity(irParaLogin);
    }

    private void setupActionbar() {
        Toolbar bar = (Toolbar) findViewById(R.id.bar);
        bar.setTitle("");

        setSupportActionBar(bar);
    }

}

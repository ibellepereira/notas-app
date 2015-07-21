package org.lema.notasapp.activity;

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
import org.lema.notasapp.json.JsonConverter;
import org.lema.notasapp.modelo.Materia;

import java.util.Arrays;
import java.util.List;


public class NotasActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listaDeMaterias = (ListView) findViewById(R.id.materias);

        String json = (String) getIntent().getSerializableExtra("materias");

        List<Materia> materias = new JsonConverter(json).toList();

        MateriasAdapter materiasAdapter = new MateriasAdapter(this, materias);

        listaDeMaterias.setAdapter(materiasAdapter);

        listaDeMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

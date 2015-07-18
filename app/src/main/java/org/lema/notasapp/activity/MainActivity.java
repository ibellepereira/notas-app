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
import org.lema.notasapp.modelo.Materia;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listaDeMaterias = (ListView) findViewById(R.id.materias);

        List<Materia> materias = Arrays.asList(new Materia("Calculo I"), new Materia("Algebra Linear"));

        MateriasAdapter materiasAdapter = new MateriasAdapter(this, materias);

        listaDeMaterias.setAdapter(materiasAdapter);

    }


}

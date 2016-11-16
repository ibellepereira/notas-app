package org.lema.notasapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.lema.notasapp.R;
import org.lema.notasapp.adapter.GradeAdapter;
import org.lema.notasapp.modelo.Horario;
import org.lema.notasapp.modelo.Grade;
import org.lema.notasapp.modelo.Materia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class HorariosActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GradeAdapter mAdapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);

        preparaToolbar();

        preparaRecyclerView();

    }

    private void preparaRecyclerView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_horarios);
        mAdapter = new GradeAdapter(this, preencheLista());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void preparaToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar_horario);
        mToolbar.setTitle(R.string.activity_horario_name);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private List<Grade> preencheLista(){
        Calendar dataAtual = Calendar.getInstance();

        List<Horario> horarios = new ArrayList<>();

        Horario h1 = new Horario(new Materia("Cálculo I", "Renata"), null, dataAtual.getTime(), dataAtual.getTime());
        Horario h2 = new Horario(new Materia("POO3", "Leonardo"), null, dataAtual.getTime(), dataAtual.getTime());

        Grade g1 = new Grade("Segunda", Arrays.asList(h1, h2));
        Grade g2 = new Grade("Terça", Arrays.asList(h2));

        List<Grade> grades = Arrays.asList(g1, g2);

        return grades;
    }
}

package org.lema.notasapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import org.lema.notasapp.R;
import org.lema.notasapp.delegate.MostrarNotasDelegate;
import org.lema.notasapp.json.BoletimConverter;
import org.lema.notasapp.modelo.Aluno;
import org.lema.notasapp.modelo.EstadoNotasActivity;
import org.lema.notasapp.modelo.Materia;
import org.lema.notasapp.task.BoletimTask;

import java.util.ArrayList;
import java.util.List;


public class BoletimActivity extends ActionBarActivity implements MostrarNotasDelegate {
    private List<Materia> boletim = new ArrayList<>();
    private EstadoNotasActivity estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notas_activity);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        this.estado = EstadoNotasActivity.BUSCAR_NOTAS;
        this.estado.executa(this);
    }

    private Aluno getAluno() {
        return (Aluno) getIntent().getSerializableExtra("aluno");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_logout) {
            Intent irParaPerfil = new Intent(this, PerfilActivity.class);
            startActivity(irParaPerfil);

        }
        return true;
    }

    public void buscaBoletim() {
        new BoletimTask(this, getAluno()).execute();

    }


    @Override
    public void lidaComRetorno(String json) {
        this.boletim = getBoletim(json);

        alteraEstadoEExecuta(EstadoNotasActivity.NA_TELA);
    }

    public List<Materia> getBoletim() {
        return this.boletim;
    }

    private List<Materia> getBoletim(String json) {
        return new BoletimConverter(json).toList();
    }

    @Override
    public void lidaComErro(Exception e) {

    }

    public void alteraEstadoEExecuta(EstadoNotasActivity activity) {
        this.estado = activity;
        this.estado.executa(this);
    }
}

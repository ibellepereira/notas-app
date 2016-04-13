package org.lema.notasapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.Toast;

import org.lema.notasapp.R;
import org.lema.notasapp.dao.AlunoDao;
import org.lema.notasapp.delegate.MostrarNotasDelegate;
import org.lema.notasapp.json.BoletimConverter;
import org.lema.notasapp.modelo.Aluno;
import org.lema.notasapp.modelo.EstadoNotasActivity;
import org.lema.notasapp.modelo.Materia;
import org.lema.notasapp.task.BoletimService;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class BoletimActivity extends ActionBarActivity implements MostrarNotasDelegate {
    private List<Materia> boletim = new ArrayList<>();
    private BoletimService buscador = new BoletimService();
    private EstadoNotasActivity estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notas_activity);

        alteraEstadoEExecuta(EstadoNotasActivity.BUSCAR_NOTAS);
    }

    private Aluno getAluno() {
        return (Aluno) getIntent().getSerializableExtra("aluno");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas, menu);
        return true;
    }

    public void buscaBoletim() {
        buscador.buscar(getAluno())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    lidaComRetorno(s);
                    if(entrarAutomaticamente())
                        salvarCredenciais();

                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    lidaComErro((Exception) throwable);
                }
            });

    }

    private void salvarCredenciais() {
        AlunoDao alunoDao = new AlunoDao(this);
        alunoDao.salvar(getAluno());
    }

    private boolean entrarAutomaticamente() {
        return getIntent().getBooleanExtra("entrar-automaticamente", false);
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

    public void lidaComErro(Exception e) {
        if(e.getCause() instanceof FileNotFoundException) dadosInvalidos();
        else erroGenerico();
        finish();
    }

    public void dadosInvalidos() {
        Toast.makeText(this, "Matrícula ou Senha inválidos", Toast.LENGTH_LONG).show();
        finish();
    }

    public void erroGenerico() {
        Toast.makeText(this, "Problema no servidor", Toast.LENGTH_LONG).show();
        finish();
    }

    public void alteraEstadoEExecuta(EstadoNotasActivity activity) {
        this.estado = activity;
        this.estado.executa(this);
    }
}
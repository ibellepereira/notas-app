package org.lema.notasapp.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import org.greenrobot.eventbus.Subscribe;
import org.lema.notasapp.R;
import org.lema.notasapp.infra.oauth2.model.AccessToken;
import org.lema.notasapp.ui.utils.DialogMessage;
import org.lema.notasapp.domain.dao.AlunoDao;
import org.lema.notasapp.domain.service.BoletimService;
import org.lema.notasapp.ui.fragment.CarregandoFragment;
import org.lema.notasapp.ui.fragment.NotasFragment;
import org.lema.notasapp.infra.app.NotasAppAplication;
import org.lema.notasapp.infra.retrofit.callback.BoletimCallback;
import org.lema.notasapp.infra.dagger.component.BoletimComponent;
import org.lema.notasapp.infra.event.ThrowableEvent;
import org.lema.notasapp.infra.listener.OnRetryListener;
import org.lema.notasapp.infra.event.BoletimEvent;
import org.lema.notasapp.domain.model.Aluno;
import org.lema.notasapp.domain.model.Materia;

import javax.inject.Inject;
import java.util.ArrayList;


public class BoletimActivity extends OAuthActivity {

    @Inject BoletimService boletimService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notas_activity);

        prepararInjecao();
        buscaBoletim();
    }

    public void onStart() {
        super.onStart();

        CarregandoFragment fragment = new CarregandoFragment();
        fragment.render(this);
    }

    private void prepararInjecao() {
        NotasAppAplication app = (NotasAppAplication) getApplication();
        BoletimComponent component = app.getComponent();
        component.inject(this);
    }

    private Aluno getAluno() {
        return (Aluno) getIntent().getSerializableExtra("aluno");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas, menu);
        return true;
    }

    @Subscribe
    public void colocarNaTela(BoletimEvent boletimEvent) {
        ArrayList<Materia> materias = (ArrayList<Materia>) boletimEvent.boletim.getMaterias();

        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList("materias", materias);

        NotasFragment notasFragment = new NotasFragment();
        notasFragment.setArguments(arguments);
        notasFragment.render(this);

    }

    public void buscaBoletim() {
        Aluno aluno = getAluno();
        boletimService.getBoletimDo(aluno).enqueue(new BoletimCallback());

    }

    private void salvarCredenciais() {
        AlunoDao alunoDao = new AlunoDao(this);
        alunoDao.salvar(getAluno());
    }

    private boolean entrarAutomaticamente() {
        return getIntent().getBooleanExtra("entrar-automaticamente", false);
    }

    @Subscribe
    public void handle(ThrowableEvent event) {
        dialogUtils.show(new DialogMessage(event.exception.getMessage(), new OnRetryListener() {
            @Override
            public void onRetry() {
                buscaBoletim();
            }
        }));

    }

    @Subscribe
    public void onReceiveAccessToken(AccessToken accessToken) {
        buscaBoletim();
    }

}
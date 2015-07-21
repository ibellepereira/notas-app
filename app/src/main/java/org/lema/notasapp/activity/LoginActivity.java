package org.lema.notasapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.lema.notasapp.R;
import org.lema.notasapp.modelo.Credenciais;
import org.lema.notasapp.task.LoginTask;

/**
 * Created by leonardocordeiro on 21/07/15.
 */
public class LoginActivity extends ActionBarActivity {

    private Button mLoginButton;

    private EditText mMatricula;
    private EditText mSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        setupActionBar();

        mMatricula = (EditText) findViewById(R.id.ed_matricula);
        mSenha = (EditText) findViewById(R.id.ed_senha);

        mMatricula.setText("1423331034");
        mSenha.setText("zaion6491");

        mLoginButton = (Button) findViewById(R.id.btn_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matricula = mMatricula.getText().toString();
                String senha = mSenha.getText().toString();

                if(matricula.isEmpty())
                    mMatricula.setError("Insira a matricula");

                if(senha.isEmpty())
                    mSenha.setError("Insira a senha");

                if(matricula.isEmpty() || senha.isEmpty())
                    return;

                Credenciais credenciais = new Credenciais(matricula, senha);

                LoginTask task = new LoginTask(LoginActivity.this, credenciais);
                task.execute();
            }
        });

    }

    public void lidarComRetorno(String json) {
        Intent irParaNotas = new Intent(this, NotasActivity.class);
        irParaNotas.putExtra("materias", json);
        startActivity(irParaNotas);
    }

    private void setupActionBar() {
        Toolbar bar = (Toolbar) findViewById(R.id.bar);
        bar.setTitle("");

        setSupportActionBar(bar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

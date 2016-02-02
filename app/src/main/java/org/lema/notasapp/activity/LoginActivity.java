package org.lema.notasapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.lema.notasapp.R;
import org.lema.notasapp.dao.AlunoDao;
import org.lema.notasapp.modelo.Aluno;

/**
 * Created by leonardocordeiro on 21/07/15.
 */
public class LoginActivity extends AppCompatActivity {

    private Button mLoginButton;

    private EditText mMatricula;
    private EditText mSenha;

    private CheckBox mEntrarAutomaticamente;

    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mMatricula = (EditText) findViewById(R.id.ed_matricula);
        mSenha = (EditText) findViewById(R.id.ed_senha);
        mEntrarAutomaticamente = (CheckBox) findViewById(R.id.cb_entrar_automaticamente);

        limparDados();

        mLoginButton = (Button) findViewById(R.id.btn_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ehValido(mMatricula, mSenha)) {
                    String matricula = mMatricula.getText().toString();
                    String senha = mSenha.getText().toString();

                    aluno = new Aluno(matricula, senha);

                    login(aluno);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Aluno aluno = new AlunoDao(getSharedPreferences("notas-app", MODE_PRIVATE)).busca();

        if(aluno != null) {
            login(aluno);
            return;
        }
    }

    private boolean entrarAutomaticamente() {
        return mEntrarAutomaticamente.isChecked();
    }

    private void login(Aluno aluno) {
        Intent irParaBoletim = new Intent(this, BoletimActivity.class);
        irParaBoletim.putExtra("aluno", aluno);

        startActivity(irParaBoletim);
    }

    private void limparDados() {
        mMatricula.setText("");
        mSenha.setText("");
    }

    public boolean ehValido(EditText mMatricula, EditText mSenha) {
        boolean ehValido = true;

        if(mMatricula.getText().toString().isEmpty()) {
            mMatricula.setError("Insira a matricula");
            ehValido = false;
        }

        if(mSenha.getText().toString().isEmpty()) {
            mSenha.setError("Insira a senha");
            ehValido = false;
        }

        return ehValido;

    }

}

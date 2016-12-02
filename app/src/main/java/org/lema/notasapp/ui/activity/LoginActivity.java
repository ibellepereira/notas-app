package org.lema.notasapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.dao.AlunoDao;
import org.lema.notasapp.domain.model.Aluno;
import org.lema.notasapp.infra.oauth2.OAuth2;
import org.lema.notasapp.infra.oauth2.model.AccessToken;

import java.util.Properties;


/**
 * Created by leonardocordeiro on 21/07/15.
 */
public class LoginActivity extends AccessTokenActivity {

    private Button mLoginButton;

    private EditText mMatricula;
    private EditText mSenha;

    private CheckBox mEntrarAutomaticamente;

    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prepateOAuth2Properties();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mMatricula = (EditText) findViewById(R.id.ed_matricula);

        mSenha = (EditText) findViewById(R.id.ed_senha);
        mSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSenha.setText("");
            }
        });

        mEntrarAutomaticamente = (CheckBox) findViewById(R.id.cb_entrar_automaticamente);

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

        Aluno aluno = new AlunoDao(this).getAluno();

        if(aluno != null) {
            populaFormulario(aluno);
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        limparDados();
    }

    private void prepateOAuth2Properties() {
        Properties oauth2Properties = new Properties();

        oauth2Properties.setProperty(OAuth2.BASE_URL, "http://notasapp-lema.rhcloud.com/notasapp-backend/");
        oauth2Properties.setProperty(OAuth2.SCOPE, "read");
        oauth2Properties.setProperty(OAuth2.CLIENT_ID, "mobile-client");
        oauth2Properties.setProperty(OAuth2.CLIENT_SECRET, "462441f0-852d-11e6-ae22-56b6b6499611");

        OAuth2.setProperties(oauth2Properties);
    }

    @Override
    public void onReceiveAccessToken(AccessToken accessToken) {

    }

    private void populaFormulario(Aluno aluno) {
        mMatricula.setText(aluno.getMatricula());
        mSenha.setText(aluno.getSenha());
    }

    private boolean entrarAutomaticamente() {
        return mEntrarAutomaticamente.isChecked();
    }

    private void login(Aluno aluno) {
        Intent irParaBoletim = new Intent(this, BoletimActivity.class);
        irParaBoletim.putExtra("aluno", aluno);
        irParaBoletim.putExtra("entrar-automaticamente", entrarAutomaticamente());

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

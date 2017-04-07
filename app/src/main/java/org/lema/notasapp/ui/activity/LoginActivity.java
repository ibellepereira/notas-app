package org.lema.notasapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.dao.AlunoDao;
import org.lema.notasapp.domain.model.Aluno;
import org.lema.notasapp.domain.preferences.SenhaPreferences;
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
    private TextView mTermosCondicoes;

    private Aluno aluno;
    private AlunoDao alunoDao = new AlunoDao(this);
    private SenhaPreferences senhaPreferences = new SenhaPreferences(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prepareOAuth2Properties();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        preencheReferencias();

        loginButtonOnClick();

        preparaTermosECondicoesLink();

    }

    private void carregarPreferencias() {

        boolean entrarAutomaticamente = senhaPreferences.obterSenhaSalva();

        if(entrarAutomaticamente) {
            populaFormulario(alunoDao.obterAlunoLogado());
            mEntrarAutomaticamente.setChecked(senhaPreferences.obterSenhaSalva());
        }
    }

    private void loginButtonOnClick() {
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

    private void preencheReferencias(){
        mMatricula = (EditText) findViewById(R.id.ed_matricula);
        mTermosCondicoes = (TextView) findViewById(R.id.tv_login_termos);
        mSenha = (EditText) findViewById(R.id.ed_senha);
        mLoginButton = (Button) findViewById(R.id.btn_login);
        mEntrarAutomaticamente = (CheckBox) findViewById(R.id.save_password);
    }

    private void preparaTermosECondicoesLink() {
        SpannableString spannedString = new SpannableString(getResources().getString(R.string.terms_and_conditios));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, TermosCondicoesActivity.class);
                startActivity(intent);
            }
        };
        spannedString.setSpan(clickableSpan, 24, 42, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTermosCondicoes.setText(spannedString);
        mTermosCondicoes.setMovementMethod(LinkMovementMethod.getInstance());
        mTermosCondicoes.setHighlightColor(ContextCompat.getColor(this, R.color.colorAccent));

    }


    private void prepareOAuth2Properties() {
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
        if(aluno != null) {
            mMatricula.setText(aluno.getMatricula());
            mSenha.setText(aluno.getSenha());
        }
    }

    private boolean entrarAutomaticamente() {
        return mEntrarAutomaticamente.isChecked();
    }

    private void login(Aluno aluno) {
        alunoDao.salvarAluno(aluno);
        senhaPreferences.salvarSenha(entrarAutomaticamente());

        Intent irParaBoletim = new Intent(this, MainActivity.class);

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


    @Override
    protected void onResume() {
        super.onResume();

        carregarPreferencias();
    }

    public void onRestart() {
        super.onRestart();
        limparDados();
    }

}

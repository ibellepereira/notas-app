package org.lema.notasapp.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.lema.notasapp.activity.LoginActivity;
import org.lema.notasapp.activity.NotasActivity;
import org.lema.notasapp.client.WebClient;
import org.lema.notasapp.json.JsonConverter;
import org.lema.notasapp.modelo.Credenciais;
import org.lema.notasapp.modelo.Materia;

import java.util.List;

/**
 * Created by leonardocordeiro on 21/07/15.
 */
public class LoginTask extends AsyncTask<Void, Void, String> {

    private Credenciais credenciais;
    private LoginActivity activity;

    private ProgressDialog dialog;


    public LoginTask(LoginActivity activity, Credenciais credenciais) {
        this.credenciais = credenciais;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = ProgressDialog.show(activity, "Processando", "Carregando...", true, false);

    }

    @Override
    protected String doInBackground(Void... voids) {
        WebClient client = new WebClient("http://notasapp-lema.rhcloud.com/servicos/notas/" +
                                                           credenciais.getMatricula() + "/" +
                                                           credenciais.getSenha());

        String json = client.doGet();

        return json;
    }

    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);

        dialog.dismiss();

        activity.lidarComRetorno(json);

    }
}

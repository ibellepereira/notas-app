package org.lema.notasapp.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.lema.notasapp.Enderecos;
import org.lema.notasapp.activity.LoginActivity;
import org.lema.notasapp.client.WebClient;
import org.lema.notasapp.modelo.Aluno;

/**
 * Created by leonardocordeiro on 21/07/15.
 */
public class LoginTask extends AsyncTask<Void, Void, String> {

    private LoginActivity activity;
    private ProgressDialog dialog;

    private Aluno aluno;

    public LoginTask(LoginActivity activity, Aluno aluno) {
        this.aluno = aluno;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = ProgressDialog.show(activity, "Processando", "Carregando...", true, false);

    }

    @Override
    protected String doInBackground(Void... voids) {
        WebClient client = new WebClient(Enderecos.NOTAS_SERVICO_PATH +
                                     aluno.getMatricula() + "/" +
                                     aluno.getSenha());

        try {
            String json = client.get();

            return json;

        } catch(Exception e) {

        }

        return null;

    }

    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);

        dialog.dismiss();

        if(json != null)
            activity.mostrarNotas(json);

    }
}

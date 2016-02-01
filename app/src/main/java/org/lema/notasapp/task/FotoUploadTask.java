package org.lema.notasapp.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.lema.notasapp.Enderecos;
import org.lema.notasapp.client.WebClient;

/**
 * Created by leonardocordeiro on 18/01/16.
 */
public class FotoUploadTask extends AsyncTask<Void, Void, String> {

    private String foto;
    private ProgressDialog dialog;
    private Activity activity;

    public FotoUploadTask(String foto, Activity activity) {
        this.foto = foto;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(Void... params) {
        WebClient client = new WebClient(Enderecos.PERFIL_RESOURCE_PATH + "foto");
        return client.post(foto);

    }

    @Override
    protected void onPostExecute(String retorno) {
        Toast.makeText(activity, "oi: " + retorno, Toast.LENGTH_LONG).show();
        Log.i("tag", "oi: " + retorno);

    }
}

package org.lema.notasapp.task;

import android.os.AsyncTask;

import org.lema.notasapp.Enderecos;
import org.lema.notasapp.client.WebClient;
import org.lema.notasapp.delegate.MostrarNotasDelegate;
import org.lema.notasapp.modelo.Aluno;

/**
 * Created by leonardocordeiro on 21/07/15.
 */
public class BoletimTask extends AsyncTask<Void, Void, String> {

    private MostrarNotasDelegate delegate;
    private Aluno aluno;

    public BoletimTask(MostrarNotasDelegate activity, Aluno aluno) {
        this.aluno = aluno;
        this.delegate = activity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        WebClient client = new WebClient(Enderecos.NOTAS_SERVICO_PATH +
                                     aluno.getMatricula() + "/" +
                                     aluno.getSenha());

        try {
            return client.get();

        } catch(Exception e) {

        }

        return null;

    }

    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);

        if(json != null)
            delegate.lidaComRetorno(json);

    }
}

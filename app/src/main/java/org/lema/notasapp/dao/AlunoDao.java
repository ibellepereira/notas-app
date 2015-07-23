package org.lema.notasapp.dao;

import android.content.SharedPreferences;

import org.lema.notasapp.modelo.Aluno;

/**
 * Created by leonardocordeiro on 22/07/15.
 */
public class AlunoDao {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public AlunoDao(SharedPreferences preferences, SharedPreferences.Editor editor) {
        this.preferences = preferences;
        this.editor = editor;
    }

    public AlunoDao(SharedPreferences preferences) {
        this(preferences, null);
    }

    public void salvar(Aluno aluno) {
        editor.putString("matricula", aluno.getMatricula());
        editor.putString("senha", aluno.getSenha());

        editor.commit();
    }

    public Aluno busca() {
        String matricula = preferences.getString("matricula", null);
        String senha = preferences.getString("senha", null);

        if(matricula == null || senha == null)
            return null;

        Aluno aluno = new Aluno(matricula, senha);
        return aluno;
    }

    public void remove() {
        editor.clear();
    }

}

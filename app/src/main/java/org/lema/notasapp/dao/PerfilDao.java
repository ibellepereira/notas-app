package org.lema.notasapp.dao;

import android.content.SharedPreferences;

/**
 * Created by leonardocordeiro on 23/01/16.
 */
public class PerfilDao {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public PerfilDao(SharedPreferences preferences, SharedPreferences.Editor editor) {
        this.preferences = preferences;
        this.editor = editor;
    }

    public PerfilDao(SharedPreferences preferences) {
        this(preferences, null);
    }

    public void salvarCaminhoDaFoto(String caminho) {
        editor.putString("foto", caminho);
    }

    public String pegarCaminhoDaFoto() {
        return preferences.getString("foto", null);
    }

}

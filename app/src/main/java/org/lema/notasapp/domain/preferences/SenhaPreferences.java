package org.lema.notasapp.domain.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import org.lema.notasapp.R;

/**
 * Created by igor on 28/12/16.
 */

public class SenhaPreferences {

    private Context context;

    public SenhaPreferences(Context context) {
        this.context = context;
    }

    public boolean obterSenhaSalva() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getString(R.string.preference_save), false);
    }

    public void salvarSenha(boolean entrarAutomaticamente){
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(context.getString(R.string.preference_save), entrarAutomaticamente);
        editor.commit();
    }
}

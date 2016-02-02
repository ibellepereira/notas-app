package org.lema.notasapp.modelo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import org.lema.notasapp.R;
import org.lema.notasapp.activity.BoletimActivity;
import org.lema.notasapp.fragment.CarregandoFragment;
import org.lema.notasapp.fragment.NotasFragment;
import org.lema.notasapp.task.BoletimTask;

/**
 * Created by leonardocordeiro on 01/02/16.
 */
public enum EstadoNotasActivity {

    BUSCAR_NOTAS {
        @Override
        public void executa(BoletimActivity activity) {
            activity.buscaBoletim();

            colocaFragmentNaTela(activity, new CarregandoFragment());
        }
    },

    NA_TELA {
        @Override
        public void executa(BoletimActivity activity) {
            colocaFragmentNaTela(activity, new NotasFragment());
        }
    };

    private static void colocaFragmentNaTela(BoletimActivity activity, Fragment fragment) {
        FragmentTransaction tx = activity.getSupportFragmentManager().beginTransaction();
        tx.setCustomAnimations(R.anim.abc_slide_out_top, R.anim.abc_slide_in_bottom);
        tx.replace(R.id.boletim, fragment);
        tx.commit();
    }


    public abstract void executa(BoletimActivity activity);
}

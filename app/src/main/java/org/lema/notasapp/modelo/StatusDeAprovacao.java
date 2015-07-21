package org.lema.notasapp.modelo;

import org.lema.notasapp.R;

/**
 * Created by leonardocordeiro on 21/07/15.
 */
public enum StatusDeAprovacao {

    APROVADO {
        @Override
        public int getColor() {
            return R.drawable.fundo_aprovado;
        }
    }, REPROVADO {
        @Override
        public int getColor() {
            return R.drawable.fundo_reprovado;
        }
    }, SEM_NOTA {
        @Override
        public int getColor() {
            return R.drawable.fundo_sem_nota;
        }
    }, ESPERANDO_AV3 {
        @Override
        public int getColor() {
            return R.drawable.fundo_esperando_av3;
        }
    };

    public abstract int getColor();

}

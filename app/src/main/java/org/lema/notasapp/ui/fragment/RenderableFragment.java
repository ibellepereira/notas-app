package org.lema.notasapp.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by leonardocordeiro on 02/12/16.
 */
public abstract class RenderableFragment extends Fragment {

    /**
        Método coloca o próprio Fragment em uma determinada Activity.

        Exemplo:
        <pre>
        * {@code
        *   FragmentTransaction tx = ...
        *   tx.replace( ... , this);  // this fazendo referencia ao proprio Fragment
        *   tx.commit();
        * }
        *
        </pre>
     */
    public abstract void render(FragmentActivity activity);

}

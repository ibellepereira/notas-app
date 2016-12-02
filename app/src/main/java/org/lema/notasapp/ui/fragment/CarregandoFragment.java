package org.lema.notasapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.lema.notasapp.R;

/**
 * Created by leonardocordeiro on 01/01/16.
 */
public class CarregandoFragment extends RenderableFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.carregando_fragment, null);
    }

    public void render(FragmentActivity activity) {
        FragmentTransaction tx = activity.getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.boletim, this);
        tx.commit();
    }
}

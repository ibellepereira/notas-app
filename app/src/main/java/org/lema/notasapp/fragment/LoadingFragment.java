package org.lema.notasapp.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.lema.notasapp.R;

/**
 * Created by JLGSobreiro on 22/08/2016.
 */
public class LoadingFragment extends Fragment {


    public LoadingFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.carregando_fragment, container, false);
    }

}

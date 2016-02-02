package org.lema.notasapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.lema.notasapp.R;
import org.lema.notasapp.activity.BoletimActivity;
import org.lema.notasapp.adapter.MateriasAdapter;

/**
 * Created by leonardocordeiro on 01/02/16.
 */
public class NotasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View boletimLayout = inflater.inflate(R.layout.notas_fragment, null);

        BoletimActivity activity = (BoletimActivity) getActivity();

        ListView materias = (ListView) boletimLayout.findViewById(R.id.lv_materias);
        materias.setAdapter(new MateriasAdapter(activity, activity.getBoletim()));

        return boletimLayout;
    }
}

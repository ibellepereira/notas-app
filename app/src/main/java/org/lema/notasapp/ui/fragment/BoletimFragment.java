package org.lema.notasapp.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.lema.notasapp.R;
import org.lema.notasapp.adapter.BoletimAdapter;
import org.lema.notasapp.domain.model.MateriaDto;

import java.util.ArrayList;

/**
 * Created by igor on 04/04/17.
 */

public class BoletimFragment extends Fragment {

    private RecyclerView mRecyclerViewBoletim;
    private ArrayList<MateriaDto> mMaterias = new ArrayList<>();

    public BoletimFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_boletim, container, false);
        mRecyclerViewBoletim = (RecyclerView)  view.findViewById(R.id.lv_materias);

        Bundle arguments = getArguments();
        this.mMaterias = (ArrayList<MateriaDto>) arguments.getSerializable("materias");

        if(mMaterias == null)
            mMaterias = new ArrayList<>();

        mRecyclerViewBoletim.setAdapter(new BoletimAdapter(getActivity(), mMaterias));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewBoletim.setLayoutManager(layoutManager);

        return view;
    }

}

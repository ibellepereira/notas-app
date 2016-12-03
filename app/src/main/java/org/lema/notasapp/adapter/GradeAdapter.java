package org.lema.notasapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import org.lema.notasapp.R;
import org.lema.notasapp.modelo.Grade;
import org.lema.notasapp.modelo.Horario;
import org.lema.notasapp.viewholder.HorarioViewHolder;
import org.lema.notasapp.viewholder.GradeViewHolder;

import java.util.List;

/**
 * Created by igor on 12/11/16.
 */

public class GradeAdapter extends ExpandableRecyclerAdapter<Grade, Horario, GradeViewHolder, HorarioViewHolder>{

    private LayoutInflater mInflater;

    public GradeAdapter(Context context, @NonNull List<Grade> grades) {
        super(grades);
        mInflater = LayoutInflater.from(context);
    }

    @UiThread
    @NonNull
    @Override
    public GradeViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View gradeView;
        gradeView = mInflater.inflate(R.layout.list_item_grade, parentViewGroup, false);
        return new GradeViewHolder(gradeView);
    }

    @UiThread
    @NonNull
    @Override
    public HorarioViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View horarioView;
        horarioView = mInflater.inflate(R.layout.list_item_horario, childViewGroup, false);
        return new HorarioViewHolder(horarioView);
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull GradeViewHolder gradeViewHolder, int parentPosition, @NonNull Grade grade) {
        gradeViewHolder.onBind(grade);
    }

    @Override
    public void onBindChildViewHolder(@NonNull HorarioViewHolder horarioViewHolder, int parentPosition, int childPosition, @NonNull Horario horario) {
        horarioViewHolder.onBind(horario);
    }
}

package org.lema.notasapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.Materia;
import org.lema.notasapp.viewholder.MateriaViewHolder;

import java.util.List;

/**
 * Created by igor on 30/11/16.
 */

public class BoletimAdapter extends RecyclerView.Adapter{

    private List<Materia> materias;
    private Activity activity;

    public BoletimAdapter(Activity activity, List<Materia> materias) {
        this.materias = materias;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.item_list_boletim, parent, false);

        MateriaViewHolder holder = new MateriaViewHolder(activity, view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        MateriaViewHolder holder = (MateriaViewHolder) viewHolder;

        Materia materia = materias.get(position);
        holder.onBind(materia);

    }

    @Override
    public int getItemCount() {
        return materias.size();
    }
}

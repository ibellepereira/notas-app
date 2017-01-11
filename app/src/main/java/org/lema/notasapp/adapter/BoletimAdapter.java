package org.lema.notasapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.MateriaDto;
import org.lema.notasapp.viewholder.MateriaViewHolder;

import java.util.List;

/**
 * Created by igor on 30/11/16.
 */

public class BoletimAdapter extends RecyclerView.Adapter{

    private List<MateriaDto> materias;
    private Activity activity;

    public BoletimAdapter(Activity activity, List<MateriaDto> materias) {
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

        MateriaDto materia = materias.get(position);
        holder.onBind(materia);

    }

    @Override
    public int getItemCount() {
        return materias.size();
    }
}

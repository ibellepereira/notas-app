package org.lema.notasapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.Materia;

/**
 * Created by igor on 30/11/16.
 */

public class MateriaViewHolder extends RecyclerView.ViewHolder {

    final TextView av1;
    final TextView av2;
    final TextView av3;
    final TextView media;
    final TextView nomeMateria;
    final TextView periodoMateria;
    final TextView curso;

    public MateriaViewHolder(View itemView) {
        super(itemView);

        av1 = (TextView) itemView.findViewById(R.id.av1);
        av2 = (TextView) itemView.findViewById(R.id.av2);
        av3 = (TextView) itemView.findViewById(R.id.av3);
        media = (TextView) itemView.findViewById(R.id.media);
        nomeMateria = (TextView) itemView.findViewById(R.id.disciplina);
        periodoMateria = (TextView) itemView.findViewById(R.id.periodoMateria);
        curso = (TextView) itemView.findViewById(R.id.curso);

    }

    public void onBind(Materia materia) {
        av1.setText(String.valueOf(materia.getNotaDaAv1()));
        av2.setText(String.valueOf(materia.getNotaDaAv2()));
        av3.setText(String.valueOf(materia.getNotaDaAv3()));
        media.setText(String.valueOf(materia.getMedia()));
        nomeMateria.setText(String.valueOf(materia.getNome()));
        periodoMateria.setText(materia.getAno());
        curso.setText("Ciencia da Computacao");
    }
}

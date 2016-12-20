package org.lema.notasapp.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.Materia;
import org.lema.notasapp.ui.activity.SugestaoNomeMateriaActivity;

/**
 * Created by igor on 30/11/16.
 */

public class MateriaViewHolder extends RecyclerView.ViewHolder {

    private Activity activity;

    final TextView av1;
    final TextView av2;
    final TextView av3;
    final TextView media;
    final TextView nomeMateria;
    final TextView codigoMateria;
    final TextView curso;
    final Button btnSugerir;

    public MateriaViewHolder(Activity activity, View itemView) {
        super(itemView);

        this.activity = activity;
        av1 = (TextView) itemView.findViewById(R.id.av1);
        av2 = (TextView) itemView.findViewById(R.id.av2);
        av3 = (TextView) itemView.findViewById(R.id.av3);
        media = (TextView) itemView.findViewById(R.id.media);
        nomeMateria = (TextView) itemView.findViewById(R.id.disciplina);
        codigoMateria = (TextView) itemView.findViewById(R.id.codigoMateria);
        curso = (TextView) itemView.findViewById(R.id.curso);
        btnSugerir = (Button) itemView.findViewById(R.id.btn_sugerir);

    }

    public void onBind(final Materia materia) {
        av1.setText(String.valueOf(materia.getNotaDaAv1()));
        av2.setText(String.valueOf(materia.getNotaDaAv2()));
        av3.setText(String.valueOf(materia.getNotaDaAv3()));
        media.setText(String.valueOf(materia.getMedia()));
        nomeMateria.setText(String.valueOf(materia.getNome()));
        codigoMateria.setText("CC2016");
        curso.setText("Ciencia da Computacao");
        btnSugerir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irParaSugestao = new Intent(MateriaViewHolder.this.activity, SugestaoNomeMateriaActivity.class);
                irParaSugestao.putExtra("materia", materia);
                Log.i("materia", materia.toString());
                activity.startActivity(irParaSugestao);

            }
        });
    }
}

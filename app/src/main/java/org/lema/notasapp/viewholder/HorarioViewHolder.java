package org.lema.notasapp.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;


import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.Horario;

/**
 * Created by igor on 14/11/16.
 */

public class HorarioViewHolder extends ChildViewHolder {

    private TextView mHorarioInicial, mHorarioTermino, mNomeMateria, mNomeProfessor;

    public HorarioViewHolder(@NonNull View itemView) {
        super(itemView);

        mHorarioInicial = (TextView) itemView.findViewById(R.id.tv_horario_horario_inicial);
        mHorarioTermino = (TextView) itemView.findViewById(R.id.tv_horario_horario_termino);
        mNomeMateria = (TextView) itemView.findViewById(R.id.tv_horario_nome_materia);
        mNomeProfessor = (TextView) itemView.findViewById(R.id.tv_horario_professor_materia);
    }

    public void onBind(Horario horario) {
        mHorarioInicial.setText(horario.getHorarioInicio().toString());
        mHorarioTermino.setText(horario.getHorarioTermino().toString());
        mNomeMateria.setText(horario.getDisciplina().getNome());
        mNomeProfessor.setText(horario.getDisciplina().getProfessor());
    }
}

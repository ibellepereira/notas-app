package org.lema.notasapp.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import org.lema.notasapp.R;
import org.lema.notasapp.modelo.Grade;

/**
 * Created by igor on 14/11/16.
 */

public class GradeViewHolder extends ParentViewHolder{

    private TextView mDiaDaSemanaTextView;

    public GradeViewHolder(@NonNull View itemView) {
        super(itemView);
        mDiaDaSemanaTextView = (TextView) itemView.findViewById(R.id.tv_horario_dia_semana);
    }

    public void onBind(@NonNull Grade grade) { mDiaDaSemanaTextView.setText(grade.getDiaDaSemana());}

    public TextView getmDiaDaSemanaTextView() {
        return mDiaDaSemanaTextView;
    }

    public void setmDiaDaSemanaTextView(TextView mDiaDaSemanaTextView) {
        this.mDiaDaSemanaTextView = mDiaDaSemanaTextView;
    }
}

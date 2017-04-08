package org.lema.notasapp.viewholder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.MateriaDto;
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
    final TextView periodoMateria;
    final TextView codigoMateria;
    final TextView btnSugerir;

    public MateriaViewHolder(Activity activity, View itemView) {
        super(itemView);

        this.activity = activity;
        av1 = (TextView) itemView.findViewById(R.id.av1);
        av2 = (TextView) itemView.findViewById(R.id.av2);
        av3 = (TextView) itemView.findViewById(R.id.av3);
        media = (TextView) itemView.findViewById(R.id.media);
        nomeMateria = (TextView) itemView.findViewById(R.id.disciplina);
        periodoMateria = (TextView) itemView.findViewById(R.id.periodoMateria);
        codigoMateria = (TextView) itemView.findViewById(R.id.codigoMateria);
        btnSugerir = (TextView) itemView.findViewById(R.id.btn_sugerir);

    }

    public void onBind(final MateriaDto materia) {

        if (materia.getNotaDaAv1() == 10.0) av1.setText("10"); else av1.setText(String.valueOf(materia.getNotaDaAv1()));
        if (materia.getNotaDaAv2() == 10.0) av2.setText("10"); else av2.setText(String.valueOf(materia.getNotaDaAv2()));
        if (materia.getNotaDaAv3() == 10.0) av3.setText("10"); else av3.setText(String.valueOf(materia.getNotaDaAv3()));
        if (materia.getMedia() == 10.0) media.setText("10"); else media.setText(String.valueOf(materia.getMedia()));
        nomeMateria.setText(String.valueOf(materia.getNome()));




        btnSugerir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                final EditText input = new EditText(activity);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.dialog_materia_message + " " + materia.getNome())
                        .setTitle(R.string.dialog_materia_title)
                        .setPositiveButton(R.string.dialog_materia_positive_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                // User clicked OK, so save the mSelectedItems results somewhere
                                // or return them to the component that opened the dialog
                                //...
                                Intent irParaSugestao = new Intent(MateriaViewHolder.this.activity, SugestaoNomeMateriaActivity.class);
                                irParaSugestao.putExtra("materia", materia);
                                irParaSugestao.putExtra("nomeSugerido", input.getText().toString());


                                activity.startActivity(irParaSugestao);
                                //activity.sugerir(materia, input.getText().toString(), activity);
                                //Toast.makeText(activity, "Sugestão "+ input.getText() +" enviada com sucesso!", Toast.LENGTH_LONG).show();
                               /* AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                builder.setMessage(R.string.dialog_materia_response_message_sucess)
                                        .setTitle(R.string.dialog_materia_response_title_sucess)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int id) {
                                                dialogInterface.dismiss();
                                            }
                                        });

                                AlertDialog dialog = builder.create();
                                dialog.show();*/


                            }
                        }).setNegativeButton(R.string.dialog_materia_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        //...
                        dialogInterface.dismiss();
                    }
                });

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        periodoMateria.setText(materia.getAno());
        codigoMateria.setText(materia.getCodigo());
    }

}

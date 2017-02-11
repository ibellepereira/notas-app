package org.lema.notasapp.ui.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Classe utilitaria para isolar e facilitar a utilização de Dialogos
 */
public class DialogUtils {

    private Context context;
    private AlertDialog.Builder builder;

    public DialogUtils(Context context) {
        this.context = context;
        this.builder = new AlertDialog.Builder(context);
    }

    public void show(final DialogMessage message) {
        builder
                .setCancelable(false)
                     /* TODO: extract strings to strings.xml */
                .setMessage(message.getText())
                .setTitle("Falha")
                .setPositiveButton("Tentar novamente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        message.getListener().onRetry();
                    }
                }).create().show();

    }

    public void showCancel(final DialogMessage message) {
        builder
                .setCancelable(false)
                     /* TODO: extract strings to strings.xml */
                .setMessage(message.getText())
                .setTitle("Falha")
                .setPositiveButton("Tentar novamente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        message.getListener().onRetry();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                        message.getListenerCancel().onCancel();
                }
                }).create().show();
    }

    public void showCancelable(final DialogMessage message) {
        builder
                .setCancelable(true)
                     /* TODO: extract strings to strings.xml */
                .setMessage(message.getText())
                .setTitle("Falha").create().show();

    }

}
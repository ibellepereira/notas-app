package org.lema.notasapp.ui.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Classe utilitaria para isolar e facilitar a utilização de Dialogos
 */
public class DialogUtils {

    private Context context;

    public DialogUtils(Context context) {
        this.context = context;
    }

//    public void showConnectionError(final OnRetryListener successListener) {
//        new AlertDialog.Builder(context)
//                .setCancelable(false)
//                     /* TODO: extract strings to strings.xml */
//                .setMessage("Não foi possível contactar o servidor, verifique seu acesso a Internet")
//                .setTitle("Sem conexão")
//                .setPositiveButton("Tentar novamente", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        successListener.onRetry();
//                    }
//                }).create().show();
//
//    }
//
//    public void showConnectionLost(final OnRetryListener successListener) {
//        new AlertDialog.Builder(context)
//                .setCancelable(false)
//                     /* TODO: extract strings to strings.xml */
//                .setMessage("Falha durante a conexão com o servidor.")
//                .setTitle("Falha")
//                .setPositiveButton("Tentar novamente", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        successListener.onRetry();
//                    }
//                }).create().show();
//
//    }

    public void show(final DialogMessage message) {
        new AlertDialog.Builder(context)
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

}
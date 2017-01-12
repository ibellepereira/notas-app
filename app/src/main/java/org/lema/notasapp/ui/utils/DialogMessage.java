package org.lema.notasapp.ui.utils;

import org.lema.notasapp.infra.listener.OnCancelListener;
import org.lema.notasapp.infra.listener.OnRetryListener;

/**
 * Representa uma mensagem de um dialog. Possui um listener que será executado ao clicar em "Tentar Novamente"
 */
public class DialogMessage {
    private String text;
    private OnRetryListener listener;
    private OnCancelListener listenerCancel;

    public DialogMessage(String text, OnRetryListener listener) {
        this.text = text;
        this.listener = listener;
    }

    public DialogMessage(String text, OnRetryListener listener, OnCancelListener listenerCancel) {
        this.text = text;
        this.listener = listener;
        this.listenerCancel = listenerCancel;
    }

    public DialogMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public OnRetryListener getListener() {
        return listener;
    }

    public OnCancelListener getListenerCancel() {
        return  listenerCancel;
    }
}

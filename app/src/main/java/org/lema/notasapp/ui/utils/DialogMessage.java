package org.lema.notasapp.ui.utils;

import org.lema.notasapp.infra.listener.OnRetryListener;

/**
 * Representa uma mensagem de um dialog. Possui um listener que será executado ao clicar em "Tentar Novamente"
 */
public class DialogMessage {
    private String text;
    private OnRetryListener listener;

    public DialogMessage(String text, OnRetryListener listener) {
        this.text = text;
        this.listener = listener;
    }

    public String getText() {
        return text;
    }

    public OnRetryListener getListener() {
        return listener;
    }
}

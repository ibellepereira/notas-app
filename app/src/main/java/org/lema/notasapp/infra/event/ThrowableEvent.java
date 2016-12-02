package org.lema.notasapp.infra.event;

import org.lema.notasapp.infra.listener.OnRetryListener;

/**
 * Created by leonardocordeiro on 27/11/16.
 */
public class ThrowableEvent {
    public Throwable exception;
    public OnRetryListener listener;

    public ThrowableEvent(Throwable exception) {
        this.exception = exception;
    }
}

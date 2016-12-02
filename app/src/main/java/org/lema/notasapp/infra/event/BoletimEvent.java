package org.lema.notasapp.infra.event;

import org.lema.notasapp.domain.model.Boletim;

/**
 * Created by leonardocordeiro on 24/11/16.
 */
public class BoletimEvent {
    public Boletim boletim;

    public BoletimEvent(Boletim boletim) {
        this.boletim = boletim;
    }
}

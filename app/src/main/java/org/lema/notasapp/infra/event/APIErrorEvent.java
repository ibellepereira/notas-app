package org.lema.notasapp.infra.event;

import org.lema.notasapp.infra.error.APIError;

/**
 * Created by leonardocordeiro on 24/11/16.
 */
public class APIErrorEvent {
    public APIError error;
    public APIErrorEvent(APIError error) {
        this.error = error;
    }
}

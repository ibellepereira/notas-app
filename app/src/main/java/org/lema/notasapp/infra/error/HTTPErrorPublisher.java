package org.lema.notasapp.infra.error;

import android.util.Log;
import org.greenrobot.eventbus.EventBus;
import org.lema.notasapp.infra.exception.*;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Dispara um evento para uma exceção.
 */

public class HTTPErrorPublisher {


    public void handle(Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            EventBus.getDefault().post(new NoConnectionException());
        } else if(throwable instanceof UnauthorizedException){
            EventBus.getDefault().post(new UnauthorizedException());
        } else if(throwable instanceof SocketException) {
            EventBus.getDefault().post(new ConnectionLostException());
        } else if(throwable instanceof UnavailableException) {
            EventBus.getDefault().post(new UnavailableException());
        } else {
            // colocar nas activities mae
            EventBus.getDefault().post(new GenericConnectionErrorException());
        }
        Log.e(this.getClass().getSimpleName() + " - erro", throwable.toString());
        throwable.printStackTrace();
    }

}

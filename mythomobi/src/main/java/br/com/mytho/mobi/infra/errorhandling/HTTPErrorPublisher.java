package br.com.mytho.mobi.infra.errorhandling;

import org.greenrobot.eventbus.EventBus;

import java.net.UnknownHostException;

import br.com.mytho.mobi.exception.ConnectionErrorException;
import br.com.mytho.mobi.exception.HTTPUnauthorizedException;

/**
 * Created by leonardocordeiro on 14/09/16.
 */

public class HTTPErrorPublisher {


    public void handle(Throwable throwable) {
        /* TODO: Chain of responsability */
        if (throwable instanceof UnknownHostException) {
            EventBus.getDefault().post(new ConnectionErrorException());
        } else if(throwable instanceof HTTPUnauthorizedException){
            EventBus.getDefault().post(new HTTPUnauthorizedException());
        } else {
            throw new RuntimeException(throwable);
        }

    }



}

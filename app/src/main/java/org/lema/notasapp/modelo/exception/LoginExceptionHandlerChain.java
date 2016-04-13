package org.lema.notasapp.modelo.exception;

/**
 * Created by leonardocordeiro on 09/03/16.
 */
public interface LoginExceptionHandlerChain {

    public void evaluate(Exception exception);
    public void setNext(LoginExceptionHandlerChain exception);

}

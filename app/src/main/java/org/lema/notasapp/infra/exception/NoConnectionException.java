package org.lema.notasapp.infra.exception;

/**
 * É lançada quando o cliente não possui conexão com a internet
 */
public class NoConnectionException extends RuntimeException {
    public NoConnectionException() {
        super("Não foi possível contactar o servidor, verifique seu acesso a Internet");
    }
}

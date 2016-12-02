package org.lema.notasapp.infra.exception;

/**
 * É lançada quando o status da resposta é 503
 */
public class UnavailableException extends RuntimeException {
    public UnavailableException() {
        super("Servidor indisponível no momento.");
    }

}

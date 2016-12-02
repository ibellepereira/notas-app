package org.lema.notasapp.infra.exception;

/**
 * É lançada quando a conexão é realizada porém perdida um momento depois
 */
public class ConnectionLostException extends RuntimeException {
    public ConnectionLostException() {
        super("Falha durante a conexão com o servidor.");
    }
}

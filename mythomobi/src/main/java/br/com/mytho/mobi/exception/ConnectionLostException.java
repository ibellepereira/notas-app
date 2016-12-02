package br.com.mytho.mobi.exception;

/**
 * Created by leonardocordeiro on 14/09/16.
 */
public class ConnectionLost extends RuntimeException {
    public ConnectionLost() {
        super("Falha durante a conexão com o servidor.");
    }
}

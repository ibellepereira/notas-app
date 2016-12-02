package org.lema.notasapp.infra.exception;

/**
 * Lançado quando nenhuma das exceptions é capturada
 */
public class GenericConnectionErrorException extends RuntimeException {
    public GenericConnectionErrorException() {
        super("Houve um erro.");
    }
}

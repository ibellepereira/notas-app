package org.lema.notasapp.infra.exception;

/**
 * É lançada quando o status da resposta é 401.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Matricula ou senha inválida.");
    }
}

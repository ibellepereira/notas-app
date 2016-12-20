package org.lema.notasapp.infra.error;

/**
 * Objeto que representa um erro retornado pela API
 */
public class APIError {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

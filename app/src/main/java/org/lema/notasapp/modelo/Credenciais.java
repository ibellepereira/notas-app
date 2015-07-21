package org.lema.notasapp.modelo;

/**
 * Created by leonardocordeiro on 21/07/15.
 */
public class Credenciais {

    private String matricula;
    private String senha;

    public Credenciais(String matricula, String senha) {
        this.matricula = matricula;
        this.senha = senha;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getSenha() {
        return senha;
    }

}

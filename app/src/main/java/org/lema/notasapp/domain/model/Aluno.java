package org.lema.notasapp.domain.model;

import java.io.Serializable;

/**
 * Created by leonardocordeiro on 21/07/15.
 */
public class Aluno implements Serializable {

    private String matricula;
    private String senha;

    public Aluno(String matricula, String senha) {
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

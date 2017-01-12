package org.lema.notasapp.domain.model;

import java.io.Serializable;

/**
 * Created by leonardocordeiro on 21/07/15.
 */
public class Aluno implements Serializable {

    private String nome;
    private String matricula;
    private String senha;

    public Aluno() {
    }

    public Aluno(String matricula, String senha) {
        this.matricula = matricula;
        this.senha = senha;
    }
    public Aluno(String matricula, String senha, String nome) {
        this.matricula = matricula;
        this.senha = senha;
        this.nome = nome;
    }
    public String getMatricula() {
        return matricula;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

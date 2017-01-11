package org.lema.notasapp.domain.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by leonardocordeiro on 16/12/16.
 */
public class Sugestao {
    private AlunoDto aluno;

    @Deprecated
    public Sugestao() {
    }

    public AlunoDto getAluno() {
        return aluno;
    }

    public void setAluno(AlunoDto aluno) {
        this.aluno = aluno;
    }

}

package org.lema.notasapp.domain.model;

import java.util.List;

/**
 * Created by leonardocordeiro on 12/10/16.
 */
public class Boletim {

    private List<Materia> materias;
    private Aluno aluno;

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}

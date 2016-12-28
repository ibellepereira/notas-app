package org.lema.notasapp.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by leonardocordeiro on 12/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Boletim {


    private List<Materia> materias;
    private Aluno aluno;

    public Boletim() {
    }

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

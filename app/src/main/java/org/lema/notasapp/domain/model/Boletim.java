package org.lema.notasapp.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by leonardocordeiro on 12/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Boletim {


    private List<MateriaDto> materias;
    private Aluno aluno;

    public Boletim() {
    }

    public List<MateriaDto> getMaterias() {
        return materias;
    }

    public void setMaterias(List<MateriaDto> materias) {
        this.materias = materias;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}

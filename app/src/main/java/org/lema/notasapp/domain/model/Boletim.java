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

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
}

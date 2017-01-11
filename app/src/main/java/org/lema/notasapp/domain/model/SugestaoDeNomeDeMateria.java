package org.lema.notasapp.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by leonardocordeiro on 09/01/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SugestaoDeNomeDeMateria extends Sugestao {

    private String nomeCorreto;
    private Materia materia;

    public SugestaoDeNomeDeMateria(Materia materia, String nomeCorreto) {
        this.nomeCorreto = nomeCorreto;
        this.materia = materia;
    }

    public SugestaoDeNomeDeMateria() {
    }

    public String getNomeCorreto() {
        return nomeCorreto;
    }

    public void setNomeCorreto(String nomeCorreto) {
        this.nomeCorreto = nomeCorreto;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
}
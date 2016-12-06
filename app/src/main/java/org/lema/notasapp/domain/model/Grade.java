package org.lema.notasapp.domain.model;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

/**
 * Created by igor on 14/11/16.
 */

public class Grade implements Parent<Horario> {

    private String diaDaSemana;
    private List<Horario> horarios;

    public Grade(String diaDaSemana, List<Horario> horarios){
        this.diaDaSemana = diaDaSemana;
        this.horarios = horarios;
    }

    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public Horario getHorario(int position) { return horarios.get(position); }

    @Override
    public List<Horario> getChildList() {
        return horarios;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return true;
    }
}

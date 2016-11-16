package org.lema.notasapp.modelo;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by igor on 11/11/16.
 */

public class Horario {

    private Materia disciplina;
    private String periodo;
    private Date horarioInicio;
    private Date horarioTermino;

    public Horario(Materia disciplina, String periodo, Date horarioInicio, Date horarioTermino) {
        this.disciplina = disciplina;
        this.periodo = periodo;
        this.horarioInicio = horarioInicio;
        this.horarioTermino = horarioTermino;
    }

    public Materia getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Materia disciplina) {
        this.disciplina = disciplina;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getHorarioInicio() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(horarioInicio);
    }

    public void setHorarioInicio(Date horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioTermino() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(horarioTermino);
    }

    public void setHorarioTermino(Date horarioTermino) {
        this.horarioTermino = horarioTermino;
    }


}

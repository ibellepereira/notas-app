package org.lema.notasapp.domain.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by leonardocordeiro on 16/12/16.
 */
public class Sugestao {
    private String id;
    private String texto;

    @JsonProperty("tipo")
    private TipoDeSugestao tipoDeSugestao;

    @Deprecated
    public Sugestao() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public TipoDeSugestao getTipoDeSugestao() {
        return tipoDeSugestao;
    }

    public void setTipoDeSugestao(TipoDeSugestao tipoDeSugestao) {
        this.tipoDeSugestao = tipoDeSugestao;
    }
}

package org.lema.notasapp.domain.model;

import java.io.Serializable;

/**
 * Created by igor on 08/03/17.
 */
public class Autor implements Serializable{

    private String nome;
    private String descricao;
    private String linkParaFoto;

    public Autor(String nome) {
        this.nome = nome;
    }

    public Autor() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLinkParaFoto() {
        return linkParaFoto;
    }

    public void setLinkParaFoto(String linkParaFoto) {
        this.linkParaFoto = linkParaFoto;
    }
}

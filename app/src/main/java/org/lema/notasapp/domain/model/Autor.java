package org.lema.notasapp.domain.model;

/**
 * Created by igor on 08/03/17.
 */
public class Autor {

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

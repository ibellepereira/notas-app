package org.lema.notasapp.domain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by igor on 08/03/17.
 */

public class Post implements Serializable{

    private String titulo;
    private String texto;
    private Date dataPostagem;
    private String linkParaFoto;
    private Autor autor;
    private List<Like> likes;
    private List<TagPost> tag;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(Date dataPostagem) {
        this.dataPostagem = dataPostagem;
    }

    public String getLinkParaFoto() {
        return linkParaFoto;
    }

    public void setLinkParaFoto(String linkParaFoto) {
        this.linkParaFoto = linkParaFoto;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<TagPost> getTag() {
        return tag;
    }

    public void setTag(List<TagPost> tag) {
        this.tag = tag;
    }
}

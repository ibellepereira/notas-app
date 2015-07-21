package org.lema.notasapp.modelo;

/**
 * Created by leonardocordeiro on 18/07/15.
 */
public class Materia {

    private String nome;
    private Double av1;
    private Double av2;
    private Double av3;
    private Double media;
    private StatusDeAprovacao aprovacao;


    public Materia(String nome, Double av1, Double av2, Double av3, Double media) {
        this.nome = nome;
        this.av1 = av1;
        this.av2 = av2;
        this.av3 = av3;
        this.media = media;

        if(media >= 6)
            aprovacao = StatusDeAprovacao.APROVADO;
        else if(media <= 6 && av3 != 0)
            aprovacao = StatusDeAprovacao.REPROVADO;
        else if(av2 == 0 && av3 == 0)
            aprovacao = StatusDeAprovacao.SEM_NOTA;
        else if(media <= 6 && av3 == 0)
            aprovacao = StatusDeAprovacao.ESPERANDO_AV3;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getAv1() {
        return av1;
    }

    public void setAv1(Double av1) {
        this.av1 = av1;
    }

    public Double getAv2() {
        return av2;
    }

    public void setAv2(Double av2) {
        this.av2 = av2;
    }

    public Double getAv3() {
        return av3;
    }

    public void setAv3(Double av3) {
        this.av3 = av3;
    }

    public Double getMedia() {
        return media;
    }

    public StatusDeAprovacao getStatus() {
        return aprovacao;
    }

}

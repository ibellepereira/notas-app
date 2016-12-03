package org.lema.notasapp.modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by leonardocordeiro on 18/07/15.
 */

public class Materia implements Parcelable {

    private String nome;
    private Double notaDaAv1;
    private Double notaDaAv2;
    private Double notaDaAv3;
    private Double media;
    private String ano;
    private StatusDeAprovacao aprovacao;
    private String professor;

    public Materia() {

    }

    public Materia(String nome, String professor) {
        this.nome = nome;
        this.professor = professor;
    }

    public Materia(String nome, Double av1, Double av2, Double av3, Double media) {
        this.nome = nome;
        this.notaDaAv1 = av1;
        this.notaDaAv2 = av2;
        this.notaDaAv3 = av3;
        this.media = media;

    }

    protected Materia(Parcel in) {
        nome = in.readString();
        ano = in.readString();
    }

    public static final Creator<Materia> CREATOR = new Creator<Materia>() {
        @Override
        public Materia createFromParcel(Parcel in) {
            return new Materia(in);
        }

        @Override
        public Materia[] newArray(int size) {
            return new Materia[size];
        }
    };

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Double getNotaDaAv1() {
        return notaDaAv1;
    }

    public void setNotaDaAv1(Double av1) {
        this.notaDaAv1 = av1;
    }

    public Double getNotaDaAv2() {
        return notaDaAv2;
    }

    public void setNotaDaAv2(Double av2) {
        this.notaDaAv2 = av2;
    }

    public Double getNotaDaAv3() {
        return notaDaAv3;
    }

    public void setNotaDaAv3(Double av3) {
        this.notaDaAv3 = av3;
    }

    public Double getMedia() {
        return media;
    }

    public StatusDeAprovacao getStatus() {
        if(media >= 6)
            aprovacao = StatusDeAprovacao.APROVADO;
        else if(media <= 6 && notaDaAv3 != 0)
            aprovacao = StatusDeAprovacao.REPROVADO;
        else if(notaDaAv3 == 0 && notaDaAv3 == 0)
            aprovacao = StatusDeAprovacao.SEM_NOTA;
        else if(media <= 6 && notaDaAv3 == 0)
            aprovacao = StatusDeAprovacao.ESPERANDO_AV3;
        return aprovacao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeString(ano);
    }
}

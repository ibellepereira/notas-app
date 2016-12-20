package org.lema.notasapp.domain.model.factory;

import org.lema.notasapp.domain.model.Materia;
import org.lema.notasapp.domain.model.Sugestao;
import org.lema.notasapp.domain.model.TipoDeSugestao;

/**
 * Created by leonardocordeiro on 16/12/16.
 */
public class SugestaoFactory {

    public static Sugestao criarSugestaoDeNomeParaMateria(Materia materia, String nomeCorreto) {
        Sugestao sugestao = new Sugestao();
        sugestao.setTexto("O nome da matéria de código (" + materia.getCodigo() + ") é (" + nomeCorreto + ")");
        sugestao.setTipoDeSugestao(TipoDeSugestao.CORRECAO_DE_NOME_DA_MATERIA);

        return sugestao;
    }

}

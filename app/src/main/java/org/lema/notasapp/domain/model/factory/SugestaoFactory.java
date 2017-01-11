package org.lema.notasapp.domain.model.factory;

import org.json.JSONException;
import org.json.JSONObject;
import org.lema.notasapp.domain.model.Aluno;
import org.lema.notasapp.domain.model.MateriaDto;
import org.lema.notasapp.domain.model.Sugestao;

/**
 * Created by leonardocordeiro on 16/12/16.
 */
public class SugestaoFactory {

    public static Sugestao criarSugestaoDeNomeParaMateria(MateriaDto materia, String nomeCorreto, Aluno aluno) {
        Sugestao sugestao = new Sugestao();

        return sugestao;
    }

    private static String toJson(MateriaDto materia, String nomeCorreto, Aluno aluno) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nomeCorreto", nomeCorreto);
            jsonObject.put("nomeErrado", materia.getNome());
            jsonObject.put("codigo", materia.getCodigo());
            jsonObject.put("aluno", new JSONObject().put("nome", aluno.getNome())
                    .put("matricula", aluno.getMatricula()));

            return jsonObject.toString();

        } catch(JSONException json) {
            throw new RuntimeException(json);
        }
    }

}

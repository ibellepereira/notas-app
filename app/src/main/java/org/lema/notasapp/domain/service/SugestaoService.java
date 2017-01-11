package org.lema.notasapp.domain.service;

import org.lema.notasapp.domain.model.Sugestao;
import org.lema.notasapp.domain.model.SugestaoDeNomeDeMateria;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by leonardocordeiro on 16/12/16.
 */
public interface SugestaoService {

    @POST("sugestao/correcao-nome-materia")
    Call<SugestaoDeNomeDeMateria> enviar(@Body SugestaoDeNomeDeMateria sugestao);

}

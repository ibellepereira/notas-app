package org.lema.notasapp.domain.service;

import org.lema.notasapp.domain.model.Aluno;
import org.lema.notasapp.domain.model.Boletim;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by leonardocordeiro on 12/10/16.
 */

public interface BoletimService {
        @POST("boletim")
        Call<Boletim> getBoletimDo(@Body Aluno aluno);
}

package org.lema.notasapp.domain.service;

import org.lema.notasapp.modelo.Aluno;
import org.lema.notasapp.modelo.Boletim;

import br.com.mytho.mobi.oauth2.service.OAuth2BaseService;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by leonardocordeiro on 12/10/16.
 */

public class BoletimService extends OAuth2BaseService<BoletimService.BoletimServiceContract> {

    public interface BoletimServiceContract {

        @POST("boletim")
        Call<Boletim> getBoletim(@Body Aluno aluno);

    }
}

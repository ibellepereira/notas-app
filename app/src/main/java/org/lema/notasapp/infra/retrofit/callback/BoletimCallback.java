package org.lema.notasapp.infra.retrofit.callback;

import org.greenrobot.eventbus.EventBus;
import org.lema.notasapp.infra.event.BoletimEvent;
import org.lema.notasapp.domain.model.Boletim;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by leonardocordeiro on 24/11/16.
 */
public class BoletimCallback extends OAuthCallback<Boletim> {

    @Override
    public void handle(Call<Boletim> call, Response<Boletim> response) {
        EventBus.getDefault().post(new BoletimEvent(response.body()));
    }
}

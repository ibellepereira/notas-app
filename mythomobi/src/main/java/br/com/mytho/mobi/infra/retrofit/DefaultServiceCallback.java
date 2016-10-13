package br.com.mytho.mobi.infra.retrofit;

import java.util.List;

import br.com.mytho.mobi.exception.HTTPUnauthorizedException;
import br.com.mytho.mobi.infra.errorhandling.HTTPErrorPublisher;
import br.com.mytho.mobi.model.Event;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardocordeiro on 14/09/16.
 */

public class DefaultServiceCallback<T> implements Callback<T> {
    private final OnResponse responseCallback;

    public DefaultServiceCallback(OnResponse responseCallback) {
        this.responseCallback = responseCallback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful())
            responseCallback.onResponse(call, response);
        else if(response.code() == 401) onFailure(call, new HTTPUnauthorizedException());
        else throw new RuntimeException(Integer.toString(response.code()));
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        new HTTPErrorPublisher().handle(t);

    }

    public interface OnResponse<T> {
        void onResponse(Call<T> call, Response<T> response);
    }

}

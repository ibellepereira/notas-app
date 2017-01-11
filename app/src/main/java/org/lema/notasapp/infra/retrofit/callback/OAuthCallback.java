package org.lema.notasapp.infra.retrofit.callback;

import android.util.Log;
import org.lema.notasapp.infra.retrofit.callback.chain.*;
import org.lema.notasapp.infra.error.HTTPErrorPublisher;
import retrofit2.*;

/**
 * Callback abstrato para isolar tratamento de exceções e respostas de erro.
 * Os callbacks do Retrofit deverão herdar desta classe.
 */

public abstract class OAuthCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        ResponseChain successful = new SuccessfulResponseChain(this);
        ResponseChain unauthorized = new UnauthorizedResponseChain(this);
        ResponseChain unavailable = new UnavailableResponseChain(this);
        ResponseChain apiError = new APIErrorResponseChain();

        successful.setNext(unauthorized);
        unauthorized.setNext(unavailable);
        unavailable.setNext(apiError);

        successful.handler(call, response);

//        if(response.isSuccessful()) handle(call, response);
//        else if(response.code() == 401) onFailure(call, new UnauthorizedException());
//        else if(response.code() == 503) onFailure(call, new UnavailableException());
//        else {
//            ResponseBody responseBody = response.errorBody();
//
//            try {
//                Converter<ResponseBody, APIError> converter = new Retrofit.Builder().build().responseBodyConverter(APIError.class, new Annotation[0]);
//                APIError error = converter.convert(responseBody);
//                eventBus.post(new APIErrorEvent(error));
//            } catch (IOException e) {
//                Log.e("exception", e.toString() + "handle() - BoletimConverter");
//            }
//        }
    }


    @Override
    public void onFailure(Call<T> call, Throwable t) {
        new HTTPErrorPublisher().handle(t);

    }

    public abstract void handle(Call<T> call, Response<T> response);

}

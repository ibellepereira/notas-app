package org.lema.notasapp.infra.retrofit.callback.chain;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by leonardocordeiro on 29/11/16.
 */
public interface ResponseChain {

    void handler(Call call, Response response);
    void setNext(ResponseChain chain);

}

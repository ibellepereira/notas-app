package org.lema.notasapp.infra.retrofit.callback.chain;

import org.lema.notasapp.infra.retrofit.callback.OAuthCallback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by leonardocordeiro on 29/11/16.
 */
public class SuccessfulResponseChain implements ResponseChain {
    private OAuthCallback callback;
    private ResponseChain next;
    
    public SuccessfulResponseChain(OAuthCallback callback) { 
        this.callback = callback;
    }

    @Override
    public void handler(Call call, Response response) {
        if(response.isSuccessful()) callback.handle(call, response);
        else if(next != null) next.handler(call, response);
    }

    @Override
    public void setNext(ResponseChain chain) {
        this.next = chain;
    }
}

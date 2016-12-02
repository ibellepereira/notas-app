package org.lema.notasapp.infra.retrofit.callback.chain;

import org.lema.notasapp.infra.exception.UnauthorizedException;
import org.lema.notasapp.infra.retrofit.callback.OAuthCallback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by leonardocordeiro on 29/11/16.
 */
public class UnauthorizedResponseChain implements ResponseChain {
    
    private ResponseChain next;
    private OAuthCallback callback;

    public UnauthorizedResponseChain(OAuthCallback callback) {
        this.callback = callback;
    }

    @Override
    public void handler(Call call, Response response) {
        if(response.code() == 401) callback.onFailure(call, new UnauthorizedException());
        else if(next != null) next.handler(call, response);
    }

    @Override
    public void setNext(ResponseChain next) {
        this.next = next;
    }
}

package org.lema.notasapp.infra.interceptor;

import java.io.IOException;

import android.content.Context;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.lema.notasapp.infra.oauth2.model.AccessToken;
import org.lema.notasapp.infra.oauth2.repository.AccessTokenRepository;

/**
 * Interceptor que coloca o Authorization Bearer no request
 */

public class TokenInterceptor implements Interceptor {
    private Context context;

    public TokenInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        final AccessToken token = new AccessTokenRepository(context).retrieve();

        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token.getCode())
                .method(original.method(), original.body());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}


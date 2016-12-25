package org.lema.notasapp.infra.oauth2.service;

import android.util.Base64;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.lema.notasapp.infra.oauth2.OAuth2;
import org.lema.notasapp.infra.oauth2.model.AccessToken;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public interface OAuth2AccessTokenService {
    String GRANT_TYPE = "client_credentials";

    @FormUrlEncoded
    @POST("oauth/token?grant_type=" + GRANT_TYPE)
    Call<AccessToken> getAccessToken(@Field("scope") String scope);
    class Builder {
        private Properties properties = OAuth2.getProperties();

        private final String CREDENTIALS = properties.get(OAuth2.CLIENT_ID) + ":" + properties.get(OAuth2.CLIENT_SECRET);
        private final String encodedCredentials = Base64.encodeToString(CREDENTIALS.getBytes(), Base64.NO_WRAP);

        private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        private Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.162.178.76:8080/notasapp-backend/")
                .addConverterFactory(JacksonConverterFactory.create());

        public OAuth2AccessTokenService build() {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder builder = original.newBuilder()
                           .addHeader("Authorization", "Basic " + encodedCredentials)
                           .method(original.method(), original.body());

                    Request request = builder.build();
                    return chain.proceed(request);
                }
            });

            OkHttpClient okHttpClient = httpClient.build();

            httpClient.connectTimeout(20, TimeUnit.SECONDS);
            httpClient.readTimeout(30, TimeUnit.SECONDS);

            Retrofit retrofit = builder.client(okHttpClient)
                                       .build();

            return retrofit.create(OAuth2AccessTokenService.class);

        }

    }
}
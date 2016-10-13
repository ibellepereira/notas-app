package br.com.mytho.mobi.oauth2.service;

import android.util.Base64;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Properties;

import br.com.mytho.mobi.exception.UnavailableException;
import br.com.mytho.mobi.oauth2.OAuth2;
import br.com.mytho.mobi.oauth2.model.AccessToken;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OAuth2AccessTokenService {
    String GRANT_TYPE = "client_credentials";

    @FormUrlEncoded
    @POST("oauth/token?grant_type=" + GRANT_TYPE)
    Call<AccessToken> getAccessToken(@Field("scope") String scope);
    class Builder {
        private Properties properties = OAuth2.getProperties();


        public Builder() {
            if(properties == null) throw new IllegalArgumentException("did you call 'oauth2Properties(properties)' method in your Oauth2Activity?");

            if(properties.get(OAuth2.CLIENT_ID) == null ||
               properties.get(OAuth2.CLIENT_SECRET) == null ||
               properties.get(OAuth2.BASE_URL) == null) throw new IllegalArgumentException("did you set all properties?");
        }

        private final String CREDENTIALS = properties.get(OAuth2.CLIENT_ID) + ":" + properties.get(OAuth2.CLIENT_SECRET);
        private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        private Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(properties.getProperty(OAuth2.BASE_URL))
                .addConverterFactory(JacksonConverterFactory.create());
        private String encodedCredentials = Base64.encodeToString(CREDENTIALS.getBytes(), Base64.NO_WRAP);

        public OAuth2AccessTokenService build() {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder builder = original.newBuilder()
                           .addHeader("Authorization", "Basic " + encodedCredentials)
                           .method(original.method(), original.body());

                    Request request = builder.build();
                    try {
                        return chain.proceed(request);
                    } catch(SocketTimeoutException exception) {
                        EventBus.getDefault().post(new UnavailableException());
                        return null;
                    }
                }
            });

            OkHttpClient okHttpClient = httpClient.build();
            Retrofit retrofit = builder.client(okHttpClient)
                                       .build();

            return retrofit.create(OAuth2AccessTokenService.class);

        }

    }
}
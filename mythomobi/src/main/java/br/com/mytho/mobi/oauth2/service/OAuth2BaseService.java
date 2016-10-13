package br.com.mytho.mobi.oauth2.service;


import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.SocketTimeoutException;

import br.com.mytho.mobi.exception.HTTPUnauthorizedException;
import br.com.mytho.mobi.exception.UnavailableException;
import br.com.mytho.mobi.oauth2.OAuth2;
import br.com.mytho.mobi.oauth2.model.AccessToken;
import br.com.mytho.mobi.oauth2.repository.AccessTokenRepository;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class OAuth2BaseService<T> {

        private Context context;
        private Class serviceClass;

        /*TODO: ler de um properties */
        private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        private Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(OAuth2.getProperties().getProperty(OAuth2.BASE_URL))
                .addConverterFactory(JacksonConverterFactory.create());

        public OAuth2BaseService context(Context context) {
            this.context = context;
            return this;
        }

        public OAuth2BaseService clazz(Class clazz) {
            this.serviceClass = clazz;
            return this;
        }

        public T build() {
            final AccessToken token = new AccessTokenRepository(context).retrieve();

            // TokenInterceptor
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization", "Bearer " + token.getCode())
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    try {
                        return chain.proceed(request);
                    } catch (SocketTimeoutException exception) {
                        EventBus.getDefault().post(new UnavailableException());
                        return null;
                    }
                }
            });

            Retrofit retrofit = builder.client(httpClient.build())
                    .build();

            return (T) retrofit.create(serviceClass);
        }
    }


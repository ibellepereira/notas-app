package org.lema.notasapp.infra.oauth2.service;


import android.content.Context;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.lema.notasapp.infra.oauth2.OAuth2;
import org.lema.notasapp.infra.oauth2.model.AccessToken;
import org.lema.notasapp.infra.oauth2.repository.AccessTokenRepository;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OAuth2BaseService<T> {

        private Context context;
        private Class serviceClass;

        private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        private Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://ec2-35-165-113-113.us-west-2.compute.amazonaws.com:8080/notasapp-backend/")
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
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization", "Bearer " + token.getCode())
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            httpClient.connectTimeout(20, TimeUnit.SECONDS);
            httpClient.readTimeout(30, TimeUnit.SECONDS);

            Retrofit retrofit = builder.client(httpClient.build())
                    .build();

            return (T) retrofit.create(serviceClass);
        }
    }


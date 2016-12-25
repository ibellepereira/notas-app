package org.lema.notasapp.infra;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by leonardocordeiro on 29/11/16.
 */
public class RetrofitUtils {

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://35.162.178.76:8080/notasapp-backend/v1/")
            .addConverterFactory(JacksonConverterFactory.create());

    public static Retrofit getInstance() {
        return builder.build();
    }

    public static Retrofit.Builder getBuilder() {
        return builder;
    }

}

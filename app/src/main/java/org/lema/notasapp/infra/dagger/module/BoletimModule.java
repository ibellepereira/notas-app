package org.lema.notasapp.infra.dagger.module;

import android.app.Application;

import org.lema.notasapp.domain.service.BoletimService;
import org.lema.notasapp.infra.RetrofitUtils;
import org.lema.notasapp.infra.interceptor.TokenInterceptor;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by leonardocordeiro on 15/11/16.
 */

@Module
public class BoletimModule {

    private Application application;

    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public BoletimModule(Application application) {
        this.application = application;
    }

    @Provides
    public BoletimService getBoletimService() {
        httpClient.addInterceptor(new TokenInterceptor(application));

        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);

        Retrofit retrofit = RetrofitUtils
                                .getBuilder()
                                .client(httpClient.build())
                                .build();

        return retrofit.create(BoletimService.class);
    }
}

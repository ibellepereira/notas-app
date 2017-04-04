package org.lema.notasapp.infra.dagger.module;

import android.app.Application;

import org.lema.notasapp.domain.service.PostService;
import org.lema.notasapp.infra.RetrofitUtils;
import org.lema.notasapp.infra.interceptor.TokenInterceptor;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by igor on 08/03/17.
 */

@Module
public class PostModule {

    private Application application;

    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public PostModule(Application application) {this.application = application;}

    @Provides
    public PostService getPostService() {
        httpClient.addInterceptor(new TokenInterceptor(application));

        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);

        Retrofit retrofit = RetrofitUtils
                                .getBuilder()
                                .client(httpClient.build())
                                .build();

        return retrofit.create(PostService.class);
    }
}

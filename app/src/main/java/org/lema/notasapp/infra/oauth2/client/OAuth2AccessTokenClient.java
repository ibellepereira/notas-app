package org.lema.notasapp.infra.oauth2.client;

import android.content.Context;
import android.util.Log;
import org.greenrobot.eventbus.EventBus;
import org.lema.notasapp.infra.exception.NoConnectionException;
import org.lema.notasapp.infra.oauth2.OAuth2;
import org.lema.notasapp.infra.oauth2.model.AccessToken;
import org.lema.notasapp.infra.oauth2.repository.AccessTokenRepository;
import org.lema.notasapp.infra.oauth2.service.OAuth2AccessTokenService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Created by leonardocordeiro on 14/09/16.
 */

public class OAuth2AccessTokenClient {

    private Context context;
    private AccessTokenRepository tokenRepository;
    private Properties properties = OAuth2.getProperties();

    public OAuth2AccessTokenClient(Context context) {
        this.context = context;

        this.tokenRepository = new AccessTokenRepository(context);
    }

    public void getAccessToken() {
        if (tokenRepository.empty()) {
            final OAuth2AccessTokenService oAuth2AccessTokenService = new OAuth2AccessTokenService.Builder().build();

            oAuth2AccessTokenService.getAccessToken(properties.getProperty(OAuth2.SCOPE))
                    .enqueue(new Callback<AccessToken>() {
                        @Override
                        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                            if(response.isSuccessful()) {
                                AccessToken accessToken = response.body();

                                Log.i("oauth2", "succeful accestoken");

                                tokenRepository.save(accessToken);
                                EventBus.getDefault().post(accessToken);
                            }
                        }

                        @Override
                        public void onFailure(Call<AccessToken> call, Throwable t) {
                            Log.i("oauth2", "not succeful accestoken " + t.toString());
                            if (t instanceof UnknownHostException) {
                                EventBus.getDefault().post(new NoConnectionException());
                            }
                        }
                    });

        } else {
            EventBus.getDefault().post(new AccessToken());
        }
    }

    public void retry() {
        tokenRepository.clear();
        getAccessToken();
    }

}

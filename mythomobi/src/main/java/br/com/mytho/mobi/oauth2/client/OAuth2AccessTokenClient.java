package br.com.mytho.mobi.oauth2.client;

import android.content.Context;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

import br.com.mytho.mobi.activity.AccessTokenActivity;
import br.com.mytho.mobi.exception.ConnectionErrorException;
import br.com.mytho.mobi.model.Event;
import br.com.mytho.mobi.oauth2.OAuth2;
import br.com.mytho.mobi.oauth2.model.AccessToken;
import br.com.mytho.mobi.oauth2.repository.AccessTokenRepository;
import br.com.mytho.mobi.oauth2.service.OAuth2AccessTokenService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardocordeiro on 14/09/16.
 */

public class OAuth2AccessTokenClient {

    private Context context;
    private AccessTokenRepository tokenRepository;
    private Properties properties = OAuth2.getProperties();

    public OAuth2AccessTokenClient(Context context) {
        this.context = context;

        if(properties.get(OAuth2.SCOPE) == null) throw new IllegalArgumentException("did you set 'OAuth2.SCOPE' property?");
        this.properties = properties;

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

                                tokenRepository.save(accessToken);
                                EventBus.getDefault().post(accessToken);
                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<AccessToken> call, Throwable t) {
                            if (t instanceof UnknownHostException) {
                                EventBus.getDefault().post(new ConnectionErrorException());
                            } else {
                                getAccessToken();
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

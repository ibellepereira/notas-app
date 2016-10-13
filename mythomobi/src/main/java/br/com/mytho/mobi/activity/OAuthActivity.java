package br.com.mytho.mobi.activity;

import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;

import br.com.mytho.mobi.activity.utils.DialogUtils;
import br.com.mytho.mobi.exception.ConnectionErrorException;
import br.com.mytho.mobi.exception.HTTPUnauthorizedException;
import br.com.mytho.mobi.exception.UnavailableException;
import br.com.mytho.mobi.oauth2.client.OAuth2AccessTokenClient;
import br.com.mytho.mobi.oauth2.model.AccessToken;

public abstract class OAuthActivity extends BaseActivity {

    private OAuth2AccessTokenClient oAuth2AccessTokenClient;
    protected DialogUtils dialogUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        oAuth2AccessTokenClient = new OAuth2AccessTokenClient(this);
        dialogUtils = new DialogUtils(this);

    }

    @Subscribe
    public void handle(HTTPUnauthorizedException exception) {
        oAuth2AccessTokenClient.retry();
    }

    private void getAccessToken() {
        this.oAuth2AccessTokenClient = new OAuth2AccessTokenClient(this);
        oAuth2AccessTokenClient.getAccessToken();
    }

    public abstract void onReceiveAccessToken(AccessToken accessToken);

    public abstract void handle(UnavailableException exception);

    public abstract void handle(ConnectionErrorException exception);

    @Override
    protected void onStop() {
        super.onStop();
    }


}

package org.lema.notasapp.ui.activity;

import android.os.Bundle;
import org.greenrobot.eventbus.Subscribe;
import org.lema.notasapp.infra.exception.*;
import org.lema.notasapp.infra.listener.OnRetryListener;
import org.lema.notasapp.infra.oauth2.client.OAuth2AccessTokenClient;
import org.lema.notasapp.infra.oauth2.model.AccessToken;
import org.lema.notasapp.ui.utils.DialogMessage;
import org.lema.notasapp.ui.utils.DialogUtils;

import java.net.SocketException;

/**
 * Deve implementar essa classe a primeira Activity da aplicação, que irá
 * buscar o AccessToken
 *
 * @author Leonardo Cordeiro
 * @version 1.0.0
 *
 */

public abstract class AccessTokenActivity extends BaseActivity {

    private OAuth2AccessTokenClient oAuth2AccessTokenClient;
    private DialogUtils dialogUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        oAuth2AccessTokenClient = new OAuth2AccessTokenClient(getApplication());
        dialogUtils = new DialogUtils(this);

        getAccessToken();

    }

    @Subscribe
    public void handle(UnavailableException exception) {
        getAccessToken();
    }

    @Subscribe
    public void handle(UnauthorizedException exception) {
        oAuth2AccessTokenClient.retry();
    }

    @Subscribe
    public void handle(SocketException exception) {
        dialogUtils.show(new DialogMessage(exception.getMessage(), new OnRetryListener() {
            @Override
            public void onRetry() {
                oAuth2AccessTokenClient.retry();
            }
        }));
    }

    @Subscribe
    public void handle(GenericConnectionErrorException exception) {
        oAuth2AccessTokenClient.getAccessToken();
    }

    @Subscribe
    public void handle(ConnectionLostException exception) {
        oAuth2AccessTokenClient.getAccessToken();
    }

    @Subscribe
    public void handle(NoConnectionException exception) {
        dialogUtils.show(new DialogMessage(exception.getMessage(), new OnRetryListener() {
            @Override
            public void onRetry() {
                oAuth2AccessTokenClient.getAccessToken();
            }
        }));
    }

    public abstract void onReceiveAccessToken(AccessToken accessToken);

    private void getAccessToken() {
        this.oAuth2AccessTokenClient = new OAuth2AccessTokenClient(this);
        oAuth2AccessTokenClient.getAccessToken();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}

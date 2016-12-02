package org.lema.notasapp.ui.activity;

import android.os.Bundle;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.lema.notasapp.infra.event.ThrowableEvent;
import org.lema.notasapp.infra.exception.*;
import org.lema.notasapp.infra.oauth2.client.OAuth2AccessTokenClient;
import org.lema.notasapp.infra.oauth2.model.AccessToken;

/**
 * Deve implementar essa classe a Activity que usará algum serviço
 * com OAuth2
 *
 * @author Leonardo Cordeiro
 * @version 1.0.0
 *
 */
public abstract class OAuthActivity extends BaseActivity {

    private OAuth2AccessTokenClient oAuth2AccessTokenClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        oAuth2AccessTokenClient = new OAuth2AccessTokenClient(getApplication());
    }

    @Subscribe
    public void handle(UnauthorizedException exception) {
        oAuth2AccessTokenClient.retry();
    }

    public abstract void onReceiveAccessToken(AccessToken accessToken);

    public abstract void handle(ThrowableEvent event);

    @Override
    @Subscribe
    public void handle(ConnectionLostException exception) {
        post(exception);
    }

    @Override
    @Subscribe
    public void handle(GenericConnectionErrorException exception) {
        post(exception);
    }

    @Override
    @Subscribe
    public void handle(NoConnectionException exception) {
        post(exception);
    }

    @Override
    @Subscribe
    public void handle(UnavailableException exception) {
        post(exception);
    }

    private void post(Exception exception) {
        EventBus.getDefault().post(new ThrowableEvent(exception));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}

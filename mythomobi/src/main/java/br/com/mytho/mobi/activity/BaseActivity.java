package br.com.mytho.mobi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.com.mytho.mobi.exception.ConnectionErrorException;
import br.com.mytho.mobi.exception.HTTPUnauthorizedException;
import br.com.mytho.mobi.exception.UnavailableException;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    public abstract void handle(UnavailableException exception);

    public abstract void handle(HTTPUnauthorizedException exception);

    public abstract void handle(ConnectionErrorException exception);

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}

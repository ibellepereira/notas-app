package org.lema.notasapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import org.greenrobot.eventbus.EventBus;
import org.lema.notasapp.infra.exception.*;
import org.lema.notasapp.ui.utils.DialogUtils;

/**
 * Activity raiz. Não herdar diretamente.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected DialogUtils dialogUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogUtils = new DialogUtils(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    public abstract void handle(NoConnectionException exception);
    public abstract void handle(ConnectionLostException exception);
    public abstract void handle(GenericConnectionErrorException exception);
    public abstract void handle(UnauthorizedException exception);
    public abstract void handle(UnavailableException exception);

}

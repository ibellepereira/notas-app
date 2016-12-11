package org.lema.notasapp.infra.app;

import android.app.Application;

import org.lema.notasapp.infra.dagger.component.BoletimComponent;
import org.lema.notasapp.infra.dagger.component.DaggerBoletimComponent;
import org.lema.notasapp.infra.dagger.module.BoletimModule;

/**
 * Created by leonardocordeiro on 15/11/16.
 */

public class NotasAppAplication extends Application {

    private BoletimComponent comp;

    // Multidex support
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

    @Override
    public void onCreate() {
        super.onCreate();

        comp = DaggerBoletimComponent.builder()
                                     .boletimModule(new BoletimModule(this)).build();
    }

    public BoletimComponent getComponent() {
        return comp;
    }
}

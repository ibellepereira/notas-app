package org.lema.notasapp.infra.dagger.component;

import org.lema.notasapp.ui.activity.BoletimActivity;
import org.lema.notasapp.infra.dagger.module.BoletimModule;

import dagger.Component;
import org.lema.notasapp.infra.dagger.module.InfraModule;
import org.lema.notasapp.ui.activity.MainActivity;
import org.lema.notasapp.ui.activity.SugestaoNomeMateriaActivity;

/**
 * Created by leonardocordeiro on 15/11/16.
 */

@Component(modules = { InfraModule.class, BoletimModule.class })
public interface BoletimComponent {

    void inject(BoletimActivity activity);
    void inject(SugestaoNomeMateriaActivity activity);
    void inject(MainActivity activity);

}

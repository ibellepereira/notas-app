package org.lema.notasapp.infra.dagger.module;

import dagger.Module;
import dagger.Provides;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by leonardocordeiro on 26/11/16.
 */
@Module
public class InfraModule {
    @Provides
    public EventBus getEventBus() {
        return EventBus.builder().build();
    }
}

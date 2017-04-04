package org.lema.notasapp.infra.dagger.component;

import org.lema.notasapp.infra.dagger.module.InfraModule;
import org.lema.notasapp.infra.dagger.module.PostModule;
import org.lema.notasapp.ui.activity.FeedActivity;

import dagger.Component;

/**
 * Created by igor on 08/03/17.
 */
@Component(modules = {InfraModule.class, PostModule.class})
public interface PostComponent {

    void inject(FeedActivity activity);
}

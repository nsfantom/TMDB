package tm.fantom.tmdb.di.component;

import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import tm.fantom.tmdb.di.module.ApiModule;
import tm.fantom.tmdb.repo.SharedStorage;
import tm.fantom.tmdb.ui.MainActivity;
import tm.fantom.tmdb.ui.base.BaseApiPresenter;


@Singleton
@Component(modules = {ApiModule.class})
public interface AppComponent {

    SharedStorage sharedStorage();

    Retrofit retrofit();

    Resources resources();

    void inject(BaseApiPresenter baseApiPresenter);

    void inject(MainActivity mainActivity);
}

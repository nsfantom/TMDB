package tm.fantom.tmdb.ui.base;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.StringRes;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;
import tm.fantom.tmdb.TmApp;
import tm.fantom.tmdb.api.req.SimpleApi;
import tm.fantom.tmdb.repo.SharedStorage;

public abstract class BaseApiPresenter {

    @Inject
    protected SimpleApi simpleApi;

    @Inject
    protected SharedStorage sharedStorage;

    @Inject
    protected Resources resources;

    @Inject
    protected Context appContext;

    protected CompositeDisposable disposables;

    protected abstract BaseContract.View getView();

    protected void clearDisposables() {
        if (null != disposables && !disposables.isDisposed()) {
            disposables.dispose();
            disposables = null;
        }
    }

    protected CompositeDisposable getCompositeDisposable() {
        if (disposables == null || disposables.isDisposed()) {
            disposables = new CompositeDisposable();
        }
        return disposables;
    }

    protected void parseError(Throwable throwable) {
        notifyError(throwable.getLocalizedMessage());
    }

    protected void parseErrorSilent(Throwable throwable) {
        Timber.e(throwable, "Error: %s", throwable.getMessage());
    }

    private void notifyError(String msg) {
        if (getView() != null) {
            getView().showError(msg);
        }
    }

    private void notifyError(@StringRes int msg) {
        if (getView() != null) {
            getView().showError(msg);
        }
    }

    public void injectDependency(final Context context) {
        TmApp.get(context).getAppComponent().inject(this);
    }

}

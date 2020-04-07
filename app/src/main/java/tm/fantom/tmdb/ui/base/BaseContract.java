package tm.fantom.tmdb.ui.base;

import androidx.annotation.StringRes;

import timber.log.Timber;

public interface BaseContract {

    interface Presenter<T> {
        void attach(T view);

    }

    interface View {

        default void showError(String message) {
            Timber.e("default error: %s", message);
        }

        default void showError(@StringRes int text) {
            Timber.e("default error: %s", text);
        }

        default void showProgress() {
            Timber.e("default show progress");
        }

        default void hideProgress() {
            Timber.e("default hide progress");
        }

    }
}
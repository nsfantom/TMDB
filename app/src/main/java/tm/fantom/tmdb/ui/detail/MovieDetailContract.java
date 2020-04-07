package tm.fantom.tmdb.ui.detail;

import tm.fantom.tmdb.ui.base.BaseContract;

public interface MovieDetailContract {

    interface View extends BaseContract.View {

        void showTitle(String name);

        void showData(MovieDetails movieDetails);

    }

    interface Presenter extends BaseContract.Presenter<MovieDetailContract.View> {

        void subscribe();

        void unsubscribe();

    }
}
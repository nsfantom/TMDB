package tm.fantom.tmdb.ui.dashboard;

import java.util.List;

import tm.fantom.tmdb.ui.base.BaseContract;

public interface DashboardContract {

    interface View extends BaseContract.View {

        void showSearchResult(List<MovieModel> searchResult, boolean append);

        void restorePosition();

        void showMovie();

        void restoreToggle(boolean enabled);

    }

    interface Presenter extends BaseContract.Presenter<DashboardContract.View> {


        void subscribe();

        void onLoadMore(int count);

        void forceRefresh();

        void onMovieClick(int id, String name);

        void unsubscribe();
    }
}
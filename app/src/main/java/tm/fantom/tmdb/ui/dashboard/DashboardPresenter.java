package tm.fantom.tmdb.ui.dashboard;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;
import tm.fantom.tmdb.R;
import tm.fantom.tmdb.api.netmodel.MovieListObject;
import tm.fantom.tmdb.repo.AuthToken;
import tm.fantom.tmdb.repo.MovieId;
import tm.fantom.tmdb.ui.base.BaseApiPresenter;
import tm.fantom.tmdb.ui.base.BaseContract;
import tm.fantom.tmdb.util.DateUtils;

public class DashboardPresenter extends BaseApiPresenter implements DashboardContract.Presenter {

    private DashboardContract.View view;
    private int currentPage = 0;
    private int totalRecords = 0;

    @Override
    public void attach(DashboardContract.View view) {
        this.view = view;
        view.restoreToggle(sharedStorage.isDarkMode());
    }

    @Override
    protected BaseContract.View getView() {
        return view;
    }

    @Override
    public void subscribe() {
        view.restorePosition();

        AuthToken authToken = sharedStorage.getAuthToken();
        if (TextUtils.isEmpty(authToken.getGuestSessionId()) || DateUtils.isExpired(authToken.getExpiresAt())) {
            getCompositeDisposable().add(simpleApi.getAuthenticationGuestSessionNew()
                    .compose(applyMaybeAsync())
                    .subscribe(r -> {
                        if (r.success()) {
                            sharedStorage.saveAuthToken(AuthToken.fromNetwork(r));
                            getMovies();
                        }
                    }, this::parseErrorSilent)
            );
        } else {
            getMovies();
        }
        simpleApi.getAuthenticationGuestSessionNew();
    }

    private void getMovies() {
        getCompositeDisposable().add(simpleApi.getMoviePopular(currentPage + 1)
                .compose(applyMaybeBackground())
                .map(r -> {
                    currentPage = r.getPage();
                    totalRecords = r.getTotalResults();
                    return parseMovies(r.getResults());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> view.showSearchResult(movies, currentPage > 1), this::parseErrorSilent)
        );
    }

    private List<MovieModel> parseMovies(List<MovieListObject> results) {
        List<MovieModel> movies = new ArrayList<>();
        for (MovieListObject mlo : results) {
            movies.add(MovieModel.fromNetwork(mlo).setPoster(resources.getString(R.string.base_url_poster, mlo.getPosterPath())));
        }
        return movies;
    }

    @Override
    public void onLoadMore(int count) {
        view.showProgress();
        Timber.d("load more count: %s, total: %s", count, totalRecords);
        if (!(totalRecords <= count - 1)) {
            getMovies();
        }
    }

    @Override
    public void forceRefresh() {
        currentPage = 0;
        subscribe();
    }

    @Override
    public void onMovieClick(int id, String name) {
        sharedStorage.getMovieIdSubject().onNext(new MovieId(id).setMovieName(name));
        view.showMovie();
    }

    @Override
    public void unsubscribe() {
        clearDisposables();
    }

}
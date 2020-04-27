package tm.fantom.tmdb.ui.detail;

import android.text.TextUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import tm.fantom.tmdb.R;
import tm.fantom.tmdb.api.netmodel.GenresResult;
import tm.fantom.tmdb.api.netmodel.MovieDetailResponse;
import tm.fantom.tmdb.repo.MovieId;
import tm.fantom.tmdb.ui.base.BaseApiPresenter;
import tm.fantom.tmdb.ui.base.BaseContract;

public class MovieDetailPresenter extends BaseApiPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View view;

    @Override
    public void attach(MovieDetailContract.View view) {
        this.view = view;
    }

    @Override
    protected BaseContract.View getView() {
        return view;
    }

    @Override
    public void subscribe() {
        getCompositeDisposable().add(sharedStorage.getMovieIdSubject()
                .doOnNext(this::getDetails)
                .map(MovieId::getMovieName)
                .subscribe(view::showTitle));
    }

    private void getDetails(MovieId movieId) {
        view.showProgress();
        getCompositeDisposable().add(
                simpleApi.getMovieMovieId(movieId.getMovieId())
                        .compose(applyMaybeBackground())
                        .map(this::parseMovie)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(m -> view.showData(m), this::parseError)
        );
    }

    @Override
    public void unsubscribe() {
        clearDisposables();
    }

    private MovieDetails parseMovie(MovieDetailResponse mdr) {
        return new MovieDetails()
                .setPoster(resources.getString(R.string.base_url_poster, mdr.getPosterPath()))
                .setCategory(parseCategory(mdr.getGenres()))
                .setRelease(resources.getString(R.string.release, mdr.getReleaseDate()))
                .setBudget(resources.getString(R.string.budget, mdr.getBudget()))
                .setTag(mdr.getTagLine())
                .setRating(resources.getString(R.string.rating, mdr.getVoteAverage()))
                .setDescription(mdr.getOverview());
    }

    private String parseCategory(List<GenresResult> genres) {
        String cat = "";
        for (GenresResult gs : genres) {
            if (!TextUtils.isEmpty(cat))
                cat = cat.concat(", ");
            cat = cat.concat(gs.getName());
        }
        return cat;
    }

}
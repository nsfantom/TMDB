package tm.fantom.tmdb.api.req;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tm.fantom.tmdb.api.netmodel.GuestSessionResp;
import tm.fantom.tmdb.api.netmodel.MovieDetailResponse;
import tm.fantom.tmdb.api.netmodel.MoviesResponse;

public interface SimpleApi {

    /**
     * Create Guest Session
     * This method will let you create a new guest session. Guest sessions are a type of session that will let a user rate movies and TV shows but not require them to have a TMDb user account. More information about user authentication can be found [here](#docTextSection:NSZtgz7zptsiLYxXZ).  Please note, you should only generate a single guest session per user (or device) as you will be able to attach the ratings to a TMDb user account in the future. There is also IP limits in place so you should always make sure it&#39;s the end user doing the guest session actions.  If a guest session is not used for the first time within 24 hours, it will be automatically deleted.
     *
     * @return Maybe&lt;GuestSessionResp&gt;
     */
    @GET("authentication/guest_session/new")
    Maybe<GuestSessionResp> getAuthenticationGuestSessionNew();

    /**
     * Get Popular
     * Get a list of the current popular movies on TMDb. This list updates daily.
     *
     * @return Maybe&lt;MoviesResponse&gt;
     */
    @GET("movie/popular")
    Maybe<MoviesResponse> getMoviePopular(@Query("page") int page);

    /**
     * Get Details
     * Get the primary information about a movie.  Supports &#x60;append_to_response&#x60;. Read more about this [here](#docTextSection:JdZq8ctmcxNqyLQjp).
     *
     * @param movieId (required)
     * @return Maybe&lt;MovieDetailResponse&gt;
     */
    @GET("movie/{movie_id}")
    Maybe<MovieDetailResponse> getMovieMovieId(
            @retrofit2.http.Path("movie_id") int movieId
    );
}

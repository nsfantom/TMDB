package tm.fantom.tmdb.repo;

import com.google.gson.annotations.SerializedName;

public final class MovieId {
    @SerializedName("movie_id")
    private int movieId;

    @SerializedName("movie_name")
    private String movieName;

    public int getMovieId() {
        return movieId;
    }

    public MovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public MovieId setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }
}

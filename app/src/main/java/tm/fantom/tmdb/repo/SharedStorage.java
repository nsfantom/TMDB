package tm.fantom.tmdb.repo;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import tm.fantom.tmdb.api.converter.DateTimeConverter;

public final class SharedStorage {
    private static final String SESSION_ID = "auth_token";
    private static final String MOVIE_ID = "movie_id";

    private SharedPreferences sharedPreferences;
    private Context context;
    private Gson gson;


    public SharedStorage(Context context) {
        this.context = context;
        gson = new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new DateTimeConverter())
                .create();
    }

    public boolean saveAuthToken(AuthToken guest) {
        if (guest == null) {
            return getPrefs().edit().remove(SESSION_ID).commit();
        }
        return getPrefs().edit()
                .putString(SESSION_ID, gson.toJson(guest))
                .commit();
    }

    public AuthToken getAuthToken() {
        AuthToken token = new AuthToken();
        String json = getPrefs().getString(SESSION_ID, "");
        if (!TextUtils.isEmpty(json)) {
            token = gson.fromJson(json, AuthToken.class);
        }
        return token;
    }

    public boolean saveMovieId(MovieId id) {
        if (id == null) {
            return getPrefs().edit().remove(MOVIE_ID).commit();
        }
        return getPrefs().edit()
                .putString(MOVIE_ID, gson.toJson(id))
                .commit();
    }

    public MovieId getMovieId() {
        MovieId movieId = new MovieId(0);
        String json = getPrefs().getString(MOVIE_ID, "");

        if (!TextUtils.isEmpty(json)) {
            movieId = gson.fromJson(json, MovieId.class);
        }
        return movieId;
    }

    private SharedPreferences getPrefs() {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences("tmdb", Context.MODE_PRIVATE);
        return sharedPreferences;
    }
}

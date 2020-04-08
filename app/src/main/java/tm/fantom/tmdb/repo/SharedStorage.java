package tm.fantom.tmdb.repo;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import io.reactivex.subjects.BehaviorSubject;
import tm.fantom.tmdb.api.converter.DateTimeConverter;

public final class SharedStorage {
    private static final String SESSION_ID = "auth_token";
    private static final String THEME_DARK = "dark_mode";

    private SharedPreferences sharedPreferences;
    private Context context;
    private Gson gson;

    private BehaviorSubject<MovieId> movieIdBehaviorSubject = BehaviorSubject.create();

    public BehaviorSubject<MovieId> getMovieIdSubject() {
        return movieIdBehaviorSubject;
    }

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

    public boolean saveDarkMode(boolean darkMode) {
        if (darkMode)
            return getPrefs().edit().putBoolean(THEME_DARK, true).commit();
        else
            return getPrefs().edit().remove(THEME_DARK).commit();
    }

    public boolean isDarkMode() {
        return getPrefs().getBoolean(THEME_DARK, false);
    }

    private SharedPreferences getPrefs() {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences("tmdb", Context.MODE_PRIVATE);
        return sharedPreferences;
    }
}

package tm.fantom.tmdb.ui;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        Fragments.DASHBOARD, Fragments.DETAILS
})
@Retention(RetentionPolicy.CLASS)
public @interface Fragments {
    int DASHBOARD = 5;
    int DETAILS = 6;
}

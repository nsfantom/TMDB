package tm.fantom.tmdb.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class DateUtils {

    public static final DateTimeFormatter tmdbFmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss 'UTC'");

    public static boolean isExpired(long time) {
        DateTime dateTime = new DateTime(time);
        DateTime dateTimeNow = DateTime.now();
        return dateTime.isBefore(dateTimeNow);
    }
}

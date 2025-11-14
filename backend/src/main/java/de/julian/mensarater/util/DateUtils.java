package de.julian.mensarater.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.threeten.extra.YearWeek;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    private final static String TIMEZONE_ID = System.getenv("TZ") != null ? System.getenv("TZ") : "Europe/Berlin";
    private final static String DATE_PATTERN = "dd.MM.yyyy";

    private final static String[] DAYS = {"So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"};
    private final static TimeZone TIME_ZONE = TimeZone.getTimeZone(TIMEZONE_ID);

    public static int getYearWeek() {
        return YearWeek.now(ZoneId.of(TIMEZONE_ID)).getWeek();
    }

    public static String getCurrentDay() {
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.setTimeZone(TIME_ZONE);
        return DAYS[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public static String getCurrentDate() {
        return getCurrentDate(DATE_PATTERN);
    }

    public static String getCurrentDate(String pattern) {
        Instant now = Instant.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.of(TIMEZONE_ID));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return zonedDateTime.format(formatter);
    }
}

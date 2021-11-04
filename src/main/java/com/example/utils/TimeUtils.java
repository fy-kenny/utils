package com.example.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Calendar;

import static com.example.utils.constant.Constants.YYYY;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
public interface TimeUtils {

    int MINUTE_IN_SECONDS = 60;
    int HOUR_IN_SECONDS = 60 * MINUTE_IN_SECONDS;

    static int getTotalWeeksInYear(String yearStr) {

        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern(YYYY)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 12)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 31)
                .toFormatter();
        LocalDate localDate = LocalDate.parse(yearStr, dateTimeFormatter);

        return localDate
//                .withDayOfMonth(31)
//                .withMonth(12)
                .get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    }

    static int getTotalWeeksInYear(int year) {

        return getTotalWeeksInYear(String.valueOf(year));
    }

    static int getTotalWeeksInYearByCalendar(int year) {

        return Calendar.getInstance().getWeeksInWeekYear();
    }
}

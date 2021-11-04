package com.example.utils.constant.enums;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.example.utils.constant.Constants.*;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
public enum StatisticPeriodEnum {

    DAY("日", YYYY_MM_DD, "%Y-%m-%d"),
    WEEK("周", WEEK_OF_YEAR,"%X-%V"),
    MONTH("月", YYYY_MM, "%Y-%m"),
    YEAR("年", YYYY, "%Y"),
    ;

    private String nameCN;
    private String dataFormat;
    private String sqlDateFormat;

    public String nameCN() {
        return this.nameCN;
    }

    public String dateFormat() {
        return this.dataFormat;
    }

    public String sqlDateFormat() {
        return this.sqlDateFormat;
    }

    public LocalDateTime plusOne(LocalDateTime localDateTime) {
        if (YEAR.equals(this)) {
            localDateTime = localDateTime.plusYears(1);
        } else if (MONTH.equals(this)) {
            localDateTime = localDateTime.plusMonths(1);
        } else if (DAY.equals(this)) {
            localDateTime = localDateTime.plusDays(1);
        } else if (WEEK.equals(this)) {
            localDateTime = localDateTime.plusWeeks(1);
        }

        return localDateTime;
    }

    public LocalDateTime now() {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (YEAR.equals(this)) {
            localDateTime = localDateTime.truncatedTo(ChronoUnit.DAYS).withMonth(1).withDayOfMonth(1);
        } else if (MONTH.equals(this)) {
            localDateTime = localDateTime.truncatedTo(ChronoUnit.DAYS).withDayOfMonth(1);
        } else if (DAY.equals(this)) {
            localDateTime = localDateTime.truncatedTo(ChronoUnit.DAYS);
        } else if (WEEK.equals(this)) {
            localDateTime = localDateTime.truncatedTo(ChronoUnit.WEEKS);
        }

        return localDateTime;
    }
}

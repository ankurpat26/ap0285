package com.abc.tool_rental.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

public class HolidaysUtil {

    /**
     * Get the exact date of Independence Day.
     * If it falls on a weekend, it is observed on the closest weekday.
     *
     * @param year the year for which to calculate the holiday
     * @return the LocalDate of Independence Day
     */
    public static LocalDate getIndependenceDay(int year) {
        LocalDate independenceDay = LocalDate.of(year, Month.JULY, 4);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return independenceDay.minusDays(1);
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            return independenceDay.plusDays(1);
        } else {
            return independenceDay;
        }
    }

    /**
     * Get the exact date of Labor Day.
     * Labor Day is the first Monday in September.
     *
     * @param year the year for which to calculate the holiday
     * @return the LocalDate of Labor Day
     */
    public static LocalDate getLaborDay(int year) {
        LocalDate firstDayOfSeptember = LocalDate.of(year, Month.SEPTEMBER, 1);
        int dayOfWeekValue = firstDayOfSeptember.getDayOfWeek().getValue();
        int daysUntilMonday = (DayOfWeek.MONDAY.getValue() - dayOfWeekValue + 7) % 7;
        return firstDayOfSeptember.plusDays(daysUntilMonday);
    }

    /**
     * Get a list of LocalDate objects for both Independence Day and Labor Day for a given year.
     *
     * @param year the year for which to calculate the holidays
     * @return a List of LocalDate objects for both holidays
     */
    public static Set<LocalDate> getHolidays(int year) {
        Set<LocalDate> holidays = new HashSet<>();
        holidays.add(getIndependenceDay(year));
        holidays.add(getLaborDay(year));
        return holidays;
    }
}

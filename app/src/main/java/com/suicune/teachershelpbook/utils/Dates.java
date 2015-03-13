package com.suicune.teachershelpbook.utils;

import org.joda.time.DateTime;

/**
 * Created by denis on 08.03.15.
 */
public class Dates {
    public static String formatDate(DateTime date) {
        return String.format("%d/%d/%d", date.getDayOfMonth(),
                date.getMonthOfYear(), date.getYear());
    }
    public static String formatRange(DateTime start, DateTime end) {
        return String.format("%d/%d/%d - %d/%d/%d", start.getDayOfMonth(),
                start.getMonthOfYear(), start.getYear(),
                end.getDayOfMonth(), end.getMonthOfYear(),
                end.getYear());
    }

	public static String formatTimeRange(DateTime start, DateTime end) {
		return String.format("%d:%d - %d:%d", start.getHourOfDay(), start.getMinuteOfHour(),
				end.getHourOfDay(), end.getMinuteOfHour());
	}

	public static DateTime dateForDayOfWeek(int day, DateTime startOfWeek) {
		return startOfWeek.plusDays(day - 1);
	}

	public static String formatTime(DateTime time) {
		return String.format("%d:%d", time.getHourOfDay(), time.getMinuteOfHour());
	}
}

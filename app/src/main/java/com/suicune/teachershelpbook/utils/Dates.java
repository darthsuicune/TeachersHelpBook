package com.suicune.teachershelpbook.utils;

import org.joda.time.DateTime;

/**
 * Created by denis on 08.03.15.
 */
public class Dates {
	public static String formatDate(DateTime date) {
		return String.format("%d/%s/%d", date.getDayOfMonth(), month(date), date.getYear());
	}

	public static String formatDateRange(DateTime start, DateTime end) {
		return String.format("%d/%s/%d - %d/%s/%d", start.getDayOfMonth(), month(start),
				start.getYear(), end.getDayOfMonth(), month(end), end.getYear());
	}

	public static String formatTime(DateTime time) {
		return String.format("%d:%s", time.getHourOfDay(), minute(time));
	}

	public static String formatTimeRange(DateTime start, DateTime end) {
		return String.format("%d:%s - %d:%s", start.getHourOfDay(), minute(start),
				end.getHourOfDay(), minute(end));
	}

	public static DateTime dateForDayOfWeek(int day, DateTime startOfWeek) {
		return startOfWeek.plusDays(day - 1);
	}

	private static String month(DateTime date) {
		int month = date.getMonthOfYear();
		return (month < 10) ? "0" + month : Integer.toString(month);
	}

	private static String minute(DateTime time) {
		int minute = time.getMinuteOfHour();
		return (minute < 10) ? "0" + minute : Integer.toString(minute);
	}
}

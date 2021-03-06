package com.dlgdev.teachers.helpbook.utils;

import com.dlgdev.teachers.helpbook.domain.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Dates {

	public static DateTime startOfWeek(DateTime date, int firstDayOfWeek) {
		return date.withDayOfWeek(firstDayOfWeek).withHourOfDay(0).withMinuteOfHour(0);
	}

	public static DateTime endOfWeek(DateTime date, int firstDayOfWeek) {
		int endOfWeek = (firstDayOfWeek == DateTimeConstants.MONDAY) ? DateTimeConstants.SUNDAY :
				DateTimeConstants.SATURDAY;
		return date.withDayOfWeek(endOfWeek).withHourOfDay(23).withMinuteOfHour(59);
	}

	public static String formatDate(DateTime date) {
		return String.format("%d/%s/%d", date.getDayOfMonth(), month(date), date.getYear());
	}

	public static String formatDateRange(DateTime start, DateTime end) {
		return String
				.format("%d/%s/%d - %d/%s/%d", start.getDayOfMonth(), month(start), start.getYear(),
						end.getDayOfMonth(), month(end), end.getYear());
	}

	public static String formatTime(DateTime time) {
		return String.format("%d:%s", time.getHourOfDay(), minute(time));
	}

	public static String formatTimeRange(DateTime start, DateTime end) {
		return String
				.format("%d:%s - %d:%s", start.getHourOfDay(), minute(start), end.getHourOfDay(),
						minute(end));
	}

	public static DateTime dateForDayOfWeek(int day, DateTime referenceDate) {
		if (day > DateTimeConstants.SUNDAY) {
			throw new InvalidDateTimeException(R.string.invalid_day);
		}
		return referenceDate.withDayOfWeek(DateTimeConstants.MONDAY).plusDays(day - 1);
	}

	private static String month(DateTime date) {
		int month = date.getMonthOfYear();
		return (month < 10) ? "0" + month : Integer.toString(month);
	}

	private static String minute(DateTime time) {
		int minute = time.getMinuteOfHour();
		return (minute < 10) ? "0" + minute : Integer.toString(minute);
	}

	public static DateTime parseDate(String date) {
		StringTokenizer tokenizer = dateTokenizer(date);
		try {
			int day = Integer.parseInt(tokenizer.nextToken());
			int month = Integer.parseInt(tokenizer.nextToken());
			int year = Integer.parseInt(tokenizer.nextToken());
			return new DateTime(year, month, day, 0, 0);
		} catch (NumberFormatException | NoSuchElementException e) {
			throw new InvalidDateTimeException(R.string.invalid_date_format);
		}
	}

	private static StringTokenizer dateTokenizer(String date) {
		StringTokenizer tokenizer;
		if (date.contains("/")) {
			tokenizer = new StringTokenizer(date, "/");
		} else if (date.contains("-")) {
			tokenizer = new StringTokenizer(date, "-");
		} else {
			tokenizer = new StringTokenizer(date, ".");
		}
		return tokenizer;
	}

	public static DateTime parseTime(String time) {
		StringTokenizer tokenizer = new StringTokenizer(time, ":");
		try {
			int hour;
			int minute = 0;
			if (time.contains(":")) {
				hour = Integer.parseInt(tokenizer.nextToken());
				minute = Integer.parseInt(tokenizer.nextToken());
			} else {
				hour = Integer.parseInt(tokenizer.nextToken());
			}
			return new DateTime(0, 1, 1, hour, minute);
		} catch (NumberFormatException | NoSuchElementException e) {
			throw new InvalidDateTimeException(R.string.invalid_time_format);
		}
	}
}

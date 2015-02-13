package com.suicune.teachershelpbook.model.calendar;

/**
 * Created by lapuente on 13.02.15.
 */
public class EventsFactory {

	public DailyEvents createDaily(Week week, DayOfWeek day) {
		return new DailyEvents(week.day(day));
	}
}

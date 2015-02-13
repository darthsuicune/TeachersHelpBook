package com.suicune.teachershelpbook.model.calendar;

import android.text.format.Time;

import com.suicune.teachershelpbook.model.Event;

import java.util.HashMap;
import java.util.Map;

public class WeeklyTimeTable {
	Map<DayOfWeek, DailyEvents> dailyEvents;
	Week week;
	EventsFactory factory;

	public WeeklyTimeTable(Week week, EventsFactory factory) {
		dailyEvents = new HashMap<>();
		this.week = week;
		this.factory = factory;
	}

	public DailyEvents getEventsFor(DayOfWeek day) {
		if(day.isWorkingDay()) {
			if(dailyEvents.containsKey(day)) {
				return dailyEvents.get(day);
			} else {
				return factory.createDaily(week, day);
			}
		} else {
			throw new NotAWorkingDayException();
		}
	}

	public boolean hasEventsFor(DayOfWeek day) {
		if(dailyEvents.containsKey(day)) {
			DailyEvents events = dailyEvents.get(day);
			return events.hasEvents();
		}
		return false;
	}

	public void addEvent(DayOfWeek day, Event event) {
		if(day.isWorkingDay()) {
			addEventToWorkingDay(day, event);
		} else {
			throw new NotAWorkingDayException();
		}
	}

	private void addEventToWorkingDay(DayOfWeek day, Event event) {
		DailyEvents events = getEventsFor(day);
		events.addEvent(event);
		dailyEvents.put(day, events);
	}

	public Event getEventsAt(DayOfWeek day, Time time) {
		return dailyEvents.get(day).eventAt(time);
	}
}

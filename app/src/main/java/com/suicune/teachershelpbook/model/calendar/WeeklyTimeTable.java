package com.suicune.teachershelpbook.model.calendar;

import android.text.format.Time;

import com.suicune.teachershelpbook.model.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeeklyTimeTable {
	Map<DayOfWeek, DailyEvents> events;
	Week week;
	EventsProvider factory;

	public WeeklyTimeTable(Week week, EventsProvider factory) {
		events = new HashMap<>();
		this.week = week;
		this.factory = factory;
	}

	public DailyEvents eventsFor(DayOfWeek day) {
		if(!day.isWorkingDay()) {
			throw new NotAWorkingDayException();
		}
		if (events.containsKey(day)) {
			return events.get(day);
		} else {
			DailyEvents dailyEvents = factory.createDaily(week, day);
			events.put(day, dailyEvents);
			return dailyEvents;
		}
	}

	public Event eventAt(DayOfWeek day, Time time) {
		if (hasEventsFor(day)) {
			return events.get(day).eventAt(time);
		} else {
			return factory.createEmpty(time);
		}
	}

	public List<Event> eventsBetween(Time startingTime, Time endingTime) {
		if (startingTime.before(endingTime)) {
			return eventsBetweenValidTimes(startingTime, endingTime);
		}
		throw new InvalidTimeRangeException(startingTime, endingTime);
	}

	private List<Event> eventsBetweenValidTimes(Time startingTime, Time endingTime) {
		List<Event> eventList = new ArrayList<>();
		for(DayOfWeek day : events.keySet()) {
			eventList.addAll(events.get(day).eventsFrom(startingTime, endingTime));
		}
		return eventList;
	}

	public boolean hasEventsFor(DayOfWeek day) {
		if (day.isWorkingDay()) {
			if (events.containsKey(day)) {
				return events.get(day).hasEvents();
			}
		} else {
			throw new NotAWorkingDayException();
		}
		return false;
	}

	public void addEvent(DayOfWeek day, Event event) {
		if (day.isWorkingDay()) {
			addEventToWorkingDay(day, event);
		} else {
			throw new NotAWorkingDayException();
		}
	}

	private void addEventToWorkingDay(DayOfWeek day, Event event) {
		DailyEvents events = eventsFor(day);
		events.addEvent(event);
		this.events.put(day, events);
	}
}

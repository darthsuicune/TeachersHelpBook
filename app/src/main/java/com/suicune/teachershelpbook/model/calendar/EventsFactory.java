package com.suicune.teachershelpbook.model.calendar;

import android.text.format.Time;

import com.suicune.teachershelpbook.model.Event;

/**
 * Created by lapuente on 13.02.15.
 */
public class EventsFactory {

	public DailyEvents createDaily(Week week, DayOfWeek day) {
		return new DailyEvents(week.day(day));
	}

	public Event createEmpty() {
		return new Event() {};
	}

	public Event createEmpty(Time time) {
		return new Event() {};
	}

    public Event newEventAt(Time start) {
        return new Event(start) {
        };
    }

    public Event newEventBetween(Time start, Time end) {
        return new Event(start, end) {
        };
    }

    public static class EmptyEvent extends Event {

	}
}

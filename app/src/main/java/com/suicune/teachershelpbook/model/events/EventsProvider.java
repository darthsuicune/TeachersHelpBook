package com.suicune.teachershelpbook.model.events;

import android.database.Cursor;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class EventsProvider {
	public static final int DEFAULT_EVENT_DURATION_IN_HOURS = 1;
    int defaultEventDurationInHours;

	public EventsProvider() {
        this.defaultEventDurationInHours = DEFAULT_EVENT_DURATION_IN_HOURS;
    }

    public EventsProvider(int defaultEventDurationInHours) {
        this.defaultEventDurationInHours = defaultEventDurationInHours;
    }

	/**
	 * Create an empty event that starts right now and ends after one hour
	 * @return new event that starts right now and ends after one hour
	 */
	public Event createEmpty() {
		DateTime start = new DateTime();
		return createEmpty(start);
	}
	/**
	 * Create an empty event that starts at the given time and ends after one hour
	 *
	 * @param start time of the event start
	 * @return new event that starts at the given time and ends after one hour
	 */
	public Event createEmpty(DateTime start) {
		DateTime end = new DateTime(start).plusHours(defaultEventDurationInHours);
		return createEmpty(start, end);
	}

	public Event createEmpty(DateTime start, DateTime end) {
		return new Event(start, end) {};
	}

    public Event newEvent(String title, String description) {
        Event event = createEmpty();
        event.title(title);
        event.description(description);
        return event;
    }

    public Event newEventAt(DateTime start, String title, String description) {
		Event event = createEmpty(start);
		event.title(title);
		event.description(description);
		return event;
    }

    public Event newEventBetween(DateTime start, DateTime end, String title, String description) {
		Event event = createEmpty(start, end);
		event.title(title);
		event.description(description);
		return event;
    }

	public EventList listFromCursor(Cursor data) {
		List<Event> eventList = new ArrayList<>();
		return new EventList(eventList);
	}
}

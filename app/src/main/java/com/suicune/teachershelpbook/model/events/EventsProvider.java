package com.suicune.teachershelpbook.model.events;

import android.database.Cursor;
import android.text.format.Time;

import com.suicune.teachershelpbook.model.courses.Course;

import java.util.Date;

/**
 * Created by lapuente on 13.02.15.
 */
public class EventsProvider {
	public static final int DEFAULT_EVENT_DURATION = 1;
	Course course;

	public EventsProvider(Course course) {
		this.course = course;
	}

	/**
	 * Create an empty event that starts right now and ends after one hour
	 * @return new event that starts right now and ends after one hour
	 * @param date
	 */
	public Event createEmpty(Date date) {
		Time start = new Time();
		start.setToNow();
		return createEmpty(start);
	}
	/**
	 * Create an empty event that starts at the given time and ends after one hour
	 *
	 * @param start time of the event start
	 * @return new event that starts at the given time and ends after one hour
	 */
	public Event createEmpty(Time start) {
		Time end = new Time(start);
		end.hour += DEFAULT_EVENT_DURATION;
		return createEmpty(start, end);
	}

	public Event createEmpty(Time start, Time end) {
		return new Event(start, end) {};
	}

    public Event newEventAt(Time start, String title, String description) {
		Event event = createEmpty(start);
		event.title(title);
		event.description(description);
		return event;
    }

    public Event newEventBetween(Time start, Time end, String title, String description) {
		Event event = createEmpty(start, end);
		event.title(title);
		event.description(description);
		return event;
    }

	public static EventList listFromCursor(Cursor data) {
		return null;
	}
}

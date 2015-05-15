package com.dlgdev.teachers.helpbook.models.factories;

import android.database.Cursor;

import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.EventList;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventsFactory implements Serializable {
	int defaultEventDurationInHours;

	public EventsFactory() {
		this.defaultEventDurationInHours = Event.DEFAULT_EVENT_DURATION_IN_HOURS;
	}

	public EventsFactory(int defaultEventDurationInHours) {
		this.defaultEventDurationInHours = defaultEventDurationInHours;
	}

	/**
	 * Create an empty event that starts right now and ends after one hour
	 *
	 * @return new event that starts right now and ends after one hour
	 */
	public Event createEmpty() {
		DateTime start = DateTime.now();
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
		return new Event(start, end);
	}

	public Event createAndSave(String title, String description) {
		Event event = createEmpty();
		event.title(title);
		event.description(description);
		event.save();
		return event;
	}

	public Event createAndSaveAt(DateTime start, String title, String description) {
		Event event = createEmpty(start);
		event.title(title);
		event.description(description);
		event.save();
		return event;
	}

	public Event createAndSaveBetween(DateTime start, DateTime end, String title,
									  String description) {
		Event event = createEmpty(start, end);
		event.title(title);
		event.description(description);
		event.save();
		return event;
	}

	public EventList listFromCursor(Cursor data) {
		List<Event> eventList = new ArrayList<>();
		if(data.moveToFirst()) {
			do {
				Event event = new Event();
				event.loadFromCursor(data);
				eventList.add(event);
			} while(data.moveToNext());
		}
		return new EventList(eventList);
	}

	public EventList listFromList(List<Event> eventList) {
		return new EventList(eventList);
	}
}

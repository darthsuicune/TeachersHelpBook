package com.suicune.teachershelpbook.model.calendar;

import android.text.format.Time;

import com.suicune.teachershelpbook.model.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lapuente on 13.02.15.
 */
public class DailyEvents {
	EventsProvider provider;
	List<Event> eventList;
	Date date;

	public DailyEvents(Date date, EventsProvider provider) {
		eventList = new ArrayList<>();
		this.date = date;
		this.provider = provider;
	}

	public boolean hasEvents() {
		return eventList.size() > 0;
	}

	public void addEvent(Event event) {
		eventList.add(event);
	}

	public int count() {
		return eventList.size();
	}

	public boolean isFreeAt(Time time) {
		for (Event event : eventList) {
			if (event.isAt(time)) {
				return false;
			}
		}
		return true;
	}

	public Event eventAt(Time time) {
		for (Event event : eventList) {
			if (event.isAt(time)) {
				return event;
			}
		}
		return provider.createEmpty();
	}

	public List<Event> eventsFrom(Time startingTime, Time endingTime) {
		List<Event> eventList = new ArrayList<>();
		for(Event event : eventList) {
			if(event.isBetween(startingTime, endingTime)) {
				eventList.add(event);
			}
		}
		return eventList;
	}
}

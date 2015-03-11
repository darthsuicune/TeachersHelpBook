package com.suicune.teachershelpbook.model.events;

import org.joda.time.DateTime;

import java.util.List;

public class EventList {
	List<Event> eventList;

	public EventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	public int eventCount() {
		return eventList.size();
	}

	public List<Event> events() {
		return eventList;
	}

	public EventList eventsOn(DateTime date) {
		return this;
	}
}

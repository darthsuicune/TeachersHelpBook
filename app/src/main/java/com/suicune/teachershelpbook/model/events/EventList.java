package com.suicune.teachershelpbook.model.events;

import java.util.List;

public class EventList {
	List<Event> eventList;

	public EventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	public int eventCount() {
		return eventList.size();
	}
}

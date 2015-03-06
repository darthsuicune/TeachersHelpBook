package com.suicune.teachershelpbook.model.events;

import java.util.List;

/**
 * Created by lapuente on 04.03.15.
 */
public class EventList {
	List<Event> eventList;

	public EventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	public int eventCount() {
		return eventList.size();
	}
}

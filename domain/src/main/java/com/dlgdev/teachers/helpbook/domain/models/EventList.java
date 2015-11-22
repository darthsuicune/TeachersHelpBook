package com.dlgdev.teachers.helpbook.domain.models;

import org.joda.time.DateTime;

import java.util.ArrayList;
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

    public EventList eventsForDay(DateTime date) {
        List<Event> list = new ArrayList<>();
        for(Event event : eventList) {
            if(event.isInDay(date)) {
                list.add(event);
            }
        }
        return new EventList(list);
    }

    public void add(Event event) {
        eventList.add(event);
    }
}

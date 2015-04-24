package com.dlgdev.teachers.helpbook.model.events;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class EventListTest extends TestCase {
    EventList eventList;
    EventsProvider provider;

    public void setUp() throws Exception {
        super.setUp();
        List<Event> events = new ArrayList<>();
        eventList = new EventList(events);
        provider = new EventsProvider();
    }

    public void testEventCount() throws Exception {
        whenWeHaveAnEventWithXEvents(2);
        eventCountReturnsThatXAmount(2);
    }

    private void whenWeHaveAnEventWithXEvents(int count) {
        List<Event> events = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            events.add(provider.createEmpty());
        }
        eventList = new EventList(events);
    }

    private void eventCountReturnsThatXAmount(int count) {
        assertEquals(eventList.eventCount(), count);
    }
}
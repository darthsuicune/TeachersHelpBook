package com.dlgdev.teachers.helpbook.domain.models;

import com.dlgdev.teachers.helpbook.domain.models.repositories.EventsRepository;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EventListTest {
    EventList eventList;
    EventsRepository repo = new EventsRepository();
    List<Event> events = new ArrayList<>();

    @Before public void setUp() throws Exception {
        eventList = new EventList(events);
    }

    @Test public void testEventCount() throws Exception {
        whenWeHaveAnEventWithXEvents(2);
        eventCountReturnsThatXAmount(2);
    }

    private void whenWeHaveAnEventWithXEvents(int count) {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            events.add(new Event());
        }
        eventList = new EventList(events);
    }

    private void eventCountReturnsThatXAmount(int count) {
        assertEquals(eventList.eventCount(), count);
    }

    @Test public void eventsForDayReturnsANewEventListWithTheEventsForTheDay() throws Exception {
        afterCreatingEventsInDifferentDays();
        eventList = repo.fromList(events);
        eventList = eventList.eventsForDay(DateTime.now());
        assertEquals(1, eventList.eventCount());
        assertEquals(events.get(1), eventList.events().get(0));
    }

    private void afterCreatingEventsInDifferentDays() {
        events.add(repo.createAt(DateTime.now().minusDays(2), "Some title", "Some desc"));
        events.add(repo.createAt(DateTime.now(), "Some title", "Some desc"));
        events.add(repo.createAt(DateTime.now().plusDays(2), "Some title", "Some desc"));
    }
}
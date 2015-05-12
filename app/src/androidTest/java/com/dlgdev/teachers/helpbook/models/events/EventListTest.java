package com.dlgdev.teachers.helpbook.models.events;

import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class EventListTest {
    EventList eventList;
    EventsProvider provider;

    @Before public void setUp() throws Exception {
        List<Event> events = new ArrayList<>();
        eventList = new EventList(events);
        provider = new EventsProvider();
    }

    @Test public void testEventCount() throws Exception {
        whenWeHaveAnEventWithXEvents(2);
        eventCountReturnsThatXAmount(2);
    }

    @After public void teardown() throws Exception {
        new Delete().from(Event.class).execute();
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

    @Test public void eventsForDayReturnsANewEventListWithTheEventsForTheDay() throws Exception {
        afterCreatingEventsInDifferentDays();
        List<Event> events = new Select().from(Event.class).execute();
        eventList = provider.listFromList(events);
        assertEquals(1, eventList.eventsForDay(DateTime.now()).eventCount());
    }

    private void afterCreatingEventsInDifferentDays() {
        provider.newEventAt(DateTime.now().minusDays(2), "Some title", "Some desc");
        provider.newEventAt(DateTime.now(), "Some title", "Some desc");
        provider.newEventAt(DateTime.now().plusDays(2), "Some title", "Some desc");
    }
}
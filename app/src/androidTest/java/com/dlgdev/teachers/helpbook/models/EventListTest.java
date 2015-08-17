package com.dlgdev.teachers.helpbook.models;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.query.Select;
import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.models.factories.EventsFactory;

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
    EventsFactory provider;

    @Before public void setUp() throws Exception {
        DatabaseUtils.getDatabase(InstrumentationRegistry.getTargetContext());
        List<Event> events = new ArrayList<>();
        eventList = new EventList(events);
        provider = new EventsFactory();
    }

    @Test public void testEventCount() throws Exception {
        whenWeHaveAnEventWithXEvents(2);
        eventCountReturnsThatXAmount(2);
    }

    @After public void teardown() throws Exception {
        DatabaseUtils.clearDatabase();
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
        provider.createAndSaveAt(DateTime.now().minusDays(2), "Some title", "Some desc");
        provider.createAndSaveAt(DateTime.now(), "Some title", "Some desc");
        provider.createAndSaveAt(DateTime.now().plusDays(2), "Some title", "Some desc");
    }
}
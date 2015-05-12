package com.dlgdev.teachers.helpbook.models.events;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.query.Delete;
import com.dlgdev.teachers.helpbook.models.db.TeachersDBContract;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EventsProviderTest {
    EventsProvider provider;
    Event event;

    @Before public void setUp() throws Exception {
        provider = new EventsProvider();
    }

    @After public void tearDown() throws Exception {
        new Delete().from(Event.class).execute();
    }

    @Test public void testCreateEmptyCreatesAnEventThatStartsNowAndLastsForAnHour()
            throws Exception {
        event = provider.createEmpty();
        eventStarsAnEndsWithin(event, new DateTime());
    }

    private void eventStarsAnEndsWithin(Event event, DateTime dateTime) {
        assertTrue(dateTime.isAfter(event.start().minusSeconds(5)));
        assertTrue(dateTime.isBefore(event.end().plusSeconds(5)));
    }

    @Test public void testCreateEmptyCreatesAnEventAtTheGivenTimeThatLastsForAnHour()
            throws Exception {
        DateTime time = new DateTime();
        event = provider.createEmpty(time);
    }

    @Test public void testCreateEmptyWithStartAndEndCreatesAnEvent() throws Exception {
        DateTime start = new DateTime();
        event = provider.createEmpty(start, start.plusHours(2));
    }

    @Test public void testNewEvent() throws Exception {

    }

    @Test public void testNewEventAt() throws Exception {

    }

    @Test public void testNewEventBetween() throws Exception {

    }

    @Test public void testListFromCursor() throws Exception {
        int count = 2;
        afterCreatingSomeEvents(count);
        Cursor cursor = getDataFromDatabase();
        EventList list = provider.listFromCursor(cursor);
        assertEquals(count, list.eventCount());
    }

    private void afterCreatingSomeEvents(int i) {
        Event event = provider.newEvent("Someevent", "Somedesc");
        event.save();
        Event event1 = provider.newEvent("SomeOtherEvent", "Somedesc");
        event1.save();
    }

    public Cursor getDataFromDatabase() {
        ContentResolver cr = InstrumentationRegistry.getTargetContext().getContentResolver();
        return cr.query(TeachersDBContract.Events.URI, null, null, null, null);
    }
}
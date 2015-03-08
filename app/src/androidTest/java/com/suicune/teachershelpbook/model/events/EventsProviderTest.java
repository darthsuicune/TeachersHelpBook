package com.suicune.teachershelpbook.model.events;

import android.test.InstrumentationTestCase;

import org.joda.time.DateTime;

public class EventsProviderTest extends InstrumentationTestCase {
    EventsProvider provider;
    EventList list;
    Event event;

    public void setUp() throws Exception {
        super.setUp();
        provider = new EventsProvider();
    }

    public void testCreateEmptyCreatesAnEventThatStartsNowAndLastsForAnHour() throws Exception {
        event = provider.createEmpty();
    }

    public void testCreateEmptyCreatesAnEventAtTheGivenTimeThatLastsForAnHour() throws Exception {
        DateTime time = new DateTime();
        event = provider.createEmpty(time);
    }

    public void testCreateEmptyWithStartAndEndCreatesAnEvent() throws Exception {
        DateTime start = new DateTime();
        event = provider.createEmpty(start, start.plusHours(2));
    }

    public void testNewEvent() throws Exception {

    }

    public void testNewEventAt() throws Exception {

    }

    public void testNewEventBetween() throws Exception {

    }

    public void testListFromCursor() throws Exception {

    }
}
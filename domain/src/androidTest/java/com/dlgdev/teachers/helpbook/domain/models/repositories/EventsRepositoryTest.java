package com.dlgdev.teachers.helpbook.domain.models.repositories;

import android.media.MediaPlayer;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.domain.DatabaseUtils;
import com.dlgdev.teachers.helpbook.domain.models.Event;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EventsRepositoryTest {
    EventsRepository provider;
    Event event;

    @BeforeClass public static void init() {
        DatabaseUtils.init();
    }

    @Before public void setUp() throws Exception {
        provider = new EventsRepository();
    }

    @After public void tearDown() throws Exception {
        DatabaseUtils.clearDatabase();
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

    private void afterCreatingSomeEvents(int i) {
        for(int x = 0; i < x; i++) {
            Event event = provider.createAndSave("Someevent" + i, "Somedesc" + i);
            event.save();
        }
    }
}
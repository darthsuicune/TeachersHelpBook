package com.dlgdev.teachers.helpbook.domain.models;

import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.domain.DatabaseUtils;
import com.dlgdev.teachers.helpbook.domain.models.repositories.EventsRepository;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EventTest {
	Event event;
	EventsRepository provider;

    @BeforeClass public static void init() {
        DatabaseUtils.init();
    }

	@Before public void setUp() throws Exception {
		provider = new EventsRepository();
    }

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testIsAtPresentDateTime() throws Exception {
		DateTime time = new DateTime();
		anEventAt(time);
		isAtIn30Minutes(time);
	}

	private void anEventAt(DateTime time) {
		event = provider.createEmpty(time);
	}

	private void isAtIn30Minutes(DateTime dateTime) {
		assertTrue(event.isAt(dateTime.plusMinutes(30)));
	}

	@Test public void testIsBetween() throws Exception {
		DateTime time = new DateTime();
		anEventAt(time);
		isBetween(time.minusHours(1), time.plusHours(1));
	}

	private void isBetween(DateTime start, DateTime end) {
		assertTrue(event.isBetween(start, end));
	}

	@Test public void testIsInDay() throws Exception {
		Event[] events = afterCreatingEventsInDifferentDays();
		assertFalse(events[0].isInDay(DateTime.now()));
		assertTrue(events[1].isInDay(DateTime.now()));
		assertFalse(events[2].isInDay(DateTime.now()));
	}

	private Event[] afterCreatingEventsInDifferentDays() {
		Event[] events = new Event[3];
		events[0] = provider.createAndSaveAt(DateTime.now().minusDays(2), "Some title", "Some desc");
		events[1] = provider.createAndSaveAt(DateTime.now(), "Some title", "Some desc");
		events[2] = provider.createAndSaveAt(DateTime.now().plusDays(2), "Some title", "Some desc");
		return events;
	}
}
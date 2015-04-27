package com.dlgdev.teachers.helpbook.model.events;

import android.support.test.runner.AndroidJUnit4;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EventTest {
	Event event;
	EventsProvider factory;

	@Before public void setUp() throws Exception {
		factory = new EventsProvider();
    }

	@Test public void testIsAtPresentDateTime() throws Exception {
		DateTime time = new DateTime();
		anEventAt(time);
		isAtIn30Minutes(time);
	}

	private void anEventAt(DateTime time) {
		event = factory.createEmpty(time);
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
}
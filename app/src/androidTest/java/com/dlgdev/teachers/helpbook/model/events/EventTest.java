package com.dlgdev.teachers.helpbook.model.events;

import android.test.InstrumentationTestCase;

import org.joda.time.DateTime;

public class EventTest extends InstrumentationTestCase {
	Event event;
	EventsProvider factory;

	public void setUp() throws Exception {
		super.setUp();
		factory = new EventsProvider();
    }

	public void testIsAtPresentDateTime() throws Exception {
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

	public void testIsBetween() throws Exception {
		DateTime time = new DateTime();
		anEventAt(time);
		isBetween(time.minusHours(1), time.plusHours(1));
	}

	private void isBetween(DateTime start, DateTime end) {
		assertTrue(event.isBetween(start, end));
	}
}
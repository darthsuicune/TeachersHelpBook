package com.suicune.teachershelpbook.model.calendar;

import android.text.format.Time;

import com.suicune.teachershelpbook.model.Event;

import junit.framework.TestCase;

import java.util.Date;

public class DailyEventsTest extends TestCase {
	DailyEvents events;

	public void setUp() throws Exception {
		super.setUp();
		events = new DailyEvents(new Date());
	}

	public void testAddEventIncreasesTheEventCount() throws Exception {
		whenWeAddAnEvent();
		theEventListSizeIncreasesBy1();
	}

	private void whenWeAddAnEvent() {
		events.addEvent(new Event() {});
	}

	private void theEventListSizeIncreasesBy1() {
		assertTrue(events.count() == 1);
	}

	private void testEventAtReturnsAValidObject() {
		Event event = whenWeAskForAnEvent();
		weGetAValidObject(event);
	}

	private Event whenWeAskForAnEvent() {
		return events.eventAt(new Time());
	}

	private void weGetAValidObject(Object o) {
		assertNotNull(o);
	}
}
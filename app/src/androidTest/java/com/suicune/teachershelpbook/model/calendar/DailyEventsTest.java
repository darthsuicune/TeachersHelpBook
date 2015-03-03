package com.suicune.teachershelpbook.model.calendar;

import android.text.format.Time;

import com.suicune.teachershelpbook.model.Event;

import junit.framework.TestCase;

import java.util.Date;

public class DailyEventsTest extends TestCase {
	DailyEvents events;
	EventsProvider provider;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		provider = new EventsProvider();
		events = new DailyEvents(new Date(), provider);
	}

	public void testAddEventIncreasesTheEventCount() throws Exception {
		whenWeAddAnEvent();
		theEventListSizeIncreasesBy1();
	}

	private void whenWeAddAnEvent() {
		events.addEvent(provider.createEmpty());
	}

	private void theEventListSizeIncreasesBy1() {
		assertTrue(events.count() == 1);
	}

	public void testEventAtReturnsAValidObject() throws Exception {
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
package com.dlgdev.teachers.helpbook.models.events;

import android.support.test.runner.AndroidJUnit4;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EventsProviderTest {
	EventsProvider provider;
	EventList list;
	Event event;

	@Before public void setUp() throws Exception {
		provider = new EventsProvider();
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

	}
}
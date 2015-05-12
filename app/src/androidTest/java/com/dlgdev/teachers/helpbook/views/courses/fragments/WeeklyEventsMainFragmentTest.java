package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;

import com.activeandroid.query.Select;
import com.dlgdev.teachers.helpbook.models.events.Event;
import com.dlgdev.teachers.helpbook.models.events.EventsProvider;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class WeeklyEventsMainFragmentTest {
	WeeklyEventsMainFragment fragment;
	DateTime currentDate = DateTime.now();
	WeeklyEventsFragment.WeeklyEventsListener listener;
	Event event;

	@Before public void setup() throws Exception {
		Context context = InstrumentationRegistry.getTargetContext();
		fragment = (WeeklyEventsMainFragment) Fragment
				.instantiate(context, WeeklyEventsMainFragment.class.getName());
		fragment.updateDate(currentDate);
		listener = mock(WeeklyEventsFragment.WeeklyEventsListener.class);
		fragment.eventsListener = listener;
	}

	@After public void tearDown() throws Exception {
		if (event != null && event.getId() != null) {
			event.delete();
		}
	}

	@Test public void testPassingANewDateUpdatesTheMainFragmentsDate() throws Exception {
		DateTime currentDate = new DateTime(fragment.referenceDate);
		whenWePassANewDate(currentDate.plusWeeks(1));
		theMainFragmentIsUpdatedWithTheNewDate(currentDate.plusWeeks(1));
	}

	private void whenWePassANewDate(DateTime date) {
		fragment.updateDate(date);
	}

	private void theMainFragmentIsUpdatedWithTheNewDate(DateTime date) {
		assertEquals(fragment.referenceDate, date);
	}

	@Test public void creatingAnEventCallsTheEventCreatedCallback() throws Exception {
		whenWeCreateAnEvent();
		theCallbackMethodIsCalled();
	}

	private void whenWeCreateAnEvent() {
		event = new EventsProvider().createEmpty();
		fragment.onNewEventCreated(event);
	}

	private void theCallbackMethodIsCalled() {
		verify(listener).onNewEventCreated(event);
	}

	@Test public void creatingAnEventCallsTheEventSaveMethod() throws Exception {
		whenWeCreateAnEvent();
		theEventWasCreated();
	}

	private void theEventWasCreated() {
		Event event = new Select().from(Event.class).orderBy("_ID ASC").limit(1).executeSingle();
		assertEquals(event.getId(), this.event.getId());
	}
}
package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;

import com.activeandroid.ActiveAndroid;
import com.dlgdev.teachers.helpbook.model.events.Event;

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
	Event event = mock(Event.class);

	@Before public void setup() throws Exception {
		ActiveAndroid.initialize(InstrumentationRegistry.getContext());
		Context context = InstrumentationRegistry.getTargetContext();
		fragment = (WeeklyEventsMainFragment) Fragment.instantiate(context,
				WeeklyEventsMainFragment.class.getName());
		fragment.updateDate(currentDate);
		listener = mock(WeeklyEventsFragment.WeeklyEventsListener.class);
	}

	@After public void teardown() throws Exception {
		//ActiveAndroid.dispose();
	}

	@Test public void testClickingNextWeekFragmentViewUpdatesTheMainFragmentsDate()
			throws Exception {
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
		fragment.onNewEventCreated(event);
	}

	private void theCallbackMethodIsCalled() {
		verify(listener).onNewEventCreated(event);
	}

	@Test public void creatingAnEventCallsTheEventSaveMethod() throws Exception {
		whenWeCreateAnEvent();
		theEventSaveIsCalled();
	}

	private void theEventSaveIsCalled() {
		verify(event).save();
	}
}
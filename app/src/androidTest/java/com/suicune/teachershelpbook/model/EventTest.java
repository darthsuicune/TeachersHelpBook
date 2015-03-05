package com.suicune.teachershelpbook.model;

import android.test.InstrumentationTestCase;
import android.text.format.Time;

import com.suicune.teachershelpbook.model.courses.Course;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.EventsProvider;

import java.util.Date;

public class EventTest extends InstrumentationTestCase {
	Event event;
	EventsProvider factory;
	private Course course;

	public void setUp() throws Exception {
		super.setUp();
		course = new Course(new Date(), new Date(),
				getInstrumentation().getTargetContext().getContentResolver());
		factory = new EventsProvider(course);
	}

	public void testIsAtPresentTime() throws Exception {
		Time time = new Time();
		anEventAt(time);
		isAt(time);
	}

	private void anEventAt(Time time) {
		event = factory.createEmpty(time);
	}

	private void isAt(Time time) {
		Time newTime = new Time(time);
		newTime.minute += 30;
		assertTrue(event.isAt(newTime));
	}

	public void testIsBetween() throws Exception {
		Time time = new Time();
		anEventAt(new Time());
		isBetween(time);
	}

	private void isBetween(Time time) {
		Time startRange = new Time(time);
		Time endRange = new Time(time);
		startRange.hour -= 1;
		endRange.hour += 1;
		assertTrue(event.isBetween(startRange, endRange));
	}
}
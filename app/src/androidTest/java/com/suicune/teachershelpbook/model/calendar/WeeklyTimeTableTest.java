package com.suicune.teachershelpbook.model.calendar;

import android.text.format.Time;

import com.suicune.teachershelpbook.model.Event;

import junit.framework.TestCase;

import java.util.Date;

public class WeeklyTimeTableTest extends TestCase {
	public static final DayOfWeek WORKING_DAY = DayOfWeek.MONDAY;
	public static final DayOfWeek WEEKEND_DAY = DayOfWeek.SATURDAY;
	WeeklyTimeTable weeklyTimeTable;
	EventsProvider provider;

	@Override protected void setUp() throws Exception {
		super.setUp();
		provider = new EventsProvider();
		weeklyTimeTable = new WeeklyTimeTable(new Week(new Date()), provider);
	}

	public void testAddEventsOnWorkingDayWorks() throws Exception {
		withAProperEventOn(WORKING_DAY);
		theTimeTableContainsAnEventOn(WORKING_DAY);
	}

	private void withAProperEventOn(DayOfWeek day) {
		Event event = provider.createEmpty();
		weeklyTimeTable.addEvent(day, event);
	}

	private void theTimeTableContainsAnEventOn(DayOfWeek day) {
		assertTrue(weeklyTimeTable.hasEventsFor(day));
	}

	public void testDailyEventsForWeekDay() throws Exception {
		withAProperEventOn(WORKING_DAY);
		whenWeAskForAnEventOn(WORKING_DAY);
		theDayHasEvents(WORKING_DAY);
	}

	private void whenWeAskForAnEventOn(DayOfWeek day) {
		weeklyTimeTable.eventsFor(day);
	}

	private void theDayHasEvents(DayOfWeek day) {
		assertTrue(weeklyTimeTable.hasEventsFor(day));
	}

	public void testAddEventsOnNonWorkingDaysThrowsException() throws Exception {
		boolean thrown = false;
		try {
			withAProperEventOn(WEEKEND_DAY);
		} catch(NotAWorkingDayException e) {
			thrown = true;
		} finally {
			exceptionWasThrown(thrown);
		}
	}

	public void testDailyEventsForWeekendThrowsException() throws Exception {
		boolean thrown = false;
		try {
			whenWeAskForAnEventOn(WEEKEND_DAY);
		} catch(NotAWorkingDayException e) {
			thrown = true;
		} finally {
			exceptionWasThrown(thrown);
		}
	}

	private void exceptionWasThrown(boolean thrown) {
		assertTrue(thrown);
	}

	public void testEventsAtTimeReturnsAnEvent() throws Exception {
		Event event = whenWeAskForAnEventOnDayAt(WORKING_DAY, new Time());
		weGetAValidEvent(event);
	}

	private Event whenWeAskForAnEventOnDayAt(DayOfWeek day, Time time) {
		return weeklyTimeTable.eventAt(day, time);
	}

	private void weGetAValidEvent(Event event) {
		assertNotNull(event);
	}

	public void testEventsOnWeekendThrowsException() throws Exception {
		boolean thrown = false;
		try {
			whenWeAskForAnEventOnDayAt(WEEKEND_DAY, new Time());
		} catch(NotAWorkingDayException e) {
			thrown = true;
		} finally {
			exceptionWasThrown(thrown);
		}
	}
}
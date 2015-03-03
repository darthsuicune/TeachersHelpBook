package com.suicune.teachershelpbook.model.calendar;

import android.text.format.Time;

import com.suicune.teachershelpbook.model.Event;

import junit.framework.TestCase;

import java.util.Date;

public class EventsProviderTest extends TestCase {
	public static final DayOfWeek WORKING_DAY = DayOfWeek.MONDAY;
	public static final DayOfWeek WEEKEND_DAY = DayOfWeek.SATURDAY;
	public static Week week = new Week(new Date());

	EventsProvider factory;
	DailyEvents dailyEvents;
	Event event;

	@Override
    public void setUp() throws Exception {
        super.setUp();
		factory = new EventsProvider();
    }

    public void testCreateDailyForWeekdayWorks() throws Exception {
		whenWeCreateADailyEventsList(week, WORKING_DAY);
		itReturnsAValidEventList();
    }

	private void whenWeCreateADailyEventsList(Week week, DayOfWeek day) {
		dailyEvents = factory.createDaily(week, day);
	}

	private void itReturnsAValidEventList() {
		assertNotNull(dailyEvents);
	}

	public void testCreateDailyForWeekendThrowsException() throws Exception {
		boolean thrown = false;
		try {
			whenWeCreateADailyEventsListForWeekend(week, WEEKEND_DAY);
		} catch (NotAWorkingDayException e) {
			thrown = true;
		} finally {
			anExceptionWasThrown(thrown);
		}

	}

	private void whenWeCreateADailyEventsListForWeekend(Week week, DayOfWeek day) {
		dailyEvents = factory.createDaily(week, day);
	}

	private void anExceptionWasThrown(boolean thrown) {
		assertTrue(thrown);
	}

	public void testCreateEmptyCreatesAnEventNowThatLastsForAnHour() throws Exception {
		whenWeCreateAnEmptyEvent();
		itLastsForAnHourWithStartAt(now());
    }

	private void whenWeCreateAnEmptyEvent() {
		event = factory.createEmpty();
	}

	private Time now() {
		Time time = new Time();
		time.setToNow();
		return time;
	}

	private void itLastsForAnHourWithStartAt(Time now) {
		Time end = new Time(now);
		end.hour += EventsProvider.DEFAULT_EVENT_DURATION;
		assertTrue(event.isBetween(now, end));
	}

	public void testCreateEmptyWithStartTime() throws Exception {
		Time time = now();
		whenWeCreateAnEmptyEventWithStartAt(time);
		itLastsForAnHourWithStartAt(time);
    }

	private void whenWeCreateAnEmptyEventWithStartAt(Time time) {
		event = factory.createEmpty(time);
	}

	public void testCreateEmptyWithStartAndValidEndTime() throws Exception {
		Time start = past();
		Time end = now();
		whenWeCreateAnEmptyEventBetween(start, end);
		weGetAValidEvent();
	}

	private Time past() {
		return new Time();
	}

	private void whenWeCreateAnEmptyEventBetween(Time start, Time end) {
		event = factory.createEmpty(start, end);
	}

	private void weGetAValidEvent() {
		assertNotNull(event);
	}

	public void testCreateEmptyWithStartAndInvalidEndTime() throws Exception {
		boolean thrown = false;
		try {
			Time start = now();
			Time end = new Time();
			whenWeCreateAnEmptyEventBetween(start, end);
		} catch (InvalidTimeRangeException e) {
			thrown = true;
		} finally {
			anExceptionWasThrown(thrown);
		}
	}

	public void testNewEventAt() throws Exception {
		Time time = now();
		whenWeCreateAnEventWithStartAt(time);
		itLastsForAnHourWithStartAt(time);
    }

	private void whenWeCreateAnEventWithStartAt(Time time) {
		event = factory.newEventAt(time, "sometitle", "somedesc");
	}

	public void testNewEventBetweenValidTimes() throws Exception {
		Time start = past();
		Time end = now();
		whenWeCreateAnEventBetween(start, end);
		weGetAValidEvent();
    }

	public void testNewEventBetweenInvalidTimes() throws Exception {
		boolean thrown = false;
		try {
			Time start = now();
			Time end = new Time();
			whenWeCreateAnEventBetween(start, end);
		} catch (InvalidTimeRangeException e) {
			thrown = true;
		} finally {
			anExceptionWasThrown(thrown);
		}
	}

	private void whenWeCreateAnEventBetween(Time start, Time end) {
		event = factory.newEventBetween(start, end, "sometitle", "somedesc");
	}
}
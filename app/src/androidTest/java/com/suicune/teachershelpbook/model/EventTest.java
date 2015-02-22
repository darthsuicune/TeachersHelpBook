package com.suicune.teachershelpbook.model;

import android.text.format.Time;

import com.suicune.teachershelpbook.model.calendar.EventsFactory;

import junit.framework.TestCase;

public class EventTest extends TestCase {
    Event event;
    EventsFactory factory;

    public void setUp() throws Exception {
        super.setUp();
        factory = new EventsFactory();
    }

    public void testIsAtPresentTime() throws Exception {
        Time time = new Time();
        anEventAt(time);
        isAt(time);
    }

    private void anEventAt(Time time) {
        event = factory.newEventAt(time);
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
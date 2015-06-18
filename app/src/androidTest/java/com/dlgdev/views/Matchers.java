package com.dlgdev.views;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.joda.time.DateTime;

public class Matchers {

	public static Matcher<DateTime> matchesAnyDateTime() {
		return new BaseMatcher<DateTime>() {
			@Override public void describeTo(Description description) {

			}

			@Override public boolean matches(Object o) {
				return o instanceof DateTime;
			}
		};
	}
}

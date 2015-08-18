package com.dlgdev.views;

import org.joda.time.DateTime;
import org.mockito.ArgumentMatcher;

public class Matchers {

	public static ArgumentMatcher<DateTime> matchesAnyDateTime() {
		return new ArgumentMatcher<DateTime>() {
			@Override public boolean matches(Object o) {
				return o instanceof DateTime;
			}
		};
	}
}

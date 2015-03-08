package com.suicune.teachershelpbook.model.courses;

import android.test.InstrumentationTestCase;

import org.joda.time.DateTime;

public class CourseTest extends InstrumentationTestCase {
	Course course;

	@Override public void setUp() throws Exception {
		DateTime start = new DateTime();
		DateTime end = new DateTime();
		course = new Course(start, end, getInstrumentation().getContext().getContentResolver());
	}
}

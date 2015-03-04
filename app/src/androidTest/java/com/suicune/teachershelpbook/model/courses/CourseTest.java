package com.suicune.teachershelpbook.model.courses;

import android.test.InstrumentationTestCase;

import java.util.Date;

/**
 * Created by lapuente on 03.03.15.
 */
public class CourseTest extends InstrumentationTestCase {
	Course course;

	@Override public void setUp() throws Exception {
		Date start = new Date();
		Date end = new Date();
		course = new Course(start, end, getInstrumentation().getContext().getContentResolver());
	}
}

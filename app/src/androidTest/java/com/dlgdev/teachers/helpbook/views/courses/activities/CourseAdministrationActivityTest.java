package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.models.Course;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CourseAdministrationActivityTest {

	CourseAdministrationActivity activity;
	Course course;

	@Rule public ActivityTestRule<CourseAdministrationActivity> rule =
			new ActivityTestRule<>(CourseAdministrationActivity.class, true, false);

	@Before public void setUp() throws Exception {
		course = new Course();
		course.save();
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testOnCreateLoadsTheCorrectCourse() throws Exception {
		assertEquals(course.getId(), activity().course.getId());
	}

	private CourseAdministrationActivity activity() {
		Intent intent = new Intent();
		intent.putExtra(CourseAdministrationActivity.KEY_COURSE, course.getId());
		activity = rule.launchActivity(intent);
		return activity;
	}
}
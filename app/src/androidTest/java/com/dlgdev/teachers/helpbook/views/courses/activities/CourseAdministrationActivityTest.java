package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.query.Delete;
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
		new Delete().from(Course.class).execute();
	}

	@Test public void testOnCreateLoadsTheCorrectCourse() throws Exception {
		launchActivity();
		assertEquals(course.getId(), activity.course.getId());
	}

	private void launchActivity() {
		Intent intent = new Intent();
		intent.putExtra(CourseAdministrationActivity.KEY_COURSE, course.getId());
		activity = rule.launchActivity(intent);
	}
}
package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.models.Course;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CoursesListActivityWithDefaultTest {

	public static final String TITLE = "Title";
	@Rule public ActivityTestRule<CoursesListActivity> rule =
			new ActivityTestRule<>(CoursesListActivity.class, true, false);

	CoursesListActivity activity;

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void withACurrentCourseItShouldSkipToCourseOverview() throws Exception {
		// Simply verify that one of the new activity views is displayed.
		// Can't catch the intent because it's thrown on the onCreate method.
		launchActivityWithCourse();
		onView(withText(TITLE)).check(matches(isDisplayed()));
	}

	private void launchActivityWithCourse() {
		Course course = new Course(DateTime.now().minusWeeks(1), DateTime.now().plusWeeks(1));
		course.title = TITLE;
		course.save();
		activity = rule.launchActivity(new Intent());
	}
}
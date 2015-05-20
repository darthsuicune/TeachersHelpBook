package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class CoursesListActivityWithDefaultTest {

	@Rule public ActivityTestRule<CoursesListActivity> rule =
			new ActivityTestRule<>(CoursesListActivity.class);

	@BeforeClass public static void setup() throws Exception {
		Course course = new Course(DateTime.now().minusWeeks(1), DateTime.now().plusWeeks(1));
		course.save();
	}

	@AfterClass public static void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void withACurrentCourseItShouldSkipToCourseOverview() throws Exception {
		// Simply verify that one of the new activity views is displayed
		onView(withId(R.id.course_overview_panel)).check(matches(isDisplayed()));
	}
}
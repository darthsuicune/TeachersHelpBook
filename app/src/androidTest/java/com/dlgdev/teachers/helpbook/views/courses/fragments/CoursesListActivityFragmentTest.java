package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.views.courses.activities.CoursesListActivity;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class CoursesListActivityFragmentTest {
	Course course;

	@Rule public ActivityTestRule<CoursesListActivity> rule =
			new ActivityTestRule<>(CoursesListActivity.class);

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void withoutACurrentCourseItShouldDisplayAnEmptyList() throws Exception {
		onView(withId(R.id.course_list_empty)).check(matches(isDisplayed()));
		onView(withId(R.id.course_list)).check(matches(not(isDisplayed())));
	}

	@Test public void withACourseNotCurrentItShouldDisplayItInTheList() throws Exception {
		afterCreatingACourse();
		onView(withId(R.id.course_list)).check(matches(isDisplayed()));
		onView(withId(R.id.course_list_empty)).check(matches(not(isDisplayed())));
	}

	private void afterCreatingACourse() {
		course = new Course(DateTime.now().minusWeeks(1), DateTime.now().plusWeeks(1));
		course.save();
	}
}
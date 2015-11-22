package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.domain.models.Course;
import com.dlgdev.teachers.helpbook.domain.models.repositories.CoursesRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CourseAdministrationActivityTest {

	public static final String COURSE_TITLE = "course title";
	public static final String COURSE_DESCRIPTION = "course description";

	CourseAdministrationActivity activity;
	Course course;

	@Rule public ActivityTestRule<CourseAdministrationActivity> rule =
			new ActivityTestRule<>(CourseAdministrationActivity.class, true, false);

	@Before public void setUp() throws Exception {
		course = new CoursesRepository().create();
		course.title = COURSE_TITLE;
		course.description = COURSE_DESCRIPTION;
		course.save();
	}

	private void activity(long id) {
		Intent intent = new Intent();
		intent.putExtra(CourseAdministrationActivity.KEY_MODEL_ID, id);
		activity = rule.launchActivity(intent);
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testOnCreateLoadsTheCorrectCourse() throws Exception {
		activity(course.id);
		onView(withId(R.id.course_administration_name)).check(matches(withText(COURSE_TITLE)));
		onView(withId(R.id.course_administration_description))
				.check(matches(withText(COURSE_DESCRIPTION)));
	}

	@Test public void bugHavingTwoCoursesDisplaysOnlyTheFirst() throws Exception {
		String title = "other title";
		long id = createSecondCourse(title);
		activity(id);
		onView(withId(R.id.course_administration_name)).check(matches(withText(title)));
	}

	private long createSecondCourse(String title) {
		Course secondCourse = new CoursesRepository().create();
		secondCourse.title = title;
		secondCourse.description = "other description";
		secondCourse.save();
		return secondCourse.id;
	}
}
package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.support.test.espresso.intent.matcher.ComponentNameMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class CoursesListActivityWithoutDefaultTest {
	Course course;

	@Rule public IntentsTestRule<CoursesListActivity> rule =
			new IntentsTestRule<>(CoursesListActivity.class);
	private CoursesListActivity activity;

	@Before public void setup() throws Exception {
		activity = rule.getActivity();
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void preconditionsAreMatched() throws Exception {
		assertNotNull(activity.fragment);
		onView(withText(R.string.course_list_empty)).check(matches(isDisplayed()));
	}

	@Test public void requestingACourseFromTheListCallsTheIntent() throws Exception {
		course = new Course(DateTime.now().minusWeeks(1), DateTime.now().plusWeeks(1));
		course.save();
		activity.onCourseSelected(course);
		itThrowsAnIntentWithTheCourseId();
	}

	private void itThrowsAnIntentWithTheCourseId() {
		String expectedComponentName = ".views.courses.activities.CourseOverviewActivity";
		String expectedPackageName = "com.dlgdev.teachers.helpbook";
		intended(allOf(hasComponent(ComponentNameMatchers.hasShortClassName(expectedComponentName)),
				toPackage(expectedPackageName),
				hasExtras(hasEntry(CourseOverviewActivity.KEY_COURSE, course.getId()))));
	}

	@Test public void whenANewCourseIsRequestedTheAppropiateIntentIsThrown() throws Exception {
		activity.onNewCourseRequested();
		itThrowsAnIntentForANewCourse();
	}

	private void itThrowsAnIntentForANewCourse() {
		String expectedComponentName = ".views.courses.activities.CourseAdministrationActivity";
		String expectedPackageName = "com.dlgdev.teachers.helpbook";
		intended(allOf(hasComponent(ComponentNameMatchers.hasShortClassName(expectedComponentName)),
				toPackage(expectedPackageName)));
	}
}
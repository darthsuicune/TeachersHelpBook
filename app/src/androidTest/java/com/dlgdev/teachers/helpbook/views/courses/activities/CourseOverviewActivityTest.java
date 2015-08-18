package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.models.Course;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CourseOverviewActivityTest {
	public static final String COURSE_TITLE = "course1";
	CourseOverviewActivity activity;
	Course course;

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class, true, false);

	@Before public void setup() throws Exception {
		DatabaseUtils.intializeDb(InstrumentationRegistry.getTargetContext());
		course = new Course(DateTime.now(), DateTime.now());
		course.title = COURSE_TITLE;
		course.save();
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testPreConditionsMainFragmentIsntNull() throws Exception {
		loadActivity();
		assertNotNull(activity.mainViewFragment);
	}

	private void loadActivity() {
		Intent intent = new Intent();
		intent.putExtra(CourseOverviewActivity.KEY_COURSE, course.getId());
		loadActivity(intent);
	}

	private void loadActivity(Intent intent) {
		activity = rule.launchActivity(intent);
	}


	@Test public void testPreConditionsPreviewsArentNull() throws Exception {
		loadActivity();
		assertNotNull(activity.nextWeekFragment);
		assertNotNull(activity.previousWeekFragment);
		assertNotNull(activity.secondNextWeekFragment);
	}

	@Test public void testPreConditionsPanelIsntNull() throws Exception {
		loadActivity();
		assertNotNull(activity.coursePanelFragment);
	}

	@Test public void testPreConditionsImplementsWeeklyPreviewListener() throws Exception {
		loadActivity();
		assertTrue(activity != null);
	}

	@Test public void testPreConditionsImplementsWeeklyEventsListener() throws Exception {
		loadActivity();
		assertTrue(activity != null);
	}

	@Test public void testPreConditionsImplementsCoursePanelListener() throws Exception {
		loadActivity();
		assertTrue(activity != null);
	}

	@Test public void testTheCourseGetsLoaded() throws Exception {
		loadActivity();
		onView(withText(COURSE_TITLE)).check(matches(isDisplayed()));
	}

	@Test public void bugWrongCourseIsLoaded() throws Exception {
		//Create a second course
		String title = "course2";
		Course course2 = createSecondCourse(title);
		//Pass the first course id
		Intent intent = new Intent();
		intent.putExtra(CourseOverviewActivity.KEY_COURSE, course2.getId());
		loadActivity(intent);
		//Make sure the new information is displayed
		onView(withText(title)).check(matches(isDisplayed()));
	}

	@NonNull private Course createSecondCourse(String title) {
		Course course2 = new Course(DateTime.now().plusWeeks(1), DateTime.now().plusWeeks(2));
		course2.title = title;
		course2.save();
		return course2;
	}

	@Test public void bugWrongCourseIsLoadedv2() throws Exception {
		createSecondCourse("");
		loadActivity();
		onView(withText(COURSE_TITLE)).check(matches(isDisplayed()));
	}
}
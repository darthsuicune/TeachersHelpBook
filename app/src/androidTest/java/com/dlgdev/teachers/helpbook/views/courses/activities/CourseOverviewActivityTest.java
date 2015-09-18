package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.views.ModelInfoActivity;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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
		intent.putExtra(ModelInfoActivity.KEY_MODEL_ID, course.id);
		loadActivity(intent);
	}

	private void loadActivity(Intent intent) {
		activity = rule.launchActivity(intent);
	}


	@Test public void testPreConditionsPreviewsAreDisplayed() throws Exception {
		loadActivity();
		onView(withId(R.id.course_weekly_next)).check(matches(isDisplayed()));
		onView(withId(R.id.course_weekly_second_next)).check(matches(isDisplayed()));
		onView(withId(R.id.course_weekly_previous)).check(matches(isDisplayed()));
	}

	@Test public void testPreConditionsPanelIsDisplayed() throws Exception {
		loadActivity();
		onView(withId(R.id.course_info_panel)).check(matches(isDisplayed()));
	}

	@Test public void testPreConditionsImplementsItsFragmentsCallbacks() throws Exception {
		loadActivity();
		//This check suffices: If they aren't implemented an exception is thrown
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
		intent.putExtra(CourseOverviewActivity.KEY_MODEL_ID, course2.id);
		loadActivity(intent);
		//Make sure the new information is displayed
		onView(withText(title)).check(matches(isDisplayed()));
	}

	private Course createSecondCourse(String title) {
		Course course2 = new Course(DateTime.now().plusWeeks(1), DateTime.now().plusWeeks(2));
		course2.title = title;
		course2.save();
		return course2;
	}

	@Test public void bugWrongCourseIsLoadedv2() throws Exception {
		createSecondCourse("asdas");
		loadActivity();
		onView(withText(COURSE_TITLE)).check(matches(isDisplayed()));
	}

	@Test public void changingTheDataUpdatesTheViews() throws Exception {
		loadActivity();
		String title = "new Title";
		course.title = title;
		course.save();
		onView(withText(title)).check(matches(isDisplayed()));
	}

	@Test public void navigationDrawerIsIncluded() throws Throwable {
		loadActivity();
		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				activity.openDrawer();
			}
		});
		onView(withId(R.id.course_overview_navigation_drawer)).check(matches(isDisplayed()));
	}
}
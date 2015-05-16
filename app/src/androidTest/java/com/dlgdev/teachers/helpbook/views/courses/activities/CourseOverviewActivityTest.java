package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.support.test.espresso.intent.matcher.ComponentNameMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.query.Select;
import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CoursePanelFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsPreviewFragment;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CourseOverviewActivityTest {
	CourseOverviewActivity activity;
	Course course;

	@Rule public IntentsTestRule<CourseOverviewActivity> rule =
			new IntentsTestRule<>(CourseOverviewActivity.class);

	@BeforeClass public static void loadCourse() throws Exception {
		Course course = new Course(DateTime.now(), DateTime.now());
		course.save();
	}

	@Before public void setup() throws Exception {
		course = new Select().from(Course.class).executeSingle();
	}

	@AfterClass public static void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testPreConditionsMainFragmentIsntNull() throws Exception {
		loadActivity();
		assertNotNull(activity.mainViewFragment);
	}

	private void loadActivity() {
		activity = rule.getActivity();
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
		assertTrue(activity instanceof WeeklyEventsPreviewFragment.WeeklyPreviewListener);
	}

	@Test public void testPreConditionsImplementsWeeklyEventsListener() throws Exception {
		loadActivity();
		assertTrue(activity instanceof WeeklyEventsFragment.WeeklyEventsListener);
	}

	@Test public void testPreConditionsImplementsCoursePanelListener() throws Exception {
		loadActivity();
		assertTrue(activity instanceof CoursePanelFragment.CoursePanelListener);
	}

	@Test public void testPreConditionsCourseInformationIsRetrieved() throws Exception {
		loadActivity();
		assertEquals(activity.course.getId(), course.getId());
	}

	@Test public void onPanelTappedCallsTheCourseAdministrationActivity() throws Throwable {
		loadActivity();
		activity.onPanelTapped();
		String expectedComponentName = ".views.courses.activities.CourseAdministrationActivity";
		String expectedPackageName = "com.dlgdev.teachers.helpbook";
		intended(allOf(
				hasComponent(ComponentNameMatchers.hasShortClassName(expectedComponentName)),
				toPackage(expectedPackageName),
				hasExtras(hasEntry(CourseAdministrationActivity.KEY_COURSE, course.getId()))
		));
	}
}
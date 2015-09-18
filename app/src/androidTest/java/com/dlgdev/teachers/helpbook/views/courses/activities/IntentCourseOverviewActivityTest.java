package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.ComponentNameMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.views.ModelInfoActivity;
import com.dlgdev.teachers.helpbook.views.events.activities.EventsInfoActivity;
import com.dlgdev.teachers.helpbook.views.holidays.activities.HolidaysInfoActivity;
import com.dlgdev.teachers.helpbook.views.students.activities.StudentGroupsInfoActivity;
import com.dlgdev.teachers.helpbook.views.subjects.activities.SubjectsInfoActivity;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class IntentCourseOverviewActivityTest {

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class, true, false);
	Course course;
	CourseOverviewActivity activity;

	@Before public void setup() throws Exception {
		Intents.init();
		course = new Course(DateTime.now(), DateTime.now());
		course.title = "course1";
		course.save();
		Intent intent = new Intent();
		intent.putExtra(CourseOverviewActivity.KEY_MODEL_ID, course.id);
		activity = rule.launchActivity(intent);
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
		Intents.release();
	}

	@Test public void onPanelTappedCallsTheCourseAdministrationActivity() throws Throwable {
		activity.onPanelTapped(course);
		String expectedComponentName = ".views.courses.activities.CourseAdministrationActivity";
		String expectedPackageName = "com.dlgdev.teachers.helpbook";
		intended(allOf(hasComponent(ComponentNameMatchers.hasShortClassName(expectedComponentName)),
				toPackage(expectedPackageName),
				hasExtras(hasEntry(CourseAdministrationActivity.KEY_MODEL_ID, course.id))));
	}

	@Test public void clickOnSubjectsThrowsAnIntentToSeeTheSubjects() throws Exception {
		activity.onSubjectsRequested();
		dispatchesAnIntentFor(SubjectsInfoActivity.class);
	}

	private void dispatchesAnIntentFor(Class<? extends ModelInfoActivity> activity) {
		intended(allOf(hasExtra(ModelInfoActivity.KEY_MODEL_ID, course.id),
				hasComponent(hasClassName(activity.getName()))));
	}

	@Test public void clickOnStudentGroupsThrowsAnIntentToSeeTheSubjects() throws Exception {
		activity.onStudentGroupsRequested();
		dispatchesAnIntentFor(StudentGroupsInfoActivity.class);
	}

	@Test public void clickOnEventsThrowsAnIntentToSeeTheSubjects() throws Exception {
		activity.onEventsRequested();
		dispatchesAnIntentFor(EventsInfoActivity.class);
	}

	@Test public void clickOnHolidaysThrowsAnIntentToSeeTheSubjects() throws Exception {
		activity.onHolidaysRequested();
		dispatchesAnIntentFor(HolidaysInfoActivity.class);
	}

	@Test public void requestingTheCourseInfoCallsTheActivity() throws Exception {
		activity.onCourseInfoRequested();
		dispatchesAnIntentFor(CourseAdministrationActivity.class);
	}
}
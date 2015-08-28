package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.support.test.espresso.intent.matcher.ComponentNameMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.models.Course;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class IntentCourseOverviewActivityTest {

	@Rule public IntentsTestRule<CourseOverviewActivity> rule =
			new IntentsTestRule<>(CourseOverviewActivity.class);
	Course course;
	CourseOverviewActivity activity;

	@Before public void setup() throws Exception {
		course = new Course(DateTime.now(), DateTime.now());
		course.title = "course1";
		course.save();
		activity = rule.getActivity();
	}

	@Test public void onPanelTappedCallsTheCourseAdministrationActivity() throws Throwable {
		activity.onPanelTapped(course);
		String expectedComponentName = ".views.courses.activities.CourseAdministrationActivity";
		String expectedPackageName = "com.dlgdev.teachers.helpbook";
		intended(allOf(hasComponent(ComponentNameMatchers.hasShortClassName(expectedComponentName)),
				toPackage(expectedPackageName),
				hasExtras(hasEntry(CourseAdministrationActivity.KEY_COURSE, course.getId()))));
	}

}
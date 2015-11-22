package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.domain.models.Course;
import com.dlgdev.teachers.helpbook.views.ModelInfoActivity;
import com.dlgdev.teachers.helpbook.views.events.activities.EventsInfoActivity;
import com.dlgdev.teachers.helpbook.views.holidays.activities.HolidaysInfoActivity;
import com.dlgdev.teachers.helpbook.views.students.activities.StudentGroupsInfoActivity;
import com.dlgdev.teachers.helpbook.views.subjects.activities.SubjectsInfoActivity;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ollie.Model;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasPackageName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class IntentCourseAdministrationActivityTest {
	public static final String PACKAGE_NAME = "com.dlgdev.teachers.helpbook";

	CourseAdministrationActivity activity;
	Course course;

	@Rule public IntentsTestRule<CourseAdministrationActivity> rule =
			new IntentsTestRule<>(CourseAdministrationActivity.class);

	@Before public void setup() throws Exception {
		course = course();
		activity = rule.getActivity();
		activity.course = course;
	}

	private Course course() {
		course = new Course();
		course.save();
		return course;
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testOnSaved() throws Exception {
		activity.onSaved(course);
		intended(intentFor(course, CourseOverviewActivity.class));
	}

	private Matcher<Intent> intentFor(Model model, Class<? extends ModelInfoActivity> targetClass) {
		return allOf(
				hasExtra(ModelInfoActivity.KEY_MODEL_ID, model.id),
				toPackage(PACKAGE_NAME),
				hasComponent(
						allOf(hasPackageName(PACKAGE_NAME), hasClassName(targetClass.getName()))));
	}

	@Test public void testOnStudentGroupsInfoRequested() throws Exception {
		activity.onStudentGroupsInfoRequested();
		intended(intentFor(course, StudentGroupsInfoActivity.class));
	}

	@Test public void testOnHolidaysInfoRequested() throws Exception {
		activity.onHolidaysInfoRequested();
		intended(intentFor(course, HolidaysInfoActivity.class));
	}

	@Test public void testOnEventsInfoRequested() throws Exception {
		activity.onEventsInfoRequested();
		intended(intentFor(course, EventsInfoActivity.class));
	}

	@Test public void testOnSubjectsInfoRequested() throws Exception {
		activity.onSubjectsInfoRequested();
		intended(intentFor(course, SubjectsInfoActivity.class));
	}
}

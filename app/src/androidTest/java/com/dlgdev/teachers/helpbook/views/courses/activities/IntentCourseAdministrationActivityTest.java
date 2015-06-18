package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.Holiday;
import com.dlgdev.teachers.helpbook.models.StudentGroup;
import com.dlgdev.teachers.helpbook.models.Subject;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class IntentCourseAdministrationActivityTest {

	public static final String SUBJECT_TITLE = "subject title";
	public static final String STUDENT_GROUP_TITLE = "student group title";
	public static final String BANK_HOLIDAY_TITLE = "bank holiday title";
	public static final String EVENT_TITLE = "event title";

	public static final String SUBJECT_NAME = "subject name";
	public static final String STUDENT_GROUP_NAME = "student group name";
	public static final String BANK_HOLIDAY_NAME = "bank holiday name";
	public static final String EVENT_NAME = "event name";

	CourseAdministrationActivity activity;
	Course course;

	@Rule public IntentsTestRule<CourseAdministrationActivity> rule =
			new IntentsTestRule<>(CourseAdministrationActivity.class);

	@Before public void setup() throws Exception {
		course = course();
	}

	private Course course() {
		course = new Course();
		return course;
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testOnNewSubjectRequested() throws Exception {
		activity().onNewSubjectRequested();
		onView(withText(SUBJECT_TITLE)).check(matches(isDisplayed()));
	}

	private CourseAdministrationActivity activity() {
		activity = rule.getActivity();
		return activity;
	}

	@Test public void testOnSubjectSelected() throws Exception {
		activity().onSubjectSelected(subject());
		onView(withText(SUBJECT_NAME)).check(matches(isDisplayed()));
	}

	private Subject subject() {
		return null;
	}

	@Test public void testOnNewBankHolidayRequested() throws Exception {
		activity().onNewBankHolidayRequested();
		onView(withText(BANK_HOLIDAY_TITLE)).check(matches(isDisplayed()));
	}

	@Test public void testOnBankHolidaySelected() throws Exception {
		activity().onBankHolidaySelected(bankHoliday());
		onView(withText(BANK_HOLIDAY_NAME)).check(matches(isDisplayed()));
	}

	private Holiday bankHoliday() {
		return null;
	}

	@Test public void testOnNewGroupRequested() throws Exception {
		activity().onNewGroupRequested();
		onView(withText(STUDENT_GROUP_TITLE)).check(matches(isDisplayed()));
	}

	@Test public void testOnGroupSelected() throws Exception {
		activity().onGroupSelected(studentGroup());
		onView(withText(STUDENT_GROUP_NAME)).check(matches(isDisplayed()));
	}

	private StudentGroup studentGroup() {
		return null;
	}

	@Test public void testOnNewEventRequested() throws Exception {
		activity().onNewEventRequested();
		onView(withText(EVENT_TITLE)).check(matches(isDisplayed()));
	}

	@Test public void testOnEventSelected() throws Exception {
		activity().onEventSelected(event());
		onView(withText(EVENT_NAME)).check(matches(isDisplayed()));
	}

	private Event event() {
		return null;
	}

	@Test public void testOnSaved() throws Exception {
		activity().onSaved(course());

	}
}

package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.ActiveAndroid;
import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.Holiday;
import com.dlgdev.teachers.helpbook.models.StudentGroup;
import com.dlgdev.teachers.helpbook.models.Subject;
import com.dlgdev.teachers.helpbook.views.ModelInfoActivity;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasPackageName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertTrue;

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
	public static final String PACKAGE_NAME = "com.dlgdev.teachers.helpbook";

	CourseAdministrationActivity activity;
	Course course;

	@Rule public IntentsTestRule<CourseAdministrationActivity> rule =
			new IntentsTestRule<>(CourseAdministrationActivity.class);

	@Before public void setup() throws Exception {
		course = course();
	}

	private Course course() {
		course = new Course();
		course.save();
		return course;
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testDbName() {
		String name = ActiveAndroid.getDatabase().getPath();
		assertTrue(name.contains("test"));
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
		activity().onSaved(course);
		intended(intentFor(course.getId(), CourseOverviewActivity.class));
	}

	private Matcher<Intent> intentFor(long id, Class<? extends ModelInfoActivity> targetClass) {
		return allOf(
				hasExtra(ModelInfoActivity.KEY_ID, id),
				toPackage(PACKAGE_NAME),
				hasComponent(
						allOf(hasPackageName(PACKAGE_NAME),
								hasClassName(targetClass.getName()))));
	}
}

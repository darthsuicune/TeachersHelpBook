package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.Holiday;
import com.dlgdev.teachers.helpbook.models.StudentGroup;
import com.dlgdev.teachers.helpbook.models.Subject;

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
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CourseAdministrationActivityTest {

	public static final String SUBJECT_NAME = "subject name";
	public static final String STUDENT_GROUP_NAME = "student group name";
	public static final String BANK_HOLIDAY_NAME = "bank holiday name";
	public static final String EVENT_NAME = "event name";

	CourseAdministrationActivity activity;
	Course course;

	@Rule public ActivityTestRule<CourseAdministrationActivity> rule =
			new ActivityTestRule<>(CourseAdministrationActivity.class, true, false);

	@Before public void setUp() throws Exception {
		DatabaseUtils.intializeDb(InstrumentationRegistry.getTargetContext());
		course = new Course();
		course.save();
		activity();
	}

	private void activity() {
		Intent intent = new Intent();
		intent.putExtra(CourseAdministrationActivity.KEY_COURSE, course.getId());
		activity = rule.launchActivity(intent);
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testOnCreateLoadsTheCorrectCourse() throws Exception {
		assertEquals(course.getId(), activity.course.getId());
	}

	@Test public void testOnNewSubjectRequested() throws Exception {
		activity.onNewSubjectRequested();
		onView(withText(R.string.title_activity_subject_info)).check(matches(isDisplayed()));
	}

	@Test public void testOnSubjectSelected() throws Exception {
		activity.onSubjectSelected(subject());
		onView(withText(SUBJECT_NAME)).check(matches(isDisplayed()));
	}

	private Subject subject() {
		Subject subject = new Subject();
		subject.save();
		course.addSubject(subject);
		return subject;
	}

	@Test public void testOnNewBankHolidayRequested() throws Exception {
		activity.onNewBankHolidayRequested();
		onView(withText(R.string.title_activity_holiday_info)).check(matches(isDisplayed()));
	}

	@Test public void testOnBankHolidaySelected() throws Exception {
		activity.onBankHolidaySelected(bankHoliday());
		onView(withText(BANK_HOLIDAY_NAME)).check(matches(isDisplayed()));
	}

	private Holiday bankHoliday() {
		course.addBankHoliday(DateTime.now(), "Holiday name");
		return course.holidays().get(0);
	}

	@Test public void testOnNewGroupRequested() throws Exception {
		activity.onNewGroupRequested();
		onView(withText(R.string.title_activity_student_group_info)).check(matches(isDisplayed()));
	}

	@Test public void testOnGroupSelected() throws Exception {
		activity.onGroupSelected(studentGroup());
		onView(withText(STUDENT_GROUP_NAME)).check(matches(isDisplayed()));
	}

	private StudentGroup studentGroup() {
		StudentGroup group = new StudentGroup();
		group.save();
		return group;
	}

	@Test public void testOnNewEventRequested() throws Exception {
		activity.onNewEventRequested();
		onView(withText(R.string.title_activity_event_info)).check(matches(isDisplayed()));
	}

	@Test public void testOnEventSelected() throws Exception {
		activity.onEventSelected(event());
		onView(withText(EVENT_NAME)).check(matches(isDisplayed()));
	}

	private Event event() {
		Event event = new Event();
		event.save();
		return event;
	}
}
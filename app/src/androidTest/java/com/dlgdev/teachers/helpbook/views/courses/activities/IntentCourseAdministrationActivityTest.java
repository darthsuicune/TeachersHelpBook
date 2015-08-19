package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.Model;
import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.Holiday;
import com.dlgdev.teachers.helpbook.models.StudentGroup;
import com.dlgdev.teachers.helpbook.models.Subject;
import com.dlgdev.teachers.helpbook.views.ModelInfoActivity;
import com.dlgdev.teachers.helpbook.views.events.activities.EventInfoActivity;
import com.dlgdev.teachers.helpbook.views.holidays.activities.HolidayInfoActivity;
import com.dlgdev.teachers.helpbook.views.students.activities.StudentGroupInfoActivity;
import com.dlgdev.teachers.helpbook.views.subjects.activities.SubjectInfoActivity;

import org.hamcrest.Matcher;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasPackageName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static org.hamcrest.Matchers.not;
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
				hasExtra(ModelInfoActivity.KEY_ID, model.getId()),
				toPackage(PACKAGE_NAME),
				hasComponent(
						allOf(hasPackageName(PACKAGE_NAME),
								hasClassName(targetClass.getName()))));
	}

	@Test public void testOnNewSubjectRequested() throws Exception {
		activity.onNewSubjectRequested();
		intended(intentFor(SubjectInfoActivity.class));
	}

	private Matcher<Intent> intentFor(Class<? extends ModelInfoActivity> target) {
		return allOf(not(IntentMatchers.hasExtraWithKey(ModelInfoActivity.KEY_ID)),
				toPackage(PACKAGE_NAME),
				hasComponent(
						allOf(hasPackageName(PACKAGE_NAME),
								hasClassName(target.getName()))));
	}

	@Test public void testOnSubjectSelected() throws Exception {
		Subject subject = subject();
		activity.onSubjectSelected(subject);
		intended(intentFor(subject, SubjectInfoActivity.class));
	}

	private Subject subject() {
		Subject subject = new Subject();
		subject.save();
		course.addSubject(subject);
		return subject;
	}

	@Test public void testOnNewBankHolidayRequested() throws Exception {
		activity.onNewBankHolidayRequested();
		intended(intentFor(HolidayInfoActivity.class));
	}

	@Test public void testOnBankHolidaySelected() throws Exception {
		Holiday holiday = bankHoliday();
		activity.onBankHolidaySelected(holiday);
		intended(intentFor(holiday, HolidayInfoActivity.class));
	}

	private Holiday bankHoliday() {
		course.addBankHoliday(DateTime.now(), "Holiday name");
		return course.holidays().get(0);
	}

	@Test public void testOnNewGroupRequested() throws Exception {
		activity.onNewGroupRequested();
		intended(intentFor(StudentGroupInfoActivity.class));
	}

	@Test public void testOnGroupSelected() throws Exception {
		StudentGroup group = studentGroup();
		activity.onGroupSelected(group);
		intended(intentFor(group, StudentGroupInfoActivity.class));
	}

	private StudentGroup studentGroup() {
		StudentGroup group = new StudentGroup();
		group.save();
		return group;
	}

	@Test public void testOnNewEventRequested() throws Exception {
		activity.onNewEventRequested();
		intended(intentFor(EventInfoActivity.class));
	}

	@Test public void testOnEventSelected() throws Exception {
		Event event = event();
		activity.onEventSelected(event);
		intended(intentFor(event, EventInfoActivity.class));
	}

	private Event event() {
		Event event = new Event();
		event.save();
		return event;
	}
}

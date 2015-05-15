package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.factories.EventsFactory;
import com.dlgdev.teachers.helpbook.models.factories.SubjectsFactory;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseAdministrationActivity;

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
public class CourseAdministrationFragmentTest {
	static final String EVENT_TITLE = "event_title";
	static final String EVENT_DESC = "desc";
	static final String SUBJECT_TITLE = "subject_title";
	public static final String COURSE_TITLE = "Curse";
	public static final String COURSE_DESC = "This is such a curse";
	public static final String HOLIDAY_TITLE = "Something";

	CourseAdministrationFragment fragment;
	CourseAdministrationActivity activity;
	Course course;
	SubjectsFactory subjectsFactory;
	EventsFactory eventsFactory;

	@Rule public ActivityTestRule<CourseAdministrationActivity> rule =
			new ActivityTestRule<>(CourseAdministrationActivity.class, true, false);

	@Before public void setUp() throws Exception {
		subjectsFactory = new SubjectsFactory();
		eventsFactory = new EventsFactory();
		createStuffForTheCourse();
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testAfterLoadActivityLoadsTheCorrectCourse() throws Exception {
		launchActivity();
		assertEquals(course.getId(), fragment.course.getId());
	}

	private void launchActivity() {
		Intent intent = new Intent();
		intent.putExtra(CourseAdministrationActivity.KEY_COURSE, course.getId());
		activity = rule.launchActivity(intent);
		fragment = (CourseAdministrationFragment) activity.getSupportFragmentManager()
				.findFragmentById(R.id.course_administration_fragment);
	}

	@Test public void afterLaunchTheAvailableInformationIsDisplayed() throws Exception {
		launchActivity();
		onView(withText(COURSE_TITLE)).check(matches(isDisplayed()));
		onView(withText(COURSE_DESC)).check(matches(isDisplayed()));
		onView(withText(EVENT_TITLE)).check(matches(isDisplayed()));
		onView(withText(EVENT_DESC)).check(matches(isDisplayed()));
		onView(withText(SUBJECT_TITLE)).check(matches(isDisplayed()));
		onView(withText(HOLIDAY_TITLE)).check(matches(isDisplayed()));
	}

	private void createStuffForTheCourse() {
		course = new Course();
		course.start = DateTime.now().minusWeeks(5);
		course.end = DateTime.now().plusWeeks(5);
		course.addSubject(subjectsFactory.createAndSave(SUBJECT_TITLE));
		course.addBankHoliday(DateTime.now(), HOLIDAY_TITLE);
		course.addEvent(eventsFactory.createAndSave(EVENT_TITLE, EVENT_DESC));
		course.title = COURSE_TITLE;
		course.description = COURSE_DESC;
		course.save();
	}
}
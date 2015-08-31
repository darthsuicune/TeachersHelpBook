package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.Subject;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CourseOverviewDrawerFragment.OnOverviewDrawerListener;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class CourseOverviewDrawerFragmentTest {

	private static final String COURSE_TITLE = "some random title";
	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class, true, false);
	CourseOverviewDrawerFragment fragment;
	CourseOverviewActivity activity;
	Course course;
	OnOverviewDrawerListener listener;

	@Before public void setUp() throws Throwable {
		getFragment();
	}

	private void getFragment() throws Throwable {
		course = new Course();
		course.title = COURSE_TITLE;
		course.save();
		Intent intent = new Intent();
		intent.putExtra(CourseOverviewActivity.KEY_MODEL_ID, course.getId());
		activity = rule.launchActivity(intent);
		fragment = (CourseOverviewDrawerFragment) activity.getSupportFragmentManager()
				.findFragmentById(R.id.course_overview_navigation_drawer);
		listener = Mockito.mock(OnOverviewDrawerListener.class);
		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				fragment.openDrawer();
			}
		});
		//Required to make the app wait until the drawer is opened. Also convenient to avoid
		// attachment race condition for the listener
		Thread.sleep(500);
		fragment.listener = listener;
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void displaysSubjectTagAndNumberOfAvailableElements() throws Throwable {
		course.addSubject(new Subject());
		loadCourseData();
		onView(withText(activity.getString(R.string.subjects_marker, 1)))
				.check(matches(isDisplayed()));
	}

	private void loadCourseData() throws Throwable {
		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				fragment.updateCourse(course);
			}
		});
	}

	@Test public void displaysStudentGroupsTagAndNumberOfAvailableElements() throws Throwable {
		loadCourseData();
		onView(withText(activity.getString(R.string.student_groups_marker, 0)))
				.check(matches(isDisplayed()));
	}

	@Test public void displaysHolidaysTagAndNumberOfAvailableElements() throws Throwable {
		course.addBankHoliday(DateTime.now(), "asdf");
		loadCourseData();
		onView(withText(activity.getString(R.string.holidays_marker, 1)))
				.check(matches(isDisplayed()));
	}

	@Test public void displaysEventsTagAndNumberOfAvailableElements() throws Throwable {
		course.addEvent(new Event());
		loadCourseData();
		onView(withText(activity.getString(R.string.events_marker, 1)))
				.check(matches(isDisplayed()));
	}

	@Test public void clickOnSubjectsCallsTheCallbackForTheSubjects() throws Exception {
		onView(withId(R.id.course_overview_drawer_subjects)).perform(click());
		verify(listener).onSubjectsRequested();
	}

	@Test public void clickOnStudentGroupsCallsTheCallbackForTheSubjects() throws Exception {
		onView(withId(R.id.course_overview_drawer_student_groups)).perform(click());
		verify(listener).onStudentGroupsRequested();
	}

	@Test public void clickOnEventsCallsTheCallbackForTheSubjects() throws Exception {
		onView(withId(R.id.course_overview_drawer_events)).perform(click());
		verify(listener).onEventsRequested();
	}

	@Test public void clickOnHolidaysCallsTheCallbackForTheSubjects() throws Exception {
		onView(withId(R.id.course_overview_drawer_holidays)).perform(click());
		verify(listener).onHolidaysRequested();
	}
	
	@Test public void clickOnHeaderCallsTheCallbackForTheCourseInfo() throws Exception {
		onView(withId(R.id.course_overview_drawer_header)).perform(click());
		verify(listener).onCourseInfoRequested();
	}

	@Test public void headerDisplaysTheCourseTitle() throws Exception {
		String title = activity.getString(R.string.course_header, course.title);
		onView(withId(R.id.course_overview_drawer_header)).check(matches(withText(title)));
	}
}
package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.factories.EventsFactory;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CourseInfoFragment.CoursePanelListener;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dlgdev.teachers.helpbook.utils.Dates.formatDate;
import static com.dlgdev.views.MoreViewMatchers.hasText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class CourseInfoFragmentTest {
	Course course;
	EventsFactory provider;
	CourseInfoFragment fragment;

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class, true, false);

	@Before public void setUp() throws Exception {
		course = new Course(DateTime.now(), DateTime.now());
		course.save();
		provider = new EventsFactory();
		Intent intent = new Intent();
		intent.putExtra(CourseOverviewActivity.KEY_MODEL_ID, course.id);
		rule.launchActivity(intent);
	}

	@After public void teardown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testUpdateDateDoesntModifyItsMainDate() throws Exception {
		whenWeClickOnTheNextWeekPanel();
		theCurrentDateIsntModified();
	}

	private void whenWeClickOnTheNextWeekPanel() {
		onView(withId(R.id.course_weekly_next)).perform(click());
	}

	private void theCurrentDateIsntModified() {
		onView(withId(R.id.current_date)).check(matches(withText(formatDate(DateTime.now()))));
	}

	@Test public void testUpdateDateModifiesCurrentlyViewing() throws Throwable {
		whenWeClickOnTheNextWeekPanel();
		theCurrentReferenceWeekIsUpdatedTo(
				Dates.formatDateRange(startOfNextWeek(), endOfNextWeek()));
	}

	private void theCurrentReferenceWeekIsUpdatedTo(String s) {
		onView(withId(R.id.reference_week)).check(matches(withText(s)));
	}

	private DateTime startOfNextWeek() {
		return Dates.startOfWeek(DateTime.now(), DateTimeConstants.MONDAY).plusWeeks(1);
	}

	private DateTime endOfNextWeek() {
		return Dates.endOfWeek(DateTime.now(), DateTimeConstants.MONDAY).plusWeeks(1);
	}

	@Test public void testUpdateEventListUpdatesTheCounter() throws Throwable {
		whenWeCreateAnEventListWithOneEvent();
		theEventCounterIsSetTo(1);
	}

	private void whenWeCreateAnEventListWithOneEvent() throws Throwable {
		final List<Event> list = new ArrayList<>();
		list.add(provider.createEmpty());

		final CourseInfoFragment fragment =
				(CourseInfoFragment) rule.getActivity().getSupportFragmentManager()
						.findFragmentById(R.id.course_info_panel);
		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				fragment.eventList(list);
			}
		});
	}

	private void theEventCounterIsSetTo(int count) {
		onView(withId(R.id.event_counter))
				.check(matches(hasText(Integer.toString(count) + " events")));
	}

	@Test public void clickingOnThePanelCallsTheListener() throws Exception {
		loadFragment();
		CoursePanelListener listener = mock(CoursePanelListener.class);
		fragment.listener = listener;
		onView(withId(R.id.course_info_panel)).perform(click());
		verify(listener).onPanelTapped(course);
	}

	private void loadFragment() {
		fragment = (CourseInfoFragment) rule.getActivity().getSupportFragmentManager()
				.findFragmentById(R.id.course_info_panel);
	}
}
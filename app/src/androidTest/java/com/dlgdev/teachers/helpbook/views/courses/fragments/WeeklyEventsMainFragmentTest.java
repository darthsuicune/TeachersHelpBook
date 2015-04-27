package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class WeeklyEventsMainFragmentTest {
	CourseOverviewActivity activity;
	WeeklyEventsMainFragment fragment;
	DateTime currentDate;

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class);

	@Before public void setUp() throws Exception {
		activity = rule.getActivity();
		fragment = (WeeklyEventsMainFragment) activity.getSupportFragmentManager()
				.findFragmentById(R.id.course_weekly_main_fragment);
		currentDate = new DateTime(2015, 3, 5, 0, 0);
		activity.onNewDaySelected(currentDate);
	}

	@Test public void testClickingNextWeekFragmentViewUpdatesTheMainFragmentsDate()
			throws Exception {
		whenWeClickOnThePanel(R.id.course_weekly_next);
		theMainFragmentIsUpdatedWithTheNewDate(currentDate.plusWeeks(1));
	}

	private void whenWeClickOnThePanel(int panel) {
		onView(withId(panel)).perform(click());
	}

	private void theMainFragmentIsUpdatedWithTheNewDate(DateTime date) {
		assertEquals(fragment.referenceDate, date);
	}

	@Test public void testOnNewEventRequestedOpensADialogToCreateAnEvent() throws Exception {
		whenWeGetANewEventRequest();
		aNewEventDialogIsOpened();
	}

	private void whenWeGetANewEventRequest() {
		fragment.onNewEventRequested(currentDate.plusHours(1));
	}

	private void aNewEventDialogIsOpened() {
		onView(withId(R.id.new_event_dialog)).check(matches(isDisplayed()));
	}
}
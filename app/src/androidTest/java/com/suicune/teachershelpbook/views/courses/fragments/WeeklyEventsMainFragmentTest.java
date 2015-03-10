package com.suicune.teachershelpbook.views.courses.fragments;

import android.test.ActivityInstrumentationTestCase2;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.views.courses.activities.CourseOverviewActivity;

import org.joda.time.DateTime;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class WeeklyEventsMainFragmentTest
		extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	CourseOverviewActivity activity;
	WeeklyEventsFragment fragment;
	DateTime currentDate;

	public WeeklyEventsMainFragmentTest() {
		super(CourseOverviewActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		fragment = (WeeklyEventsMainFragment) activity.getSupportFragmentManager()
				.findFragmentById(R.id.course_weekly_main_fragment);
		currentDate = new DateTime(2015, 3, 5, 0, 0);
		try {
			runTestOnUiThread(new Runnable() {
				@Override public void run() {
					activity.onNewDaySelected(currentDate);
				}
			});
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	public void testClickingNextWeekFragmentViewUpdatesTheMainFragmentsDate() throws Exception {
		whenWeClickOnThePanel(R.id.course_weekly_next);
		theMainFragmentIsUpdatedWithTheNewDate(currentDate.plusWeeks(1));
	}

	private void whenWeClickOnThePanel(int panel) {
		onView(withId(panel)).perform(click());
	}

	private void theMainFragmentIsUpdatedWithTheNewDate(DateTime date) {
		assertEquals(fragment.referenceDate, date);
	}

	public void testAfterLaunchShowsTheWeeklyEventsAsListOnThePhone() {
		withASetOfEvents();
		weGetAListWithTheExpectedEvents();
	}

	private void withASetOfEvents() {

	}

	private void weGetAListWithTheExpectedEvents() {

	}

	public void testAfterLaunchHighlightsTheCurrentDayInTheList() {
		//After launching the activity, done in setUp with getActivity();
		weGetAListWithTheExpectedEventsWithTodayHighlighted();
	}

	private void weGetAListWithTheExpectedEventsWithTodayHighlighted() {

	}
}
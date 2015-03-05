package com.suicune.teachershelpbook.activities;

import android.test.ActivityInstrumentationTestCase2;

import com.suicune.teachershelpbook.R;

import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class CourseOverviewActivityTest
		extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	public static final int WEEK_IN_MILLIS = 1000*60*60*24*7;
	CourseOverviewActivity activity;

	public CourseOverviewActivityTest() {
		super(CourseOverviewActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
	}

	public void testPreConditionsMainFragmentIsntNull() throws Exception {
		assertNotNull(activity.mainViewFragment);
	}

	public void testPreConditionsPreviewsArentNull() throws Exception {
		assertNotNull(activity.nextWeekFragment);
		assertNotNull(activity.previousWeekFragment);
		assertNotNull(activity.secondNextWeekFragment);
	}

	public void testPreConditionsPanelIsntNull() throws Exception {
		assertNotNull(activity.coursePanelFragment);
	}

	public void testClickingNextWeekFragmentViewShwosNextWeekInMain() throws Exception {
		Date nextWeek = new Date(activity.currentDate.getTime() + WEEK_IN_MILLIS);
		onView(withId(R.id.course_weekly_next))
				.perform(click());

		onView(withId(R.id.weekly_events_text))
				.check(matches(withText(nextWeek.toString())));
	}

	public void testClickingSecondNextWeekFragmentViewShwosNextWeekInMain() throws Exception {
		Date nextWeek = new Date(activity.currentDate.getTime() + 2*WEEK_IN_MILLIS);
		onView(withId(R.id.course_weekly_second_next))
				.perform(click());

		onView(withId(R.id.weekly_events_text))
				.check(matches(withText(nextWeek.toString())));
	}

	public void testClickingPreviousWeekFragmentViewShwosNextWeekInMain() throws Exception {
		Date nextWeek = new Date(activity.currentDate.getTime() - WEEK_IN_MILLIS);
		onView(withId(R.id.course_weekly_previous))
				.perform(click());

		onView(withId(R.id.weekly_events_text))
				.check(matches(withText(nextWeek.toString())));
	}
}
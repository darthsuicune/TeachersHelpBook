package com.suicune.teachershelpbook.views.courses.activities;

import android.test.ActivityInstrumentationTestCase2;

import com.suicune.teachershelpbook.views.courses.fragments.CoursePanelFragment;
import com.suicune.teachershelpbook.views.courses.fragments.WeeklyEventsFragment;

import org.joda.time.DateTime;

public class CourseOverviewActivityTest
		extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	CourseOverviewActivity activity;

	public CourseOverviewActivityTest() {
		super(CourseOverviewActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		activity.currentDate = new DateTime(2015, 3, 6, 0, 0);
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

	public void testPreConditionsImplementsWeeklyPreviewListener() throws Exception {
		assertTrue(activity instanceof WeeklyEventsFragment.WeeklyPreviewListener);
	}

	public void testPreConditionsImplementsWeeklyEventsListener() throws Exception {
		assertTrue(activity instanceof WeeklyEventsFragment.WeeklyEventsListener);
	}

	public void testPreConditionsImplementsCoursePanelListener() throws Exception {
		assertTrue(activity instanceof CoursePanelFragment.CoursePanelListener);
	}
}
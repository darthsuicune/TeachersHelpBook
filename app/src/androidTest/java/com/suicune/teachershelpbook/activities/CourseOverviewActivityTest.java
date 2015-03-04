package com.suicune.teachershelpbook.activities;

import android.test.ActivityInstrumentationTestCase2;

public class CourseOverviewActivityTest
		extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	CourseOverviewActivity activity;

	public CourseOverviewActivityTest() {
		super(CourseOverviewActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
	}

	public void testOnNewWeekSelectedTheFragmentsGetReplaced() throws Exception {

	}
}
package com.suicune.teachershelpbook.views.events;

import android.test.ActivityInstrumentationTestCase2;

import com.suicune.teachershelpbook.views.courses.activities.CourseOverviewActivity;

public class NewEventDialogTest extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	//NewEventDialog dialog;
	CourseOverviewActivity activity;

	public NewEventDialogTest() {
		super(CourseOverviewActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
	}

	public void testSetup() throws Exception {

	}
}
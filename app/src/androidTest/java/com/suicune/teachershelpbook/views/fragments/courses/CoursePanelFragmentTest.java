package com.suicune.teachershelpbook.views.fragments.courses;

import android.test.ActivityInstrumentationTestCase2;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.activities.CourseOverviewActivity;

public class CoursePanelFragmentTest
		extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	CourseOverviewActivity activity;
	CoursePanelFragment fragment;

	public CoursePanelFragmentTest() {
		super(CourseOverviewActivity.class);
	}

	@Override protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		fragment = (CoursePanelFragment) activity.getSupportFragmentManager()
				.findFragmentById(R.id.course_overview_panel);
	}

	public void testUpdateDate() throws Exception {

	}
}
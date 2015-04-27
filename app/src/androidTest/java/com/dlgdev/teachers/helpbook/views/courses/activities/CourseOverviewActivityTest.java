package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.views.courses.fragments.CoursePanelFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsFragment;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CourseOverviewActivityTest {
	CourseOverviewActivity activity;

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class);

	@Before public void setUp() throws Exception {
		activity = rule.getActivity();
		activity.currentDate = new DateTime(2015, 3, 6, 0, 0);
	}

	@Test public void testPreConditionsMainFragmentIsntNull() throws Exception {
		assertNotNull(activity.mainViewFragment);
	}

	@Test public void testPreConditionsPreviewsArentNull() throws Exception {
		assertNotNull(activity.nextWeekFragment);
		assertNotNull(activity.previousWeekFragment);
		assertNotNull(activity.secondNextWeekFragment);
	}

	@Test public void testPreConditionsPanelIsntNull() throws Exception {
		assertNotNull(activity.coursePanelFragment);
	}

	@Test public void testPreConditionsImplementsWeeklyPreviewListener() throws Exception {
		assertTrue(activity instanceof WeeklyEventsFragment.WeeklyPreviewListener);
	}

	@Test public void testPreConditionsImplementsWeeklyEventsListener() throws Exception {
		assertTrue(activity instanceof WeeklyEventsFragment.WeeklyEventsListener);
	}

	@Test public void testPreConditionsImplementsCoursePanelListener() throws Exception {
		assertTrue(activity instanceof CoursePanelFragment.CoursePanelListener);
	}
}
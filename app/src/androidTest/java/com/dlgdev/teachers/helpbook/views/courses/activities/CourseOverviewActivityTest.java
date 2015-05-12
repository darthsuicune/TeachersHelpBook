package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.views.courses.fragments.CoursePanelFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsPreviewFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CourseOverviewActivityTest {
	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class);

	@Test public void testPreConditionsMainFragmentIsntNull() throws Exception {
		assertNotNull(rule.getActivity().mainViewFragment);
	}

	@Test public void testPreConditionsPreviewsArentNull() throws Exception {
		CourseOverviewActivity activity = rule.getActivity();
		assertNotNull(activity.nextWeekFragment);
		assertNotNull(activity.previousWeekFragment);
		assertNotNull(activity.secondNextWeekFragment);
	}

	@Test public void testPreConditionsPanelIsntNull() throws Exception {
		assertNotNull(rule.getActivity().coursePanelFragment);
	}

	@Test public void testPreConditionsImplementsWeeklyPreviewListener() throws Exception {
		assertTrue(rule.getActivity() instanceof WeeklyEventsPreviewFragment.WeeklyPreviewListener);
	}

	@Test public void testPreConditionsImplementsWeeklyEventsListener() throws Exception {
		assertTrue(rule.getActivity() instanceof WeeklyEventsFragment.WeeklyEventsListener);
	}

	@Test public void testPreConditionsImplementsCoursePanelListener() throws Exception {
		assertTrue(rule.getActivity() instanceof CoursePanelFragment.CoursePanelListener);
	}
}
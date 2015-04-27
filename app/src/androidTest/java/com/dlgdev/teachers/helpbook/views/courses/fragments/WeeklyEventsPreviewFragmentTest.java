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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class WeeklyEventsPreviewFragmentTest {
	CourseOverviewActivity activity;

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class);

	@Before public void setUp() throws Exception {
		activity = rule.getActivity();
		activity.onNewDaySelected(new DateTime(2015, 3, 5, 0, 0));
	}

	@Test public void testClickingNextWeekFragmentViewShowsFollowingWeekInOwnPanel()
			throws Exception {
		whenWeClickOnThePanel(R.id.course_weekly_next);
		weFindAPanelWithTextBeing("16/03/2015 - 22/03/2015"); //2 weeks from current
	}

	private void whenWeClickOnThePanel(int panel) {
		onView(withId(panel)).perform(click());
	}

	private void weFindAPanelWithTextBeing(String s) {
		onView(withText(s)).check(matches(withId(R.id.weekly_events_preview_text)));
	}

	@Test public void testClickingSecondNextWeekFragmentViewShowsSecondNextWeekInMain()
			throws Exception {
		whenWeClickOnThePanel(R.id.course_weekly_second_next);
		weFindAPanelWithTextBeing("30/03/2015 - 5/04/2015"); // 4 weeks from current
	}

	@Test public void testClickingPreviousWeekFragmentViewShowsPreviousWeekInMain()
			throws Exception {
		whenWeClickOnThePanel(R.id.course_weekly_previous);
		weFindAPanelWithTextBeing("16/02/2015 - 22/02/2015"); //2 weeks before current
	}
}
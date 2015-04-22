package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.test.ActivityInstrumentationTestCase2;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;

import org.joda.time.DateTime;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class WeeklyEventsPreviewFragmentTest
        extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
    CourseOverviewActivity activity;

    public WeeklyEventsPreviewFragmentTest() {
        super(CourseOverviewActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
		try {
			runTestOnUiThread(new Runnable() {
				@Override public void run() {
					activity.onNewDaySelected(new DateTime(2015, 3, 5, 0, 0));
				}
			});
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	public void testClickingNextWeekFragmentViewShowsFollowingWeekInOwnPanel() throws Exception {
		whenWeClickOnThePanel(R.id.course_weekly_next);
		weFindAPanelWithTextBeing("16/03/2015 - 22/03/2015"); //2 weeks from current
	}

	private void whenWeClickOnThePanel(int panel) {
		onView(withId(panel)).perform(click());
	}

	private void weFindAPanelWithTextBeing(String s) {
		onView(withText(s)).check(matches(withId(R.id.weekly_events_preview_text)));
	}

	public void testClickingSecondNextWeekFragmentViewShowsSecondNextWeekInMain() throws Exception {
		whenWeClickOnThePanel(R.id.course_weekly_second_next);
		weFindAPanelWithTextBeing("30/03/2015 - 5/04/2015"); // 4 weeks from current
    }

    public void testClickingPreviousWeekFragmentViewShowsPreviousWeekInMain() throws Exception {
        whenWeClickOnThePanel(R.id.course_weekly_previous);
		weFindAPanelWithTextBeing("16/02/2015 - 22/02/2015"); //2 weeks before current
    }
}
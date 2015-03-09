package com.suicune.teachershelpbook.views.courses.fragments;

import android.test.ActivityInstrumentationTestCase2;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.utils.Dates;
import com.suicune.teachershelpbook.views.courses.activities.CourseOverviewActivity;

import org.joda.time.DateTime;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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

	public void testUpdateDateDoesntModifyItsMainDate() throws Exception {
		onView(withId(R.id.course_weekly_next)).perform(click());

		onView(withId(R.id.current_date))
				.check(matches(withText(Dates.formatDate(new DateTime()))));
	}

	public void testUpdateDateModifiesCurrentlyViewing() throws Exception {
		onView(withId(R.id.course_weekly_next)).perform(click());

		onView(withId(R.id.reference_week)).check(matches(withText("9/3/2015 - 15/3/2015")));
	}
}
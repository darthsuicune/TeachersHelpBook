package com.suicune.teachershelpbook.views.fragments.courses;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.activities.CourseOverviewActivity;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class WeeklyEventsFragmentTest
		extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	CourseOverviewActivity activity;
	WeeklyEventsFragment fragment;

	public WeeklyEventsFragmentTest() {
		super(CourseOverviewActivity.class);
	}

	@Override public void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		fragment = (WeeklyEventsFragment) activity.getSupportFragmentManager()
				.findFragmentById(R.id.course_weekly_main_fragment);
	}

	public void testClickingNextWeekFragmentViewShowsNextWeekInMain() throws Exception {
		onView(withId(R.id.course_weekly_next)).perform(click());

		onView(withId(R.id.weekly_events_text)).check(matches(withText("9/3/2015 - 15/3/2015")));
	}

	public void testClickingSecondNextWeekFragmentViewShowsSecondNextWeekInMain() throws Exception {
		onView(withId(R.id.course_weekly_second_next)).perform(click());

		onView(withId(R.id.weekly_events_text)).check(matches(withText("16/3/2015 - 22/3/2015")));
	}

	public void testClickingPreviousWeekFragmentViewShowsPreviousWeekInMain() throws Exception {
		onView(withId(R.id.course_weekly_previous)).perform(click());

		onView(withId(R.id.weekly_events_text)).check(matches(withText("23/2/2015 - 1/3/2015")));
	}

	private Matcher<? super View> withText(String s) {
		return new DateMatcher(s);
	}

	public static class DateMatcher<TextView> extends BaseMatcher<TextView> {
		String string;
		public DateMatcher(String string) {
			this.string = string;
		}

		@Override public boolean matches(Object o) {
			return this.string.equals(o.toString());
		}

		@Override public void describeTo(Description description) {

		}
	}
}
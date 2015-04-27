package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;

import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class UIWeeklyEventsMainFragmentTest {
	WeeklyEventsMainFragment fragment;

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class);

	@Test public void testOnNewEventRequestedOpensADialogToCreateAnEvent() throws Exception {
		whenWeGetANewEventRequest();
		aNewEventDialogIsOpened();
	}

	private void whenWeGetANewEventRequest() {
		fragment = (WeeklyEventsMainFragment) rule.getActivity().getSupportFragmentManager()
				.findFragmentById(R.id.course_weekly_main_fragment);
		fragment.onNewEventRequested(DateTime.now());
	}

	private void aNewEventDialogIsOpened() {
		onView(withId(R.id.new_event_dialog)).check(matches(isDisplayed()));
	}
}

package com.suicune.teachershelpbook.views.courses.fragments;

import android.test.ActivityInstrumentationTestCase2;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.EventsProvider;
import com.suicune.teachershelpbook.utils.Dates;
import com.suicune.teachershelpbook.views.courses.activities.CourseOverviewActivity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.suicune.teachershelpbook.views.MoreViewMatchers.hasText;

public class CoursePanelFragmentTest
		extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	CourseOverviewActivity activity;
	CoursePanelFragment fragment;
	DateTime date;
	EventsProvider provider;

	public CoursePanelFragmentTest() {
		super(CourseOverviewActivity.class);
	}

	@Override protected void setUp() throws Exception {
		super.setUp();
		provider = new EventsProvider();
		activity = getActivity();
		fragment = (CoursePanelFragment) activity.getFragmentManager()
				.findFragmentById(R.id.course_overview_panel);
		date = fragment.currentDate;
	}

	public void testUpdateDateDoesntModifyItsMainDate() throws Exception {
		whenWeClickOnTheNextWeekPanel();
		theCurrentDateIsntModified();
	}

	private void whenWeClickOnTheNextWeekPanel() {
		onView(withId(R.id.course_weekly_next)).perform(click());
	}

	private void theCurrentDateIsntModified() {
		onView(withId(R.id.current_date)).check(matches(withText(Dates.formatDate(date))));
	}

	public void testUpdateDateModifiesCurrentlyViewing() throws Throwable {
		withTheCurrentlySelectedDateSetTo(new DateTime(2015, 3, 5, 0, 0));
		whenWeClickOnTheNextWeekPanel();
		theCurrentReferenceWeekIsUpdatedTo("9/3/2015 - 15/3/2015");
	}

	private void withTheCurrentlySelectedDateSetTo(final DateTime dateTime) throws Throwable {
			runTestOnUiThread(new Runnable() {
				@Override public void run() {
					activity.onNewDaySelected(dateTime);
				}
			});
	}

	private void theCurrentReferenceWeekIsUpdatedTo(String s) {
		onView(withId(R.id.reference_week)).check(matches(withText(s)));
	}

	public void testUpdateEventListUpdatesTheCounter() throws Throwable {
		whenWeCreateAnEventListWithOneEvent();
		theEventCounterIsSetTo(1);
	}

	private void whenWeCreateAnEventListWithOneEvent() throws Throwable {
		final List<Event> list = new ArrayList<>();
		list.add(provider.createEmpty());
		runTestOnUiThread(new Runnable() {
			@Override public void run() {
				fragment.eventList(provider.listFromList(list));
			}
		});
	}

	private void theEventCounterIsSetTo(int count) {
		onView(withId(R.id.event_counter)).check(matches(hasText(Integer.toString(count))));
	}
}
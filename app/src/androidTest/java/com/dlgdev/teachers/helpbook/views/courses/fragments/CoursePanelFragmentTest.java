package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.events.Event;
import com.dlgdev.teachers.helpbook.models.events.EventsProvider;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dlgdev.teachers.helpbook.utils.Dates.formatDate;
import static com.dlgdev.teachers.helpbook.views.MoreViewMatchers.hasText;

@RunWith(AndroidJUnit4.class)
public class CoursePanelFragmentTest {
	EventsProvider provider;

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class);

	@Before public void setUp() throws Exception {
		provider = new EventsProvider();
	}

	@Test public void testUpdateDateDoesntModifyItsMainDate() throws Exception {
		whenWeClickOnTheNextWeekPanel();
		theCurrentDateIsntModified();
	}

	private void whenWeClickOnTheNextWeekPanel() {
		onView(withId(R.id.course_weekly_next)).perform(click());
	}

	private void theCurrentDateIsntModified() {
		onView(withId(R.id.current_date)).check(matches(withText(formatDate(DateTime.now()))));
	}

	@Test public void testUpdateDateModifiesCurrentlyViewing() throws Throwable {
		whenWeClickOnTheNextWeekPanel();
		theCurrentReferenceWeekIsUpdatedTo(
				Dates.formatDateRange(startOfNextWeek(), endOfNextWeek()));
	}

	private DateTime endOfNextWeek() {
		return Dates.endOfWeek(DateTime.now()).plusWeeks(1);
	}

	private DateTime startOfNextWeek() {
		return Dates.startOfWeek(DateTime.now()).plusWeeks(1);
	}

	private void theCurrentReferenceWeekIsUpdatedTo(String s) {
		onView(withId(R.id.reference_week)).check(matches(withText(s)));
	}

	@Test public void testUpdateEventListUpdatesTheCounter() throws Throwable {
		whenWeCreateAnEventListWithOneEvent();
		theEventCounterIsSetTo(1);
	}

	private void whenWeCreateAnEventListWithOneEvent() throws Throwable {
		final List<Event> list = new ArrayList<>();
		list.add(provider.createEmpty());

		final CoursePanelFragment fragment =
				(CoursePanelFragment) rule.getActivity().getSupportFragmentManager()
						.findFragmentById(R.id.course_overview_panel);
		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				fragment.eventList(provider.listFromList(list));
			}
		});
	}

	private void theEventCounterIsSetTo(int count) {
		onView(withId(R.id.event_counter))
				.check(matches(hasText(Integer.toString(count) + " events")));
	}
}
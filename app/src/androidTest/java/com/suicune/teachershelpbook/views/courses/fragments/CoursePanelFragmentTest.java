package com.suicune.teachershelpbook.views.courses.fragments;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.EventsProvider;
import com.suicune.teachershelpbook.utils.Dates;
import com.suicune.teachershelpbook.views.courses.activities.CourseOverviewActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class CoursePanelFragmentTest
		extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	CourseOverviewActivity activity;
	CoursePanelFragment fragment;
	DateTime date;

	public CoursePanelFragmentTest() {
		super(CourseOverviewActivity.class);
	}

	@Override protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		fragment = (CoursePanelFragment) activity.getSupportFragmentManager()
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

	public void testUpdateDateModifiesCurrentlyViewing() throws Exception {
		withTheCurrentlySelectedDateSetTo(new DateTime(2015, 3, 5, 0, 0));
		whenWeClickOnTheNextWeekPanel();
		theCurrentReferenceWeekIsUpdatedTo("9/3/2015 - 15/3/2015");
	}

	private void withTheCurrentlySelectedDateSetTo(final DateTime dateTime) {
		try {
			runTestOnUiThread(new Runnable() {
				@Override public void run() {
					activity.onNewDaySelected(dateTime);
				}
			});
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	private void theCurrentReferenceWeekIsUpdatedTo(String s) {
		onView(withId(R.id.reference_week)).check(matches(withText(s)));
	}

	public void testUpdateEventListUpdatesTheCounter() throws Exception {
		whenWeCreateAnEventListWithOneEvent();
		theEventCounterIsSetTo(1);
	}

	private void theEventCounterIsSetTo(int count) {
		onView(withId(R.id.event_counter)).check(matches(hasText(Integer.toString(count))));
	}

	private Matcher<View> hasText(final String s) {
		return new TypeSafeMatcher<View>() {
			@Override public boolean matchesSafely(View view) {
				if(view == null) {
					return false;
				}
				TextView tv = (TextView) view;
				return tv.getText().toString().contains(s);
			}

			@Override public void describeTo(Description description) {
				description.appendText("It should include the text: ").appendValue(s);
			}
		};
	}

	private void whenWeCreateAnEventListWithOneEvent() {
		final EventsProvider provider = new EventsProvider();
		final List<Event> list = new ArrayList<>();
		list.add(provider.createEmpty());
		try {
			runTestOnUiThread(new Runnable() {
				@Override public void run() {
					fragment.eventList(provider.listFromList(list));
				}
			});
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}
}
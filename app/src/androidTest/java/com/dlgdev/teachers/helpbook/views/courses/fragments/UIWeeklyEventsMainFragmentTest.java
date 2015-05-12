package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.query.Delete;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.events.Event;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class UIWeeklyEventsMainFragmentTest {
	WeeklyEventsMainFragment fragment;
	String text = "someText";

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class);

	@After public void tearDown() throws Exception {
		new Delete().from(Event.class).execute();
	}

	@Test public void testOnNewEventRequestedOpensADialogToCreateAnEvent() throws Exception {
		whenWeRequestANewEvent();
		aNewEventDialogIsOpened();
	}

	private void whenWeRequestANewEvent() {
		getFragment();
		fragment.onNewEventRequested(DateTime.now());
	}

	public void getFragment() {
		fragment = (WeeklyEventsMainFragment) rule.getActivity().getSupportFragmentManager()
				.findFragmentById(R.id.course_weekly_main_fragment);
	}

	private void aNewEventDialogIsOpened() {
		onView(withId(R.id.new_event_dialog)).check(matches(isDisplayed()));
	}

	@Test public void testCreatingAnEventDisplaysItInTheList() throws Exception {
		whenWeRequestANewEvent();
		fillSomeDataAndCreateTheEvent();
		theEventDataIsDisplayedInTheProperDay();
	}

	private void fillSomeDataAndCreateTheEvent() throws Exception {
		onView(withId(R.id.create_event_dialog_title)).perform(typeText(text));
		Espresso.closeSoftKeyboard();
		Thread.sleep(1000);
		onView(withText(R.string.create_event)).perform(click());
	}

	private void theEventDataIsDisplayedInTheProperDay() {
		int parentId = fragment.dailyCards.get(DateTime.now().getDayOfWeek()).getId();
		onView(allOf(withId(R.id.event_entry_name), isDescendantOfA(withId(parentId))))
				.check(matches(withText(text)));
	}

	@Test public void clickingAnEventOnTheListPassesTheEventToTheListener() throws Exception {

	}
}

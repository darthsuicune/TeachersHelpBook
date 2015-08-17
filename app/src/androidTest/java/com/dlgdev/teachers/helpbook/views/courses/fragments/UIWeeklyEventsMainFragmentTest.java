package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.factories.EventsFactory;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsFragment.WeeklyEventsListener;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.After;
import org.junit.Before;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class UIWeeklyEventsMainFragmentTest {
	WeeklyEventsMainFragment fragment;
	WeeklyEventsListener listener = mock(WeeklyEventsListener.class);
	String text = "someText";
	DateTime date = DateTime.now().withDayOfWeek(DateTimeConstants.MONDAY);

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class);

	@Before public void setUp() throws Exception {
		DatabaseUtils.getDatabase(InstrumentationRegistry.getTargetContext());
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testOnNewEventRequestedOpensADialogToCreateAnEvent() throws Exception {
		whenWeRequestANewEvent();
		aNewEventDialogIsOpened();
	}

	private void whenWeRequestANewEvent() {
		getFragment();
		fragment.onNewEventRequested(date);
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
		int parentId = fragment.dailyCards.get(date.getDayOfWeek()).getId();
		onView(allOf(withId(R.id.event_entry_name), isDescendantOfA(withId(parentId))))
				.check(matches(withText(text)));
	}

	@Test public void clickingAnEventOnTheListPassesTheEventToTheListener() throws Exception {
		getFragment();
		fragment.eventsListener = listener;
		Event event = afterAddingAnEvent();
		int parentId = fragment.dailyCards.get(date.getDayOfWeek()).getId();
		onView(allOf(withId(R.id.event_entry_name), isDescendantOfA(withId(parentId))))
				.perform(click());
		verify(listener).onExistingEventSelected(event);
	}

	private Event afterAddingAnEvent() {
		Event event = new EventsFactory().createAndSaveAt(date, "sometext", "somedesc");
		event.save();
		return event;
	}
}

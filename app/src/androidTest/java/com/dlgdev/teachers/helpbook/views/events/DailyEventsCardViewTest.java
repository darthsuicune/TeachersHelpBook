package com.dlgdev.teachers.helpbook.views.events;


import android.test.ActivityInstrumentationTestCase2;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.model.events.Event;
import com.dlgdev.teachers.helpbook.model.events.EventList;
import com.dlgdev.teachers.helpbook.model.events.EventsProvider;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dlgdev.teachers.helpbook.views.MoreViewMatchers.backgroundIs;
import static com.dlgdev.teachers.helpbook.views.MoreViewMatchers.hasChildren;
import static com.dlgdev.teachers.helpbook.views.MoreViewMatchers.instance;
import static com.dlgdev.teachers.helpbook.views.events.DailyEventsCardView.NewEventsRequestedListener;
import static org.hamcrest.core.IsNot.not;
import static org.joda.time.DateTimeConstants.MONDAY;


public class DailyEventsCardViewTest
		extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	DailyEventsCardView card;
	MockListener listener;
	DateTime date;
	CourseOverviewActivity activity;
	EventList eventList;
	EventsProvider provider;

	public DailyEventsCardViewTest() {
		super(CourseOverviewActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		provider = new EventsProvider();
		activity = getActivity();

		card = (DailyEventsCardView) activity.findViewById(R.id.monday_card);
		listener = new MockListener();
		date = Dates.dateForDayOfWeek(MONDAY, new DateTime());
	}

	public void testSetupPreparesTheViewFieldsCorrectly() throws Throwable {
		whenWeSetupTheView(date);
		theFieldsAreCorrectlySetup();
	}

	private void whenWeSetupTheView(final DateTime date) throws Throwable {
			runTestOnUiThread(new Runnable() {
				@Override public void run() {
					card.setup(listener, date);
				}
			});
	}

	private void theFieldsAreCorrectlySetup() {
		assertNotNull(card.listener);
		assertNotNull(card.date);
		assertNotNull(card.dateView);
		assertNotNull(card.addNewView);
		assertNotNull(card.emptyEventListView);
		assertNotNull(card.eventListView);
		assertEquals(card.dateView.getText(), Dates.formatDate(date));
	}

	public void testSetupPreparesTheAddNewViewSoItCallsTheListener() throws Throwable {
		whenWeSetupTheView(date);
		andClickOnAddNew();
		theListenerGetsCalled();
	}

	private void andClickOnAddNew() throws Throwable {
		runTestOnUiThread(new Runnable() {
			@Override public void run() {
				card.addNewView.performClick();
			}
		});
	}

	private void theListenerGetsCalled() {
		assertTrue(listener.called);
	}

	public void testAfterLaunchDisplaysTheDateRelatedToTheCard() throws Exception {
		//After launching the activity, done in setUp with getActivity();
		theDateSubviewDisplaysTheProperText();
	}

	private void theDateSubviewDisplaysTheProperText() {
		onView(instance(card.dateView)).check(matches(withText(Dates.formatDate(card.date))));
	}

	public void testAfterLaunchHighlightsTheCurrentDayInTheList() throws Throwable {
		whenWeSetupTheView(new DateTime());
		weGetAListWithTheExpectedEventsWithTodayHighlighted();
	}

	private void weGetAListWithTheExpectedEventsWithTodayHighlighted() {
		onView(instance(card)).check(matches(backgroundIs(
				getActivity().getResources().getColor(R.color.material_deep_teal_200))));
	}

	public void testAfterLaunchAnotherDayIsntHighlighted() throws Exception {
		onView(instance(card)).check(matches(not(
				backgroundIs(getActivity().getResources().getColor(R.color.material_deep_teal_200)))));
	}

	public void testUpdateEventsWithoutEventsShowsAnEmptyListMessage() throws Throwable {
		whenWeSendAnUpdatedEventListWithXEvents(0);
		theUserCanSeeAnEmptyListMessage();
	}

	private void whenWeSendAnUpdatedEventListWithXEvents(int eventCount) throws Throwable {
		List<Event> events = new ArrayList<>();
		for (int i = 0; i < eventCount; i++) {
			events.add(provider.createEmpty(date.plusHours(i)));
		}
		eventList = provider.listFromList(events);
		runTestOnUiThread(new Runnable() {
			@Override public void run() {
				card.updateEvents(eventList);
			}
		});
	}

	private void theUserCanSeeAnEmptyListMessage() {
		onView(instance(card.emptyEventListView)).check(matches(isDisplayed()));
		onView(instance(card.eventListView)).check(matches(not(isDisplayed())));
	}

	public void testUpdateEventsWithEventsMakesTheMessageDisappear() throws Throwable {
		whenWeSendAnUpdatedEventListWithXEvents(2);
		theUserCannotSeeTheEmptyListMessage();
	}

	private void theUserCannotSeeTheEmptyListMessage() {
		onView(instance(card.emptyEventListView)).check(matches(not(isDisplayed())));
		onView(instance(card.eventListView)).check(matches(isDisplayed()));
	}

	public void testUpdateEventsWithEventsPopulatesTheEventList() throws Throwable {
		whenWeSendAnUpdatedEventListWithXEvents(2);
		theListIsPopulatedWithTheEventInfo(2);
	}

	private void theListIsPopulatedWithTheEventInfo(int count) {
		onView(instance(card.eventListView)).check(matches(hasChildren(count)));
	}

	public class MockListener implements NewEventsRequestedListener {
		boolean called = false;

		@Override public void onNewEventRequested(DateTime date) {
			called = true;
		}
	}
}
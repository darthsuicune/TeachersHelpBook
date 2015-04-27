package com.dlgdev.teachers.helpbook.views.events;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.UiThreadTest;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.model.events.Event;
import com.dlgdev.teachers.helpbook.model.events.EventList;
import com.dlgdev.teachers.helpbook.model.events.EventsProvider;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DailyEventsCardViewTest {
	DailyEventsCardView card;
	NewEventsRequestedListener listener;
	DateTime date;
	CourseOverviewActivity activity;
	EventList eventList;
	EventsProvider provider;

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class);

	@Before public void setUp() throws Exception {
		provider = new EventsProvider();
		activity = rule.getActivity();

		card = (DailyEventsCardView) activity.findViewById(R.id.monday_card);
		listener = mock(NewEventsRequestedListener.class);
		date = Dates.dateForDayOfWeek(MONDAY, new DateTime());
	}

	@UiThreadTest @Test public void testSetupPreparesTheViewFieldsCorrectly() throws Throwable {
		whenWeSetupTheView(date);
		theFieldsAreCorrectlySetup();
	}

	private void whenWeSetupTheView(final DateTime date) throws Throwable {
		card.setup(listener, date);
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

	@UiThreadTest @Test public void testSetupPreparesTheAddNewViewSoItCallsTheListener()
			throws Throwable {
		whenWeSetupTheView(date);
		andClickOnAddNew();
		theListenerGetsCalled();
	}

	private void andClickOnAddNew() throws Throwable {
		card.addNewView.performClick();
	}

	private void theListenerGetsCalled() {
		verify(listener).onNewEventRequested(argThat(matchesAnyDateTime()));
	}

	private Matcher<DateTime> matchesAnyDateTime() {
		return new BaseMatcher<DateTime>() {
			@Override public void describeTo(Description description) {

			}

			@Override public boolean matches(Object o) {
				return o instanceof DateTime;
			}
		};
	}

	@Test public void testAfterLaunchDisplaysTheDateRelatedToTheCard() throws Exception {
		//After launching the activity, done in setUp with getActivity();
		theDateSubviewDisplaysTheProperText();
	}

	private void theDateSubviewDisplaysTheProperText() {
		onView(instance(card.dateView)).check(matches(withText(Dates.formatDate(card.date))));
	}

	@Test public void testAfterLaunchHighlightsTheCurrentDayInTheList() throws Throwable {
		whenWeSetupTheView(new DateTime());
		weGetAListWithTheExpectedEventsWithTodayHighlighted();
	}

	private void weGetAListWithTheExpectedEventsWithTodayHighlighted() {
		onView(instance(card)).check(matches(
				backgroundIs(activity.getResources().getColor(R.color.material_deep_teal_200))));
	}

	@Test public void testAfterLaunchAnotherDayIsntHighlighted() throws Exception {
		onView(instance(card)).check(matches(not(backgroundIs(
				activity.getResources().getColor(R.color.material_deep_teal_200)))));
	}

	@UiThreadTest @Test public void testUpdateEventsWithoutEventsShowsAnEmptyListMessage()
			throws Throwable {
		whenWeSendAnUpdatedEventListWithXEvents(0);
		theUserCanSeeAnEmptyListMessage();
	}

	private void whenWeSendAnUpdatedEventListWithXEvents(int eventCount) throws Throwable {
		List<Event> events = new ArrayList<>();
		for (int i = 0; i < eventCount; i++) {
			events.add(provider.createEmpty(date.plusHours(i)));
		}
		eventList = provider.listFromList(events);
		card.updateEvents(eventList);
	}

	private void theUserCanSeeAnEmptyListMessage() {
		onView(instance(card.emptyEventListView)).check(matches(isDisplayed()));
		onView(instance(card.eventListView)).check(matches(not(isDisplayed())));
	}

	@Test public void testUpdateEventsWithEventsMakesTheMessageDisappear() throws Throwable {
		whenWeSendAnUpdatedEventListWithXEvents(2);
		theUserCannotSeeTheEmptyListMessage();
	}

	private void theUserCannotSeeTheEmptyListMessage() {
		onView(instance(card.emptyEventListView)).check(matches(not(isDisplayed())));
		onView(instance(card.eventListView)).check(matches(isDisplayed()));
	}

	@Test public void testUpdateEventsWithEventsPopulatesTheEventList() throws Throwable {
		whenWeSendAnUpdatedEventListWithXEvents(2);
		theListIsPopulatedWithTheEventInfo(2);
	}

	private void theListIsPopulatedWithTheEventInfo(int count) {
		onView(instance(card.eventListView)).check(matches(hasChildren(count)));
	}
}
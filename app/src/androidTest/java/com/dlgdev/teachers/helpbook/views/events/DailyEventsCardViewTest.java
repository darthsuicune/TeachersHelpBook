package com.dlgdev.teachers.helpbook.views.events;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;
import android.view.View;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.model.events.Event;
import com.dlgdev.teachers.helpbook.model.events.EventList;
import com.dlgdev.teachers.helpbook.model.events.EventsProvider;
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.dlgdev.teachers.helpbook.views.MoreViewMatchers.backgroundIs;
import static com.dlgdev.teachers.helpbook.views.events.DailyEventsCardView.NewEventsRequestedListener;
import static org.joda.time.DateTimeConstants.MONDAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DailyEventsCardViewTest {
	DailyEventsCardView card;
	NewEventsRequestedListener listener;
	DateTime date;
	EventList eventList;
	EventsProvider provider;

	@Before public void setUp() throws Exception {
		provider = new EventsProvider();
		LayoutInflater inflater = (LayoutInflater) InstrumentationRegistry.getTargetContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		card = (DailyEventsCardView) inflater.inflate(R.layout.daily_events_card, null);
		listener = mock(NewEventsRequestedListener.class);
		date = Dates.dateForDayOfWeek(MONDAY, DateTime.now());
		setupCard(date);
	}

	private void setupCard(DateTime date) {
		card.setup(listener, date);
	}


	@Test public void testSetupPreparesTheViewFieldsCorrectly() throws Exception {
		theFieldsAreCorrectlySetup();
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

	@Test public void testSetupPreparesTheAddNewViewSoItCallsTheListener() throws Exception {
		whenWeClickOnAddNew();
		theListenerGetsCalled();
	}

	private void whenWeClickOnAddNew() throws Exception {
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
		assertTrue(card.dateView.getText().toString().equals(Dates.formatDate(card.date)));
	}

	@Test public void testAfterLaunchHighlightsTheCurrentDayInTheList() throws Throwable {
		weGetAListWithTheExpectedEventsWithTodayHighlighted();
	}

	private void weGetAListWithTheExpectedEventsWithTodayHighlighted() {
		assertTrue(backgroundIs(InstrumentationRegistry.getTargetContext().getResources()
				.getColor(R.color.material_deep_teal_200)).matches(card));
	}

	@Test public void testAfterLaunchAnotherDayIsntHighlighted() throws Exception {
		setupCard(DateTime.now().plusDays(1));
		assertFalse(backgroundIs(InstrumentationRegistry.getTargetContext().getResources()
				.getColor(R.color.material_deep_teal_200)).matches(card));
	}

	@Test public void testUpdateEventsWithoutEventsShowsAnEmptyListMessage() throws Throwable {
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
		assertTrue(hasVisibility(card.emptyEventListView, View.VISIBLE));
		assertTrue(hasVisibility(card.eventListView, View.GONE));
	}

	private boolean hasVisibility(View v, int visibility) {
		return v.getVisibility() == visibility;
	}

	@Test public void testUpdateEventsWithEventsMakesTheMessageDisappear() throws Throwable {
		whenWeSendAnUpdatedEventListWithXEvents(2);
		theUserCannotSeeTheEmptyListMessage();
	}

	private void theUserCannotSeeTheEmptyListMessage() {
		assertTrue(hasVisibility(card.emptyEventListView, View.GONE));
		assertTrue(hasVisibility(card.eventListView, View.VISIBLE));
	}

	@Test public void testUpdateEventsWithEventsPopulatesTheEventList() throws Throwable {
		whenWeSendAnUpdatedEventListWithXEvents(2);
		theListIsPopulatedWithTheEventInfo(2);
	}

	private void theListIsPopulatedWithTheEventInfo(int count) {
		assertEquals(card.eventListView.getAdapter().getItemCount(), count);
	}
}
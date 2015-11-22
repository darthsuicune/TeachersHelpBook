package com.dlgdev.teachers.helpbook.views.events;

import android.content.Context;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;
import android.view.View;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.domain.models.Event;
import com.dlgdev.teachers.helpbook.domain.models.EventList;
import com.dlgdev.teachers.helpbook.domain.models.repositories.EventsRepository;
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.dlgdev.views.Matchers.matchesAnyDateTime;
import static com.dlgdev.views.MoreViewMatchers.backgroundIs;

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
	EventActionsListener listener;
	DateTime date;
	EventList eventList;
	EventsRepository provider;
	LayoutInflater inflater;
	Context context;

	@Before public void setUp() throws Exception {
		context = InstrumentationRegistry.getTargetContext();
		provider = new EventsRepository();
		listener = mock(EventActionsListener.class);
		date = Dates.dateForDayOfWeek(MONDAY, DateTime.now());
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		card = (DailyEventsCardView) inflater.inflate(R.layout.card_daily_events, null);
		setupCard(date);
	}

	@After public void teardown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	private void setupCard(DateTime date) {
		card.setup(listener, date);
	}

	@Test public void testSetupPreparesTheViewFieldsCorrectly() throws Exception {
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

	@Test public void testAfterLaunchDisplaysTheDateRelatedToTheCard() throws Exception {
		//After launching the activity, done in setUp with getActivity();
		theDateSubviewDisplaysTheProperText();
	}

	private void theDateSubviewDisplaysTheProperText() {
		assertTrue(card.dateView.getText().toString().equals(Dates.formatDate(card.date)));
	}

	@Test public void testAfterLaunchHighlightsTheCurrentDayInTheList() throws Exception {
		setupCard(DateTime.now());
		weGetAListWithTheExpectedEventsWithTodayHighlighted();
	}

	private void weGetAListWithTheExpectedEventsWithTodayHighlighted() {
		assertTrue(backgroundIs(Color.parseColor("#80cbc4")).matches(card));
	}

	@Test public void testAfterLaunchAnotherDayIsntHighlighted() throws Exception {
		setupCard(DateTime.now().plusDays(1));
		assertFalse(backgroundIs(Color.parseColor("#80cbc4")).matches(card));
	}

	@Test public void testUpdateEventsWithoutEventsShowsAnEmptyListMessage() {
		whenWeSendAnUpdatedEventListWithXEvents(0);
		theUserCanSeeAnEmptyListMessage();
	}

	private void whenWeSendAnUpdatedEventListWithXEvents(int eventCount) {
		List<Event> events = new ArrayList<>();
		for (int i = 0; i < eventCount; i++) {
			events.add(provider.createEmpty(date.plusHours(i)));
		}
		eventList = provider.fromList(events);
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

package com.suicune.teachershelpbook.views.events;


import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.EventList;
import com.suicune.teachershelpbook.model.events.EventsProvider;
import com.suicune.teachershelpbook.utils.Dates;
import com.suicune.teachershelpbook.views.courses.activities.CourseOverviewActivity;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.ArrayList;
import java.util.List;

import static com.suicune.teachershelpbook.views.events.DailyEventsCardView.NewEventsRequestedListener;


public class DailyEventsCardViewTest
		extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	DailyEventsCardView card;
	MockListener listener;
	DateTime date;
	CourseOverviewActivity activity;
	EventList eventList;

	public DailyEventsCardViewTest() {
		super(CourseOverviewActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		card = (DailyEventsCardView) activity.findViewById(R.id.monday_card);
		listener = new MockListener();
		date = Dates.dateForDayOfWeek(DateTimeConstants.MONDAY, new DateTime());
	}

	public void testSetupPreparesTheViewFieldsCorrectly() throws Exception {
		whenWeSetupTheView();
		theFieldsAreCorrectlySetup();
	}

	private void whenWeSetupTheView() {
		try {
			runTestOnUiThread(new Runnable() {
				@Override public void run() {
					card.setup(listener, date);
				}
			});
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
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

	public void testSetupPreparesTheAddNewViewSoItCallsTheListener() throws Exception {
		whenWeSetupTheView();
		andClickOnAddNew();
		theListenerGetsCalled();
	}

	private void andClickOnAddNew() {
		try {
			runTestOnUiThread(new Runnable() {
				@Override public void run() {
					card.addNewView.performClick();
				}
			});
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	private void theListenerGetsCalled() {
		assertTrue(listener.called);
	}

	public void testUpdateEventsWithoutEventsShowsAnEmptyListMessage() {
		whenWeSendAnUpdatedEventListWithXEvents(0);
		theUserCanSeeAnEmptyListMessage();
	}

	private void whenWeSendAnUpdatedEventListWithXEvents(int eventCount) {
		EventsProvider provider = new EventsProvider();
		List<Event> events = new ArrayList<>();
		for (int i = 0; i < eventCount; i++) {
			events.add(provider.createEmpty(date.plusHours(i)));
		}
		eventList = provider.listFromList(events);
		try {
			runTestOnUiThread(new Runnable() {
				@Override public void run() {
					card.updateEvents(eventList);
				}
			});
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	private void theUserCanSeeAnEmptyListMessage() {
		assertTrue(card.emptyEventListView.getVisibility() == View.VISIBLE);
		assertTrue(card.eventListView.getVisibility() == View.GONE);
	}

	public void testUpdateEventsWithEventsMakesTheMessageDisappear() throws Exception {
		whenWeSendAnUpdatedEventListWithXEvents(2);
		theUserCannotSeeTheEmptyListMessage();
	}

	private void theUserCannotSeeTheEmptyListMessage() {
		assertTrue(card.emptyEventListView.getVisibility() == View.GONE);
		assertTrue(card.eventListView.getVisibility() == View.VISIBLE);
	}

	public void testUpdateEventsWithEventsPopulatesTheEventList() throws Exception {
		whenWeSendAnUpdatedEventListWithXEvents(2);
		theListIsPopulatedWithTheEventInfo();
	}

	private void theListIsPopulatedWithTheEventInfo() {
		assertTrue(card.eventListView.getAdapter().getItemCount() == 2);
	}


	public class MockListener implements NewEventsRequestedListener {
		boolean called = false;

		@Override public void onNewEventRequested(DateTime date) {
			called = true;
		}
	}
}
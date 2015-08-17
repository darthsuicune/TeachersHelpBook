package com.dlgdev.teachers.helpbook.views.events;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.EventList;
import com.dlgdev.teachers.helpbook.models.factories.EventsFactory;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;
import com.dlgdev.teachers.helpbook.views.events.DailyEventsCardView.DailyEventsCardListener;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.joda.time.DateTimeConstants.THURSDAY;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class EspressoDailyEventsCardViewTest {
	DailyEventsCardView card;
	DailyEventsCardListener listener;
	DateTime date;
	EventList list;

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class);

	@Before public void setup() throws Exception {
		DatabaseUtils.getDatabase(InstrumentationRegistry.getTargetContext());
		date = DateTime.now().withDayOfWeek(THURSDAY);
		listener = mock(DailyEventsCardListener.class);
	}

	@After public void teardown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void clickOnAnEventCallsTheCallback() throws Throwable {
		setMockListener();
		whenWeSendAnUpdatedEventListWithXEvents(1);
		onView(allOf(withId(R.id.event_entry_name), isDescendantOfA(withId(R.id.thursday_card))))
				.perform(click());
		verify(listener).onEventSelected(list.events().get(0));
	}

	private void setMockListener() throws Throwable {
		getCard();
		rule.runOnUiThread(new Runnable() {
			@Override public void run() {
				card.setup(listener, date);
			}
		});
	}

	private void getCard() {
		card = ((DailyEventsCardView) rule.getActivity().findViewById(R.id.thursday_card));
	}

	private void whenWeSendAnUpdatedEventListWithXEvents(int eventCount) throws Throwable {
		EventsFactory provider = new EventsFactory();
		list = provider.listFromList(new ArrayList<Event>());
		for (int i = 0; i < eventCount; i++) {
			Event event = provider.createAndSaveAt(date, "sometext", "somedesc");
			list.add(event);
			event.save();
		}
	}
}

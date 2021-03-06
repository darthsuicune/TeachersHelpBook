package com.dlgdev.teachers.helpbook.views.events;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.domain.models.Course;
import com.dlgdev.teachers.helpbook.domain.models.Event;
import com.dlgdev.teachers.helpbook.domain.models.EventList;
import com.dlgdev.teachers.helpbook.domain.models.repositories.EventsRepository;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.joda.time.DateTimeConstants.MONDAY;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class EspressoDailyEventsCardViewTest {
	DailyEventsCardView card;
	EventActionsListener listener;
	DateTime date;
	EventList list;
	Course course;

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class, true, false);
	CourseOverviewActivity activity;

	@Before public void setup() throws Exception {
		course = new Course();
		course.save();
		date = DateTime.now().withDayOfWeek(MONDAY);
		listener = mock(EventActionsListener.class);
	}

	@After public void teardown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void clickOnAnEventCallsTheCallback() throws Throwable {
		setMockListener();
		whenWeSendAnUpdatedEventListWithEvents(1);
		onView(withId(card.getId())).perform(click());
		verify(listener).onEventSelected(list.events().get(0));
	}

	private void setMockListener() throws Throwable {
		getCard();
		rule.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				card.setup(listener, date);
			}
		});
	}

	private void getCard() {
		Intent intent = new Intent();
		intent.putExtra(CourseOverviewActivity.KEY_MODEL_ID, course.id);
		activity = rule.launchActivity(intent);
		card = ((DailyEventsCardView) activity.findViewById(R.id.monday_card));
	}

	private void whenWeSendAnUpdatedEventListWithEvents(int eventCount) throws Throwable {
		EventsRepository provider = new EventsRepository();
		list = provider.fromList(new ArrayList<Event>());
		for (int i = 0; i < eventCount; i++) {
			Event event = provider.createAndSaveAt(date, "sometext", "somedesc");
			list.add(event);
			event.save();
		}
		rule.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				card.updateEvents(list);
			}
		});
	}
}

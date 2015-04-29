package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.model.events.Event;
import com.dlgdev.teachers.helpbook.model.events.EventList;
import com.dlgdev.teachers.helpbook.model.events.EventsProvider;
import com.dlgdev.teachers.helpbook.views.events.DailyEventsCardView;
import com.dlgdev.teachers.helpbook.views.events.NewEventDialog;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

import static com.dlgdev.teachers.helpbook.views.events.DailyEventsCardView.NewEventsRequestedListener;
import static com.dlgdev.teachers.helpbook.views.events.NewEventDialog.NewEventDialogListener;
import static org.joda.time.DateTimeConstants.FRIDAY;
import static org.joda.time.DateTimeConstants.MONDAY;
import static org.joda.time.DateTimeConstants.SATURDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;
import static org.joda.time.DateTimeConstants.THURSDAY;
import static org.joda.time.DateTimeConstants.TUESDAY;
import static org.joda.time.DateTimeConstants.WEDNESDAY;

public class WeeklyEventsMainFragment extends WeeklyEventsFragment
		implements NewEventsRequestedListener, NewEventDialogListener {
	private static final String DIALOG_FRAGMENT_TAG = "dialog fragment";

	View rootView;
	Map<Integer, DailyEventsCardView> dailyCards = new HashMap<>();


	public WeeklyEventsMainFragment() {
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
									   @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.weekly_events_main_fragment, container, false);
		return rootView;
	}

	@Override protected void onDateUpdated() {
		if (isAdded()) {
			prepareMainLayout();
			updateViews();
		}
	}

	private void prepareMainLayout() {
		dailyCards.put(MONDAY, (DailyEventsCardView) rootView.findViewById(R.id.monday_card));
		dailyCards.put(TUESDAY, (DailyEventsCardView) rootView.findViewById(R.id.tuesday_card));
		dailyCards.put(WEDNESDAY, (DailyEventsCardView) rootView.findViewById(R.id.wednesday_card));
		dailyCards.put(THURSDAY, (DailyEventsCardView) rootView.findViewById(R.id.thursday_card));
		dailyCards.put(FRIDAY, (DailyEventsCardView) rootView.findViewById(R.id.friday_card));
		dailyCards.put(SATURDAY, (DailyEventsCardView) rootView.findViewById(R.id.saturday_card));
		dailyCards.put(SUNDAY, (DailyEventsCardView) rootView.findViewById(R.id.sunday_card));
	}

	private void updateViews() {
		for (Integer day : dailyCards.keySet()) {
			DailyEventsCardView card = dailyCards.get(day);
			card.setup(this, referenceDate.withDayOfWeek(day));
		}
	}

	@Override void onEventListUpdated() {
		for (Integer day : dailyCards.keySet()) {
			DateTime date = referenceDate.withDayOfWeek(day);
			EventList events = eventList.eventsOn(date);
			dailyCards.get(day).updateEvents(events);
		}
	}

	@Override public void onNewEventRequested(DateTime date) {
		Event event = new EventsProvider().createEmpty();
		NewEventDialog newEventDialog = new NewEventDialog();
		newEventDialog.setup(this, event, R.id.course_weekly_main_fragment);
		newEventDialog.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
	}

	@Override public void onNewEventCreated(Event event) {
		event.save();
		updateEventList(eventList);
		eventsListener.onNewEventCreated(event);
	}

	@Override public void onDialogCancelled() {
	}
}

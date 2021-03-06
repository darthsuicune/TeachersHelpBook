package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.domain.models.Event;
import com.dlgdev.teachers.helpbook.domain.models.EventList;
import com.dlgdev.teachers.helpbook.domain.models.repositories.EventsRepository;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.views.events.DailyEventsCardView;
import com.dlgdev.teachers.helpbook.views.events.EventActionsListener;
import com.dlgdev.teachers.helpbook.views.events.fragments.NewEventDialog;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

import static com.dlgdev.teachers.helpbook.views.events.fragments.NewEventDialog.NewEventDialogListener;
import static org.joda.time.DateTimeConstants.FRIDAY;
import static org.joda.time.DateTimeConstants.MONDAY;
import static org.joda.time.DateTimeConstants.SATURDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;
import static org.joda.time.DateTimeConstants.THURSDAY;
import static org.joda.time.DateTimeConstants.TUESDAY;
import static org.joda.time.DateTimeConstants.WEDNESDAY;

public class WeekMainFragment extends WeeklyEventsFragment
		implements NewEventDialogListener, EventActionsListener {
	private static final String DIALOG_FRAGMENT_TAG = "dialog fragment";
	Map<Integer, DailyEventsCardView> dailyCards = new HashMap<>();
	WeeklyEventsListener listener;
	View rootView;

	public WeekMainFragment() {}

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		listener = (WeeklyEventsListener) context;
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
									   @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_weekly_events_main, container, false);
		return rootView;
	}

	@Override public void onDateUpdated() {
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

		for(final int day : dailyCards.keySet()) {
			DailyEventsCardView v = dailyCards.get(day);
			v.setOnClickListener(new View.OnClickListener() {
				@Override public void onClick(View view) {
					listener.onNewDateSelected(Dates.dateForDayOfWeek(day, referenceDate));
				}
			});
		}
	}

	private void updateViews() {
		for (Integer day : dailyCards.keySet()) {
			DailyEventsCardView card = dailyCards.get(day);
			card.setup(this, referenceDate.withDayOfWeek(day));
		}
	}

	@Override public void loadEvents() {
		if(course != null) {
			this.eventList = course.eventsBetween(startOfWeek, endOfWeek);
			for (Integer day : dailyCards.keySet()) {
				DateTime date = referenceDate.withDayOfWeek(day);
				EventList events = eventList.eventsForDay(date);
				dailyCards.get(day).updateEvents(events);
			}
		}
	}

	@Override public void onNewEventRequested(DateTime date) {
		Event event = new EventsRepository().createEmpty(date);
		NewEventDialog newEventDialog = new NewEventDialog();
		newEventDialog.setup(this, event, R.id.course_weekly_main_fragment);
		newEventDialog.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
	}

	@Override public void onEventSelected(Event event) {
		listener.onExistingEventSelected(event);
	}

	@Override public void onNewEventCreated(Event event) {
		course.addEvent(event);
		loadEvents();
		listener.onNewEventCreated(event);
	}

	@Override public void onDialogCancelled() {
	}

	public interface WeeklyEventsListener {

		void onExistingEventSelected(Event event);

		void onNewDateSelected(DateTime dateTime);

		void onNewEventCreated(Event event);
	}
}

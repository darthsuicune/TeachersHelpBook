package com.suicune.teachershelpbook.views.courses.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.EventList;
import com.suicune.teachershelpbook.utils.Dates;
import com.suicune.teachershelpbook.views.events.DailyEventsCardView;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

import static org.joda.time.DateTimeConstants.FRIDAY;
import static org.joda.time.DateTimeConstants.MONDAY;
import static org.joda.time.DateTimeConstants.SATURDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;
import static org.joda.time.DateTimeConstants.THURSDAY;
import static org.joda.time.DateTimeConstants.TUESDAY;
import static org.joda.time.DateTimeConstants.WEDNESDAY;

public class WeeklyEventsMainFragment extends WeeklyEventsFragment
		implements DailyEventsCardView.NewEventsRequestedListener {
	View rootView;
	Map<DateTime, DailyEventsCardView> dailyCards;

	public WeeklyEventsMainFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.weekly_events_main_fragment, container, false);
		return rootView;
	}

	@Override protected void onDateUpdated() {
		prepareMainLayout();
		updateViews();
	}

	private void prepareMainLayout() {
		dailyCards = new HashMap<>();
		dailyCards.put(Dates.dateForDayOfWeek(MONDAY, startOfWeek),
				(DailyEventsCardView) rootView.findViewById(R.id.monday_card));
		dailyCards.put(Dates.dateForDayOfWeek(TUESDAY, startOfWeek),
				(DailyEventsCardView) rootView.findViewById(R.id.tuesday_card));
		dailyCards.put(Dates.dateForDayOfWeek(WEDNESDAY, startOfWeek),
				(DailyEventsCardView) rootView.findViewById(R.id.wednesday_card));
		dailyCards.put(Dates.dateForDayOfWeek(THURSDAY, startOfWeek),
				(DailyEventsCardView) rootView.findViewById(R.id.thursday_card));
		dailyCards.put(Dates.dateForDayOfWeek(FRIDAY, startOfWeek),
				(DailyEventsCardView) rootView.findViewById(R.id.friday_card));
		dailyCards.put(Dates.dateForDayOfWeek(SATURDAY, startOfWeek),
				(DailyEventsCardView) rootView.findViewById(R.id.saturday_card));
		dailyCards.put(Dates.dateForDayOfWeek(SUNDAY, startOfWeek),
				(DailyEventsCardView) rootView.findViewById(R.id.sunday_card));
	}

	private void updateViews() {
		for (DateTime day : dailyCards.keySet()) {
			DailyEventsCardView card = dailyCards.get(day);
			if (day.dayOfYear().equals(new DateTime().dayOfYear())) {
				int color = getResources().getColor(R.color.material_blue_grey_800);
				card.setBackgroundColor(color);
			}
			card.setup(this, day);
		}
	}

	@Override void onEventListUpdated() {
		for (DateTime date : dailyCards.keySet()) {
			EventList events = eventList.eventsOn(date);
			dailyCards.get(date).updateEvents(events);
		}
	}

	@Override public void onNewEventRequested(DateTime date) {
		Toast.makeText(getActivity(), "You have requested a new event", Toast.LENGTH_LONG).show();
	}
}

package com.suicune.teachershelpbook.views.courses.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.EventList;
import com.suicune.teachershelpbook.views.courses.DailyEventsCardView;

import java.util.HashMap;
import java.util.Map;

import static org.joda.time.DateTimeConstants.FRIDAY;
import static org.joda.time.DateTimeConstants.MONDAY;
import static org.joda.time.DateTimeConstants.SATURDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;
import static org.joda.time.DateTimeConstants.THURSDAY;
import static org.joda.time.DateTimeConstants.TUESDAY;
import static org.joda.time.DateTimeConstants.WEDNESDAY;

public class WeeklyEventsMainFragment extends WeeklyEventsFragment {
	Map<Integer, DailyEventsCardView> dailyCards;

	public WeeklyEventsMainFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.weekly_events_main_fragment, container, false);
		prepareMainLayout(v);
		return v;
	}

	private void prepareMainLayout(View v) {
		dailyCards = new HashMap<>();
		dailyCards.put(MONDAY, (DailyEventsCardView) v.findViewById(R.id.monday_card));
		dailyCards.put(TUESDAY, (DailyEventsCardView) v.findViewById(R.id.tuesday_card));
		dailyCards.put(WEDNESDAY, (DailyEventsCardView) v.findViewById(R.id.wednesday_card));
		dailyCards.put(THURSDAY, (DailyEventsCardView) v.findViewById(R.id.thursday_card));
		dailyCards.put(FRIDAY, (DailyEventsCardView) v.findViewById(R.id.friday_card));
		dailyCards.put(SATURDAY, (DailyEventsCardView) v.findViewById(R.id.saturday_card));
		dailyCards.put(SUNDAY, (DailyEventsCardView) v.findViewById(R.id.sunday_card));
	}

	@Override void onEventListUpdated() {
		for(int day = MONDAY; day <= SUNDAY; day++) {
			EventList events = eventList.eventsOn(MONDAY, referenceDate.getWeekOfWeekyear());
			dailyCards.get(day).updateEvents(events);
		}
	}
}

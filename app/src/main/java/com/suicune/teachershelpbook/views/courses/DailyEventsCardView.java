package com.suicune.teachershelpbook.views.courses;

import android.content.Context;
import android.support.v7.widget.CardView;

import com.suicune.teachershelpbook.model.events.EventList;

/**
 * Created by lapuente on 10.03.15.
 */
public class DailyEventsCardView extends CardView {
	EventList events;
	public DailyEventsCardView(Context context) {
		super(context);
	}

	public void updateEvents(EventList events) {
		this.events = events;
		updateEventList();
	}

	private void updateEventList() {

	}
}

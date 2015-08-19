package com.dlgdev.teachers.helpbook.views.events;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.utils.Dates;

public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
	EventActionsListener listener;
	Event event;
	TextView name;
	TextView time;

	public EventViewHolder(View itemView, EventActionsListener listener) {
		super(itemView);
		name = (TextView) itemView.findViewById(R.id.event_entry_name);
		time = (TextView) itemView.findViewById(R.id.event_entry_time);
		this.listener = listener;
	}

	public void event(Event event) {
		this.event = event;
		name.setText(event.title());
		time.setText(Dates.formatTimeRange(event.start(), event.end()));
	}

	@Override public void onClick(View view) {
		listener.onEventSelected(event);
	}
}

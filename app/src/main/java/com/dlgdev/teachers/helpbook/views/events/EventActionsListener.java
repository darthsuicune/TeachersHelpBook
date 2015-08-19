package com.dlgdev.teachers.helpbook.views.events;

import com.dlgdev.teachers.helpbook.models.Event;

import org.joda.time.DateTime;

public interface EventActionsListener {
	void onNewEventRequested(DateTime date);

	void onEventSelected(Event event);
}

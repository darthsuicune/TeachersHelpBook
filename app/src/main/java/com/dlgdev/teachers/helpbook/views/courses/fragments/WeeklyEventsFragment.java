package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.dlgdev.teachers.helpbook.Settings;
import com.dlgdev.teachers.helpbook.model.events.Event;
import com.dlgdev.teachers.helpbook.model.events.EventList;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.views.events.EventListLoader;

import org.joda.time.DateTime;

import static org.joda.time.DateTimeConstants.MONDAY;
import static org.joda.time.DateTimeConstants.SATURDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;

public abstract class WeeklyEventsFragment extends Fragment {
	static final int LOADER_EVENTS = 1;
	WeeklyEventsListener eventsListener;
	WeeklyPreviewListener previewListener;
	DateTime referenceDate;
	DateTime startOfWeek;
	DateTime endOfWeek;
	int startingDayOfWeek;
	int endingDayOfWeek;
	EventList eventList;
	SharedPreferences prefs;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			eventsListener = (WeeklyEventsListener) activity;
			previewListener = (WeeklyPreviewListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					activity.getLocalClassName() + " should implement the callback interfaces");
		}
		prefs = PreferenceManager.getDefaultSharedPreferences(activity);
		startingDayOfWeek = prefs.getInt(Settings.FIRST_DAY_OF_WEEK, MONDAY);
		endingDayOfWeek = (startingDayOfWeek == MONDAY) ? SUNDAY : SATURDAY;
	}

	public void updateDate(DateTime currentDate) {
		this.referenceDate = currentDate;
		startOfWeek = Dates.startOfWeek(referenceDate);
		endOfWeek = Dates.endOfWeek(referenceDate);
		onDateUpdated();
		if(isAdded()) {
			loadEvents();
		}
	}

	protected abstract void onDateUpdated();

	private void loadEvents() {
		Bundle args = new Bundle();
		args.putLong(EventListLoader.KEY_START, startOfWeek.getMillis());
		args.putLong(EventListLoader.KEY_END, endOfWeek.getMillis());
		getLoaderManager().restartLoader(LOADER_EVENTS, args, new EventLoaderHelper());
	}

	public interface WeeklyPreviewListener {
		void onPreviewTapped(DateTime referenceDate);
	}

	public interface WeeklyEventsListener {
		void onNewEventRequested(DateTime startDate);

		void onExistingEventSelected(Event event);

		void onNewDaySelected(DateTime newDate);
	}

	class EventLoaderHelper implements LoaderManager.LoaderCallbacks<EventList> {
		@Override
		public Loader<EventList> onCreateLoader(int id, Bundle args) {
			return new EventListLoader(getActivity(), args);
		}

		@Override
		public void onLoadFinished(Loader<EventList> loader, EventList data) {
			updateEventList(data);
		}

		@Override
		public void onLoaderReset(Loader<EventList> loader) {

		}
	}

	void updateEventList(EventList data) {
		this.eventList = data;
		onEventListUpdated();
	}

	abstract void onEventListUpdated();
}

package com.suicune.teachershelpbook.views.courses.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.suicune.teachershelpbook.Settings;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.EventList;
import com.suicune.teachershelpbook.model.events.EventListLoader;
import com.suicune.teachershelpbook.model.events.EventsProvider;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import static org.joda.time.DateTimeConstants.MONDAY;
import static org.joda.time.DateTimeConstants.SATURDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;

public abstract class WeeklyEventsFragment extends Fragment {
	static final int LOADER_EVENTS = 1;

	EventsProvider eventsProvider;
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
		eventsProvider = new EventsProvider();
		prefs = PreferenceManager.getDefaultSharedPreferences(activity);
		startingDayOfWeek = prefs.getInt(Settings.FIRST_DAY_OF_WEEK, MONDAY);
		endingDayOfWeek = (startingDayOfWeek == MONDAY) ? SUNDAY : SATURDAY;
	}

	public void updateDate(DateTime currentDate) {
		this.referenceDate = currentDate;
		Interval week = referenceDate.weekOfWeekyear().toInterval();
		startOfWeek = week.getStart();
		endOfWeek = week.getEnd().minusDays(1); //week.end returns the first day of the next week.
		loadEvents();
	}

	void loadEvents() {
		Bundle args = new Bundle();
		args.putLong(EventListLoader.KEY_START, startOfWeek.getMillis());
		args.putLong(EventListLoader.KEY_END, endOfWeek.getMillis());
		getLoaderManager().restartLoader(LOADER_EVENTS, args, new EventLoaderHelper());
	}

	public interface WeeklyPreviewListener {
		public void onPreviewTapped(DateTime referenceDate);
	}

	public interface WeeklyEventsListener {
		public void onNewEventRequested(Period period);

		public void onExistingEventSelected(Event event);

		public void onNewDaySelected(DateTime newDate);
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

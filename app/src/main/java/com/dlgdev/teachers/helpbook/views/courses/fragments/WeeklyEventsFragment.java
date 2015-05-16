package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.dlgdev.teachers.helpbook.Settings;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.EventList;
import com.dlgdev.teachers.helpbook.models.factories.EventsFactory;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsPreviewFragment.WeeklyPreviewListener;

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
	Course course;

	@Override public void onAttach(Activity activity) {
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
		if (isAdded()) {
			loadEvents();
		}
	}

	protected abstract void onDateUpdated();

	private void loadEvents() {
		getLoaderManager().restartLoader(LOADER_EVENTS, null, new EventLoaderHelper());
	}

	public void updateCourse(Course course) {
		this.course = course;
	}

	public interface WeeklyEventsListener {
		void onNewEventCreated(Event event);

		void onExistingEventSelected(Event event);

		void onNewDaySelected(DateTime newDate);
	}

	class EventLoaderHelper implements LoaderManager.LoaderCallbacks<Cursor> {
		@Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			Uri uri = TeachersDBContract.Events.URI;
			return new CursorLoader(getActivity(), uri, null, null, null, null);
		}

		@Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			updateEventList(new EventsFactory().listFromCursor(data));
		}

		@Override public void onLoaderReset(Loader<Cursor> loader) {

		}
	}

	void updateEventList(EventList data) {
		this.eventList = data;
		onEventListUpdated();
	}

	abstract void onEventListUpdated();
}

package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import com.dlgdev.teachers.helpbook.Settings;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.EventList;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsPreviewFragment.WeeklyPreviewListener;

import org.joda.time.DateTime;

import static org.joda.time.DateTimeConstants.MONDAY;

public abstract class WeeklyEventsFragment extends Fragment {
	WeeklyEventsListener eventsListener;
	WeeklyPreviewListener previewListener;
	DateTime referenceDate;
	DateTime startOfWeek;
	DateTime endOfWeek;
	int startingDayOfWeek;
	EventList eventList;
	SharedPreferences prefs;
	Course course;

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		eventsListener = (WeeklyEventsListener) context;
		previewListener = (WeeklyPreviewListener) context;
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		startingDayOfWeek = prefs.getInt(Settings.FIRST_DAY_OF_WEEK, MONDAY);
	}

	public void updateDate(DateTime currentDate) {
		this.referenceDate = currentDate;
		startOfWeek = Dates.startOfWeek(referenceDate, startingDayOfWeek);
		endOfWeek = Dates.endOfWeek(referenceDate, startingDayOfWeek);
		onDateUpdated();
		if (isAdded()) {
			loadEvents();
		}
	}

	protected abstract void onDateUpdated();

	abstract void loadEvents();

	public void updateCourse(Course course) {
		this.course = course;
		loadEvents();
	}

	public interface WeeklyEventsListener {
		void onNewEventCreated(Event event);

		void onExistingEventSelected(Event event);

		void onNewDaySelected(DateTime newDate);
	}
}

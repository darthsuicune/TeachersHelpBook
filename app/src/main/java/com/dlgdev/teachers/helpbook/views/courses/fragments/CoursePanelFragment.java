package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.models.events.EventList;
import com.dlgdev.teachers.helpbook.models.events.EventsProvider;
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;

public class CoursePanelFragment extends Fragment {
	private static final int LOADER_COURSE = 1;
	DateTime currentDate;
	DateTime referenceDate;
	CoursePanelListener listener;
	TextView currentWeek;
	TextView referenceWeek;
	TextView eventCounter;
	EventList eventList;

	public CoursePanelFragment() {
	}

	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (CoursePanelListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activities holding a CoursePanelFragment must implement CoursePanelListener");
		}
		loadCourseData();
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
									   @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.course_panel_fragment, container, false);
		prepareViews(v);
		return v;
	}

	private void prepareViews(View v) {
		currentWeek = (TextView) v.findViewById(R.id.current_date);
		referenceWeek = (TextView) v.findViewById(R.id.reference_week);
		eventCounter = (TextView) v.findViewById(R.id.event_counter);

		currentWeek.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onCurrentWeekTapped();
			}
		});
		eventCounter.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onEventCounterTapped();
			}
		});
	}

	public void updateDate(DateTime date) {
		showCurrentDate(date);
		this.referenceDate = date;
		DateTime startOfWeek = Dates.startOfWeek(date);
		DateTime endOfWeek = Dates.endOfWeek(date);
		if (referenceWeek != null) {
			referenceWeek.setText(Dates.formatDateRange(startOfWeek, endOfWeek));
		}
	}

	private void showCurrentDate(DateTime date) {
		if (currentDate == null) {
			currentDate = date;
			currentWeek.setText(Dates.formatDate(currentDate));
		}
	}

	public void loadCourseData() {
		getLoaderManager().initLoader(LOADER_COURSE, null, new CourseDataLoaderHelper());
	}

	public void eventList(EventList eventList) {
		this.eventList = eventList;
		eventCounter.setText(getString(R.string.event_count, eventList.eventCount()));
	}

	public interface CoursePanelListener {
		void onCurrentWeekTapped();

		void onEventCounterTapped();
	}

	class CourseDataLoaderHelper implements LoaderManager.LoaderCallbacks<Cursor> {
		@Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			return new CursorLoader(getActivity(), TeachersDBContract.Events.URI, null, null, null,
					null);
		}

		@Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			eventList(new EventsProvider().listFromCursor(data));
		}

		@Override public void onLoaderReset(Loader<Cursor> loader) {

		}
	}
}

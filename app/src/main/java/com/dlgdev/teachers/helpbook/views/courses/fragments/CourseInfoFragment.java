package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ollie.Ollie;

public class CourseInfoFragment extends WeeklyEventsFragment {
	private static final int LOADER_COURSE = 1;
	private static final String KEY_COURSE_ID = "course";
	CoursePanelListener listener;
	DateTime currentDate;
	TextView currentWeek;
	TextView referenceWeek;
	TextView eventCounter;
	List<Event> eventList;
	List<CourseInfoHolder> listeners = new ArrayList<>();

	public CourseInfoFragment() {
	}

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		listener = (CoursePanelListener) context;
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
									   @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_course_panel, container, false);
		prepareViews(v);
		return v;
	}

	private void prepareViews(View v) {
		v.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onPanelTapped(course);
			}
		});
		currentWeek = (TextView) v.findViewById(R.id.current_date);
		referenceWeek = (TextView) v.findViewById(R.id.reference_week);
		eventCounter = (TextView) v.findViewById(R.id.event_counter);
	}

	public void registerListeners(CourseInfoHolder... holders) {
		Collections.addAll(this.listeners, holders);
	}

	public void setup(Long courseId) {
		Bundle args = new Bundle();
		args.putLong(KEY_COURSE_ID, courseId);
		getLoaderManager().initLoader(LOADER_COURSE, args, new CourseLoaderHelper());
	}

	@Override protected void onDateUpdated() {
		showCurrentDate();
		if (referenceWeek != null) {
			referenceWeek.setText(Dates.formatDateRange(startOfWeek, endOfWeek));
		}
	}

	private void showCurrentDate() {
		if (currentDate == null) {
			currentDate = DateTime.now();
			currentWeek.setText(Dates.formatDate(currentDate));
		}
	}

	@Override void loadEvents() {
		if (course != null) {
			eventList(course.events());
		}
	}

	public void eventList(List<Event> eventList) {
		this.eventList = eventList;
		eventCounter.setText(getString(R.string.event_count, eventList.size()));
	}

	public interface CoursePanelListener {
		void onPanelTapped(Course course);
	}

	private class CourseLoaderHelper implements LoaderManager.LoaderCallbacks<Cursor> {
		@Override public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
			long id = args.getLong(KEY_COURSE_ID);
			Uri uri = TeachersDBContract.Courses.URI;
			String selection = TeachersDBContract.Courses._ID + "=?";
			String[] selectionArgs = {Long.toString(id)};
			return new CursorLoader(getActivity(), uri, null, selection, selectionArgs, null);
		}

		@Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			if (data.moveToFirst()) {
				course = Ollie.processCursor(Course.class, data).get(0);
				loadCourseData();
			}
		}

		@Override public void onLoaderReset(Loader<Cursor> loader) {

		}

	}

	private void loadCourseData() {
		getActivity().setTitle(course.title);
		reportCourseToFragments();
	}

	private void reportCourseToFragments() {
		for (CourseInfoHolder holder : listeners) {
			holder.updateCourse(course);
		}
	}
}

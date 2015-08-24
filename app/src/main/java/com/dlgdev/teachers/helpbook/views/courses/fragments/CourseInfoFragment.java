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

import com.activeandroid.content.ContentProvider;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class CourseInfoFragment extends WeeklyEventsFragment
		implements NewCourseDialog.NewCourseDialogListener {
	private static final int LOADER_COURSE = 1;
	private static final String KEY_COURSE_ID = "course";
	private static final String TAG_NEW_COURSE_DIALOG = "new course";
	CoursePanelListener listener;
	DateTime currentDate;
	TextView currentWeek;
	TextView referenceWeek;
	TextView eventCounter;
	List<Event> eventList;
	List<WeeklyEventsFragment> fragments = new ArrayList<>();

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

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (course != null) {
			outState.putLong(KEY_COURSE_ID, course.getId());
		}
	}

	public void setup(Long courseId) {
		if (courseId != null) {
			loadCourse(courseId);
		} else {
			createNewCourse();
		}
	}

	private void createNewCourse() {
		NewCourseDialog dialog = new NewCourseDialog();
		dialog.setup(this, R.id.course_info_panel);
		dialog.show(getFragmentManager(), TAG_NEW_COURSE_DIALOG);
	}

	@Override public void onCourseCreated(Course course) {

	}

	@Override public void onDialogCancelled() {
		getActivity().finish();
	}

	private void loadCourse(Long courseId) {
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
		if(course != null) {
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
			Uri uri = ContentProvider.createUri(Course.class, id);
			String selection = TeachersDBContract.Courses._ID + "=?";
			String[] selectionArgs = {Long.toString(id)};
			return new CursorLoader(getActivity(), uri, null, selection,
					selectionArgs, null);
		}

		@Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			if (data.moveToFirst()) {
				course = new Course();
				course.loadFromCursor(data);
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
		for(WeeklyEventsFragment fragment : fragments) {
			fragment.updateCourse(course);
		}
	}
}

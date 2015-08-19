package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.content.ContentProvider;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.views.ModelInfoActivity;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CoursePanelFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsMainFragment.WeeklyEventsListener;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsPreviewFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsPreviewFragment.WeeklyPreviewListener;

import org.joda.time.DateTime;

import static com.dlgdev.teachers.helpbook.views.courses.fragments.CoursePanelFragment.CoursePanelListener;


public class CourseOverviewActivity extends ModelInfoActivity
		implements WeeklyEventsListener, WeeklyPreviewListener, CoursePanelListener {
	private static final int LOADER_COURSE = 1;
	private static final String WORKING_DATE = "workingDate";
	public static final String KEY_COURSE = "course";
	SharedPreferences prefs;
	WeeklyEventsFragment mainViewFragment;
	WeeklyEventsFragment previousWeekFragment;
	WeeklyEventsFragment nextWeekFragment;
	WeeklyEventsFragment secondNextWeekFragment;
	CoursePanelFragment coursePanelFragment;
	DateTime currentDate;
	Course course;


	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.activity_course_overview);
		setSupportActionBar((Toolbar) findViewById(R.id.course_overview_toolbar));
		prepareCurrentDate(savedInstanceState);
		prepareCourseData(savedInstanceState);
	}

	private void prepareCurrentDate(Bundle savedInstanceState) {
		if (savedInstanceState != null && savedInstanceState.containsKey(WORKING_DATE)) {
			currentDate = new DateTime(savedInstanceState.getLong(WORKING_DATE));
		} else {
			currentDate = new DateTime();
		}
	}

	private void prepareCourseData(Bundle savedInstanceState) {
		Bundle extras = getIntent().getExtras();
		Bundle args = new Bundle();
		if (extras != null && extras.containsKey(KEY_COURSE)) {
			args.putLong(KEY_COURSE, extras.getLong(KEY_COURSE));
		}
		if (savedInstanceState != null && savedInstanceState.containsKey(KEY_COURSE)) {
			args.putLong(KEY_COURSE, savedInstanceState.getLong(KEY_COURSE));
		}
		getSupportLoaderManager().initLoader(LOADER_COURSE, args, new CourseLoaderHelper());
	}

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong(WORKING_DATE, currentDate.getMillis());
		if (course != null) {
			outState.putLong(KEY_COURSE, course.getId());
		}
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar will automatically handle clicks on the Home/Up button,
		// so long as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override public void onNewEventCreated(Event event) {
		Snackbar.make(findViewById(R.id.course_overview_root), R.string.event_created,
				Snackbar.LENGTH_LONG).show();
	}

	@Override public void onExistingEventSelected(Event event) {
		Snackbar.make(findViewById(R.id.course_overview_root), "Something!!!!" + event.title(),
				Snackbar.LENGTH_LONG).show();
	}

	@Override public void onNewDateSelected(DateTime newDate) {
		currentDate = newDate;
		reportDateToFragments();
	}

	@Override public void onPreviewSelected(DateTime referenceDate) {
		currentDate = referenceDate;
		reportDateToFragments();
	}

	@Override public void onPanelTapped() {
		Intent intent = new Intent(getApplicationContext(), CourseAdministrationActivity.class);
		intent.putExtra(CourseAdministrationActivity.KEY_COURSE, course.getId());
		startActivity(intent);
	}

	private class CourseLoaderHelper implements LoaderManager.LoaderCallbacks<Cursor> {
		@Override public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
			long id = args.getLong(KEY_COURSE);
			Uri uri = ContentProvider.createUri(Course.class, id);
			String selection = TeachersDBContract.Courses._ID + "=?";
			String[] selectionArgs = {Long.toString(id)};
			return new CursorLoader(CourseOverviewActivity.this, uri, null, selection,
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
		setTitle(course.title);
		loadFragments();
		reportDateToFragments();
		reportCourseToFragments();
	}

	private void loadFragments() {
		FragmentManager fm = getSupportFragmentManager();
		mainViewFragment =
				(WeeklyEventsFragment) fm.findFragmentById(R.id.course_weekly_main_fragment);
		previousWeekFragment =
				(WeeklyEventsPreviewFragment) fm.findFragmentById(R.id.course_weekly_previous);
		nextWeekFragment =
				(WeeklyEventsPreviewFragment) fm.findFragmentById(R.id.course_weekly_next);
		secondNextWeekFragment =
				(WeeklyEventsPreviewFragment) fm.findFragmentById(R.id.course_weekly_second_next);
		coursePanelFragment = (CoursePanelFragment) fm.findFragmentById(R.id.course_overview_panel);
	}

	private void reportDateToFragments() {
		mainViewFragment.updateDate(currentDate);
		previousWeekFragment.updateDate(currentDate.minusWeeks(1));
		nextWeekFragment.updateDate(currentDate.plusWeeks(1));
		secondNextWeekFragment.updateDate(currentDate.plusWeeks(2));
		coursePanelFragment.updateDate(currentDate);
	}

	private void reportCourseToFragments() {
		mainViewFragment.updateCourse(course);
		previousWeekFragment.updateCourse(course);
		nextWeekFragment.updateCourse(course);
		secondNextWeekFragment.updateCourse(course);
		coursePanelFragment.updateCourse(course);
	}
}

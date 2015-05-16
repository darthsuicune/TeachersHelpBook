package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CoursePanelFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsPreviewFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsPreviewFragment.WeeklyPreviewListener;

import org.joda.time.DateTime;

import static com.dlgdev.teachers.helpbook.views.courses.fragments.CoursePanelFragment.CoursePanelListener;
import static com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsFragment.WeeklyEventsListener;


public class CourseOverviewActivity extends AppCompatActivity implements WeeklyEventsListener,
		WeeklyPreviewListener, CoursePanelListener {
	private static final int LOADER_COURSE = 1;
	private static final String WORKING_DATE = "workingDate";
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
		setContentView(R.layout.course_overview_activity);
		prepareCurrentDate(savedInstanceState);
		getSupportLoaderManager().initLoader(LOADER_COURSE, null, new CourseLoaderHelper());
	}

	private void prepareCurrentDate(Bundle savedInstanceState) {
		if (savedInstanceState != null && savedInstanceState.containsKey(WORKING_DATE)) {
			currentDate = new DateTime(savedInstanceState.getLong(WORKING_DATE));
		} else {
			currentDate = new DateTime();
		}
	}

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong(WORKING_DATE, currentDate.getMillis());
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
		Toast.makeText(this, R.string.event_created, Toast.LENGTH_LONG).show();
	}

	@Override public void onExistingEventSelected(Event event) {
		Toast.makeText(this, "Something!!!!" + event.title(), Toast.LENGTH_LONG).show();
	}

	@Override public void onNewDaySelected(DateTime newDate) {
		currentDate = newDate;
		reportDateToFragments();
	}

	@Override public void onPreviewTapped(DateTime referenceDate) {
		currentDate = referenceDate;
		reportDateToFragments();
	}

	@Override public void onPanelTapped() {
		Intent intent = new Intent(getApplicationContext(), CourseAdministrationActivity.class);
		intent.putExtra(CourseAdministrationActivity.KEY_COURSE, course.getId());
		startActivity(intent);
	}

	private class CourseLoaderHelper implements LoaderManager.LoaderCallbacks<Cursor> {
		@Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			Uri uri = TeachersDBContract.Courses.URI;
			return new CursorLoader(CourseOverviewActivity.this, uri, null, null, null, null);
		}

		@Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			course = new Course();
			if(data.moveToFirst()) {
				course.loadFromCursor(data);
			}
			setupFragments();
		}

		@Override public void onLoaderReset(Loader<Cursor> loader) {

		}
	}

	private void setupFragments() {
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
		reportDateToFragments();
		reportCourseToFragments();
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

package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.views.ModelInfoActivity;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CourseInfoFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsMainFragment.WeeklyEventsListener;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsPreviewFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsPreviewFragment.WeeklyPreviewListener;

import org.joda.time.DateTime;

import static com.dlgdev.teachers.helpbook.views.courses.fragments.CourseInfoFragment.CoursePanelListener;


public class CourseOverviewActivity extends ModelInfoActivity
		implements WeeklyEventsListener, WeeklyPreviewListener, CoursePanelListener {
	private static final String WORKING_DATE = "workingDate";
	SharedPreferences prefs;
	WeeklyEventsFragment mainViewFragment;
	WeeklyEventsFragment previousWeekFragment;
	WeeklyEventsFragment nextWeekFragment;
	WeeklyEventsFragment secondNextWeekFragment;
	CourseInfoFragment courseInfoFragment;
	DateTime currentDate;


	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.activity_course_overview);
		setSupportActionBar((Toolbar) findViewById(R.id.course_overview_toolbar));
		loadFragments();
		prepareCurrentDate(savedInstanceState);
		prepareCourseData();
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
		courseInfoFragment = (CourseInfoFragment) fm.findFragmentById(R.id.course_info_panel);
		courseInfoFragment.registerListeners(mainViewFragment, previousWeekFragment,
				nextWeekFragment, secondNextWeekFragment);
	}

	private void prepareCurrentDate(Bundle savedInstanceState) {
		if (savedInstanceState != null && savedInstanceState.containsKey(WORKING_DATE)) {
			currentDate = new DateTime(savedInstanceState.getLong(WORKING_DATE));
		} else {
			currentDate = new DateTime();
		}
		reportDateToFragments();
	}

	private void reportDateToFragments() {
		mainViewFragment.updateDate(currentDate);
		previousWeekFragment.updateDate(currentDate.minusWeeks(1));
		nextWeekFragment.updateDate(currentDate.plusWeeks(1));
		secondNextWeekFragment.updateDate(currentDate.plusWeeks(2));
		courseInfoFragment.updateDate(currentDate);
	}

	private void prepareCourseData() {
		Long id = null;
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey(KEY_MODEL_ID)) {
			id = extras.getLong(KEY_MODEL_ID);
		}
		courseInfoFragment.setup(id);
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

	@Override public void onPanelTapped(Course course) {
		Intent intent = new Intent(getApplicationContext(), CourseAdministrationActivity.class);
		intent.putExtra(CourseAdministrationActivity.KEY_COURSE, course.getId());
		startActivity(intent);
	}
}

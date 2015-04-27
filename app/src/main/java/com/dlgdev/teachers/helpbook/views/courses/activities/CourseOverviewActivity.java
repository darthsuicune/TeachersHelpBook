package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.model.events.Event;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CoursePanelFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsPreviewFragment;

import org.joda.time.DateTime;

import static com.dlgdev.teachers.helpbook.views.courses.fragments.CoursePanelFragment.CoursePanelListener;
import static com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsFragment.WeeklyEventsListener;
import static com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsFragment.WeeklyPreviewListener;


public class CourseOverviewActivity extends AppCompatActivity implements WeeklyEventsListener,
		WeeklyPreviewListener, CoursePanelListener {
	private static final String WORKING_DATE = "workingDate";
	SharedPreferences prefs;
	WeeklyEventsFragment mainViewFragment;
	WeeklyEventsPreviewFragment previousWeekFragment;
	WeeklyEventsPreviewFragment nextWeekFragment;
	WeeklyEventsPreviewFragment secondNextWeekFragment;
	CoursePanelFragment coursePanelFragment;
	DateTime currentDate;


	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.course_overview_activity);
		prepareCurrentDate(savedInstanceState);
		setupFragments();
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

	private void setupFragments() {
		FragmentManager fm = getSupportFragmentManager();
		mainViewFragment =
				(WeeklyEventsFragment) fm.findFragmentById(R.id.course_weekly_main_fragment);
		previousWeekFragment =
				(WeeklyEventsPreviewFragment) fm.findFragmentById(R.id.course_weekly_previous);
		nextWeekFragment = (WeeklyEventsPreviewFragment) fm.findFragmentById(R.id.course_weekly_next);
		secondNextWeekFragment =
				(WeeklyEventsPreviewFragment) fm.findFragmentById(R.id.course_weekly_second_next);
		coursePanelFragment = (CoursePanelFragment) fm.findFragmentById(R.id.course_overview_panel);
		reportDateToFragments();
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

	@Override public void onNewEventRequested(DateTime time) {
		//TODO: Do something
	}

	@Override public void onExistingEventSelected(Event event) {
		//TODO: Do something
	}

	@Override public void onNewDaySelected(DateTime newDate) {
		currentDate = newDate;
		reportDateToFragments();
	}

	private void reportDateToFragments() {
		mainViewFragment.updateDate(currentDate);
		previousWeekFragment.updateDate(currentDate.minusWeeks(1));
		nextWeekFragment.updateDate(currentDate.plusWeeks(1));
		secondNextWeekFragment.updateDate(currentDate.plusWeeks(2));
		coursePanelFragment.updateDate(currentDate);
	}

	@Override public void onPreviewTapped(DateTime referenceDate) {
		currentDate = referenceDate;
		reportDateToFragments();
	}

	@Override public void onCurrentWeekTapped() {
		currentDate = new DateTime();
		reportDateToFragments();
	}

	@Override public void onEventCounterTapped() {
		Toast.makeText(this, "Something fancy will happen!", Toast.LENGTH_LONG).show();
	}
}

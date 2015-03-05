package com.suicune.teachershelpbook.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.views.fragments.courses.CoursePanelFragment;
import com.suicune.teachershelpbook.views.fragments.courses.WeeklyEventsFragment;

import java.util.Date;

import static com.suicune.teachershelpbook.views.fragments.courses.WeeklyEventsFragment.WeeklyEventsListener;
import static com.suicune.teachershelpbook.views.fragments.courses.WeeklyEventsFragment.WeeklyPreviewListener;


public class CourseOverviewActivity extends ActionBarActivity
		implements WeeklyEventsListener, WeeklyPreviewListener {
	private static final String WORKING_DATE = "workingDate";
	SharedPreferences prefs;
	WeeklyEventsFragment mainViewFragment;
	WeeklyEventsFragment previousWeekFragment;
	WeeklyEventsFragment nextWeekFragment;
	WeeklyEventsFragment secondNextWeekFragment;
	CoursePanelFragment coursePanelFragment;
	Date currentDate;


	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.course_overview_activity);
		prepareCurrentDate(savedInstanceState);
		setupFragments();
	}

	private void prepareCurrentDate(Bundle savedInstanceState) {
		if (savedInstanceState != null && savedInstanceState.containsKey(WORKING_DATE)) {
			currentDate = new Date(savedInstanceState.getLong(WORKING_DATE));
		} else {
			currentDate = new Date();
		}
	}

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong(WORKING_DATE, currentDate.getTime());
	}

	private void setupFragments() {
		FragmentManager fm = getSupportFragmentManager();
		mainViewFragment =
				(WeeklyEventsFragment) fm.findFragmentById(R.id.course_weekly_main_fragment);
		previousWeekFragment =
				(WeeklyEventsFragment) fm.findFragmentById(R.id.course_weekly_previous);
		nextWeekFragment = (WeeklyEventsFragment) fm.findFragmentById(R.id.course_weekly_next);
		secondNextWeekFragment =
				(WeeklyEventsFragment) fm.findFragmentById(R.id.course_weekly_second_next);
		coursePanelFragment = (CoursePanelFragment) fm.findFragmentById(R.id.course_overview_panel);
		reportDateToFragments();
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will automatically handle clicks
		// on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override public void onNewEventRequested(Time time) {
		//TODO: Do something
	}

	@Override public void onExistingEventSelected(Event event) {
		//TODO: Do something
	}

	@Override public void onNewDaySelected(Date newDate) {
		currentDate = newDate;
		reportDateToFragments();
	}

	private void reportDateToFragments() {
		mainViewFragment.updateDate(currentDate);
		previousWeekFragment.updateDate(currentDate, -7);
		nextWeekFragment.updateDate(currentDate, 7);
		secondNextWeekFragment.updateDate(currentDate, 14);
		coursePanelFragment.updateDate(currentDate);
	}

	@Override public void onPreviewTapped(Date referenceDate) {
		currentDate = referenceDate;
		reportDateToFragments();
	}
}

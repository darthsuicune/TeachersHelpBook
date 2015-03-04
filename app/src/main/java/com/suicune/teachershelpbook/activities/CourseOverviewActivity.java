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
import com.suicune.teachershelpbook.views.fragments.courses.WeeklyEventsFragment;

import java.util.Date;

import static com.suicune.teachershelpbook.views.fragments.courses.WeeklyEventsFragment.*;


public class CourseOverviewActivity extends ActionBarActivity
		implements WeeklyEventsListener, WeeklyPreviewListener {
	private static final String WORKING_DATE = "workingDate";
	SharedPreferences prefs;
	WeeklyEventsFragment mainViewFragment;
	WeeklyEventsFragment previousWeekFragment;
	WeeklyEventsFragment nextWeekFragment;
	WeeklyEventsFragment secondNextWeekFragment;
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

	private void setupFragments() {
		FragmentManager fm = getSupportFragmentManager();
		mainViewFragment =
				(WeeklyEventsFragment) fm.findFragmentById(R.id.course_weekly_main_fragment);
		previousWeekFragment =
				(WeeklyEventsFragment) fm.findFragmentById(R.id.course_weekly_previous);
		nextWeekFragment =
				(WeeklyEventsFragment) fm.findFragmentById(R.id.course_weekly_next);
		secondNextWeekFragment =
				(WeeklyEventsFragment) fm.findFragmentById(R.id.course_weekly_second_next);
	}


	@Override public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
		reportNewDateToFragments();
	}

	private void reportNewDateToFragments() {
		mainViewFragment.currentDate(currentDate);
		previousWeekFragment.currentDate(currentDate, -7);
		nextWeekFragment.currentDate(currentDate, 7);
		secondNextWeekFragment.currentDate(currentDate, 14);
	}

	@Override public void onPreviewTapped(Date referenceDate) {
		currentDate = referenceDate;
		setupFragments();
	}
}

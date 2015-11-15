package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.views.ModelInfoActivity;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CourseInfoFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CourseInfoHolder;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CourseOverviewDrawerFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CourseOverviewDrawerFragment.OnOverviewDrawerListener;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeeklyEventsFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeekMainFragment.WeeklyEventsListener;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeekPreviewFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeekPreviewFragment.WeeklyPreviewListener;
import com.dlgdev.teachers.helpbook.views.events.activities.EventsInfoActivity;
import com.dlgdev.teachers.helpbook.views.holidays.activities.HolidaysInfoActivity;
import com.dlgdev.teachers.helpbook.views.students.activities.StudentGroupsInfoActivity;
import com.dlgdev.teachers.helpbook.views.subjects.activities.SubjectsInfoActivity;

import org.joda.time.DateTime;

import static com.dlgdev.teachers.helpbook.views.courses.fragments.CourseInfoFragment.CoursePanelListener;

public class CourseOverviewActivity extends ModelInfoActivity
		implements WeeklyEventsListener, WeeklyPreviewListener, CoursePanelListener,
		OnOverviewDrawerListener, CourseInfoHolder {
	private static final String WORKING_DATE = "workingDate";
	SharedPreferences prefs;
	WeeklyEventsFragment mainViewFragment;
	WeeklyEventsFragment previousWeekFragment;
	WeeklyEventsFragment nextWeekFragment;
	WeeklyEventsFragment secondNextWeekFragment;
	CourseOverviewDrawerFragment drawerFragment;
	CourseInfoFragment courseInfoFragment;
	DateTime currentDate;
	Course course;


	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.activity_course_overview);
		Toolbar toolbar = (Toolbar) findViewById(R.id.course_overview_toolbar);
		setSupportActionBar(toolbar);
		loadFragments(toolbar);
		prepareCurrentDate(savedInstanceState);
		prepareCourseData();
	}

	private void loadFragments(Toolbar toolbar) {
		FragmentManager fm = getSupportFragmentManager();
		mainViewFragment =
				(WeeklyEventsFragment) fm.findFragmentById(R.id.course_weekly_main_fragment);
		previousWeekFragment =
				(WeekPreviewFragment) fm.findFragmentById(R.id.course_weekly_previous);
		nextWeekFragment =
				(WeekPreviewFragment) fm.findFragmentById(R.id.course_weekly_next);
		secondNextWeekFragment =
				(WeekPreviewFragment) fm.findFragmentById(R.id.course_weekly_second_next);
		drawerFragment = (CourseOverviewDrawerFragment) fm
				.findFragmentById(R.id.course_overview_navigation_drawer);
		drawerFragment.setup((DrawerLayout) findViewById(R.id.overview_drawer_layout),
				findViewById(R.id.course_overview_navigation_drawer), toolbar,
				PreferenceManager.getDefaultSharedPreferences(this));
		courseInfoFragment = (CourseInfoFragment) fm.findFragmentById(R.id.course_info_panel);
		courseInfoFragment
				.registerListeners(mainViewFragment, previousWeekFragment, nextWeekFragment,
						secondNextWeekFragment, drawerFragment, this);
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
		long id = 0;
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
		intent.putExtra(CourseAdministrationActivity.KEY_MODEL_ID, course.id);
		startActivity(intent);
	}

	public void openDrawer() {
		drawerFragment.openDrawer();
	}

	@Override public void onEventsRequested() {
		openModelListActivity(EventsInfoActivity.class);
	}

	private void openModelListActivity(Class<? extends ModelInfoActivity> modelClass) {
		Intent intent = new Intent(this, modelClass);
		intent.putExtra(ModelInfoActivity.KEY_MODEL_ID, course.id);
		startActivity(intent);
	}

	@Override public void onSubjectsRequested() {
		openModelListActivity(SubjectsInfoActivity.class);
	}

	@Override public void onStudentGroupsRequested() {
		openModelListActivity(StudentGroupsInfoActivity.class);
	}

	@Override public void onHolidaysRequested() {
		openModelListActivity(HolidaysInfoActivity.class);
	}

	@Override public void onCourseInfoRequested() {
		onPanelTapped(course);
	}

	@Override public void updateCourse(Course course) {
		this.course = course;
	}
}

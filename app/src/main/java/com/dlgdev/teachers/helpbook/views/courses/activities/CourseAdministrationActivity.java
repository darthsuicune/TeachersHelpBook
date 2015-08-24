package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.activeandroid.content.ContentProvider;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.views.ModelInfoActivity;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CourseAdministrationFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CourseAdministrationFragment.CourseAdministrationActionListener;
import com.dlgdev.teachers.helpbook.views.events.activities.EventsInfoActivity;
import com.dlgdev.teachers.helpbook.views.holidays.activities.HolidaysInfoActivity;
import com.dlgdev.teachers.helpbook.views.students.activities.StudentGroupsInfoActivity;
import com.dlgdev.teachers.helpbook.views.subjects.activities.SubjectsInfoActivity;

public class CourseAdministrationActivity extends AppCompatActivity
		implements CourseAdministrationActionListener {
	public static final String KEY_COURSE = "course";
	private static final int LOADER_COURSE = 1;
	CourseAdministrationFragment fragment;
	Course course;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		course = new Course();
		parseInvocationArguments();
		setContentView(R.layout.activity_course_administration);
		setSupportActionBar((Toolbar) findViewById(R.id.course_administration_toolbar));
		fragment = (CourseAdministrationFragment) getSupportFragmentManager()
				.findFragmentById(R.id.course_administration_fragment);
	}

	private void parseInvocationArguments() {
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey(KEY_COURSE)) {
			getSupportLoaderManager().initLoader(LOADER_COURSE, extras, new CourseLoaderHelper());
		}
	}

	@Override public void onSaved(Course course) {
		openModelInfoActivity(CourseOverviewActivity.class, course.getId());
		finish();
	}

	private void openModelInfoActivity(Class<? extends ModelInfoActivity> target, long id) {
		if(course.getId() == null) {
			course.save();
		}
		Intent intent = new Intent(this, target);
		intent.putExtra(ModelInfoActivity.KEY_MODEL_ID, id);
		startActivity(intent);
	}

	@Override public void onStudentGroupsInfoRequested() {
		openModelInfoActivity(StudentGroupsInfoActivity.class, course.getId());
	}

	@Override public void onHolidaysInfoRequested() {
		openModelInfoActivity(HolidaysInfoActivity.class, course.getId());
	}

	@Override public void onEventsInfoRequested() {
		openModelInfoActivity(EventsInfoActivity.class, course.getId());
	}

	@Override public void onSubjectsInfoRequested() {
		openModelInfoActivity(SubjectsInfoActivity.class, course.getId());
	}

	private class CourseLoaderHelper implements LoaderManager.LoaderCallbacks<Cursor> {
		@Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			long courseId = args.getLong(KEY_COURSE);
			Uri uri = ContentProvider.createUri(Course.class, courseId);
			return new CursorLoader(CourseAdministrationActivity.this, uri, null, null, null, null);
		}

		@Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			if (data != null && data.moveToFirst()) {
				course.loadFromCursor(data);
				fragment.course(course);
				if (!TextUtils.isEmpty(course.title)) {
					setTitle(course.title);
				}
			}
		}

		@Override public void onLoaderReset(Loader<Cursor> loader) {

		}
	}
}

package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.activeandroid.content.ContentProvider;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.courses.Course;
import com.dlgdev.teachers.helpbook.models.subjects.Subject;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CourseAdministrationFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CourseAdministrationFragment.CourseAdministrationActionListener;

import org.joda.time.DateTime;

public class CourseAdministrationActivity extends AppCompatActivity implements
		CourseAdministrationActionListener {
	public static final String KEY_COURSE = "course";
	private static final int LOADER_COURSE = 1;
	CourseAdministrationFragment fragment;
	Course course;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		course = new Course();
		parseInvocationArguments();
		setContentView(R.layout.course_administration_activity);
		fragment = (CourseAdministrationFragment) getSupportFragmentManager()
				.findFragmentById(R.id.course_administration_fragment);
	}

	private void parseInvocationArguments() {
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey(KEY_COURSE)) {
			getSupportLoaderManager().initLoader(LOADER_COURSE, extras, new CourseLoaderHelper());
		} else {
			finish();
		}

	}

	@Override public void onNewSubjectRequested() {

	}

	@Override public void onSubjectSelected(Subject subject) {

	}

	@Override public void onNewBankHolidayRequested() {

	}

	@Override public void onBankHolidaySelected(DateTime date) {

	}

	@Override public void onNewGroupRequested() {

	}

	@Override public void onGroupSelected() {

	}

	private class CourseLoaderHelper implements LoaderManager.LoaderCallbacks<Cursor> {
		@Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			long courseId = args.getLong(KEY_COURSE);
			Uri uri = ContentProvider.createUri(Course.class, courseId);
			return new CursorLoader(CourseAdministrationActivity.this, uri, null, null, null, null);
		}

		@Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			if(data != null && data.moveToFirst()) {
				course.loadFromCursor(data);
				fragment.course(course);
			}
		}

		@Override public void onLoaderReset(Loader<Cursor> loader) {

		}
	}
}

package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CoursesListFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CoursesListFragment.OnCourseListInteractionListener;

public class CoursesListActivity extends AppCompatActivity implements
		OnCourseListInteractionListener {
	CoursesListFragment fragment;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (shouldSkipList()) {
			skipToCourse();
			finish();
		} else {
			setContentView(R.layout.activity_courses_list);
			fragment = (CoursesListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.courses_list_fragment);
			fragment.setHasOptionsMenu(true);
		}
	}

	private boolean shouldSkipList() {
		return Course.current() != null;
	}

	private void skipToCourse() {
		onCourseSelected(Course.current());
	}

	@Override public void onCourseSelected(Course course) {
		Intent intent = new Intent(this, CourseOverviewActivity.class);
		intent.putExtra(CourseOverviewActivity.KEY_COURSE, course.getId());
		startActivity(intent);
	}

	@Override public void onNewCourseRequested() {
		Intent intent = new Intent(this, CourseAdministrationActivity.class);
		startActivity(intent);
	}
}

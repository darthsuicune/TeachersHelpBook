package com.dlgdev.teachers.helpbook.views.courses.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.domain.models.Course;
import com.dlgdev.teachers.helpbook.domain.models.repositories.CoursesRepository;
import com.dlgdev.teachers.helpbook.views.ModelInfoActivity;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CoursesListFragment;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CoursesListFragment.OnCourseListInteractionListener;

public class CoursesListActivity extends AppCompatActivity
		implements OnCourseListInteractionListener {
	CoursesListFragment fragment;
	Course course;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_courses_list);
		fragment = (CoursesListFragment) getSupportFragmentManager()
				.findFragmentById(R.id.courses_list_fragment);
		if (justEnteredApp(savedInstanceState)) {
			course = new CoursesRepository().current();
			if (shouldSkipList()) {
				skipToCourse();
				finish();
			}
			setSupportActionBar((Toolbar) findViewById(R.id.course_list_toolbar));
		}
	}

	private boolean justEnteredApp(Bundle savedInstanceState) {
		return savedInstanceState != null;
	}

	private boolean shouldSkipList() {
		return course != null;
	}

	private void skipToCourse() {
		onCourseSelected(course);
	}

	@Override public void onCourseSelected(Course course) {
		this.course = course;
		Intent intent = new Intent(this, CourseOverviewActivity.class);
		intent.putExtra(ModelInfoActivity.KEY_MODEL_ID, course.id);
		startActivity(intent);
	}
}

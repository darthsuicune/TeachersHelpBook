package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Subject;

import org.joda.time.DateTime;

/**
 * This Fragment should offer the chance to add new subjects, view the current subjects, handle
 * the bank holiday, course start date, course end date, student groups for the course, etc
 */
public class CourseAdministrationFragment extends Fragment {
	CourseAdministrationActionListener listener;
	Course course;

	public CourseAdministrationFragment() {
	}

	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (CourseAdministrationActionListener) activity;
	}

	@Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.course_administration_fragment, container, false);
		setupViews(v);
		return v;
	}

	public void setupViews(View v) {

	}

	public void course(Course course) {
		this.course = course;
	}

	public interface CourseAdministrationActionListener {
		void onNewSubjectRequested();

		void onSubjectSelected(Subject subject);

		void onNewBankHolidayRequested();

		void onBankHolidaySelected(DateTime date);

		void onNewGroupRequested();

		void onGroupSelected();
	}
}

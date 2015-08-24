package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.utils.InvalidDateTimeException;

/**
 * This Fragment should offer the chance to add new subjects, view the current subjects, handle
 * the bank holiday, course start date, course end date, student groups for the course, etc
 */
public class CourseAdministrationFragment extends Fragment {
	CourseAdministrationActionListener listener;
	EditText courseNameView;
	EditText courseDescriptionView;
	EditText startDateView;
	EditText endDateView;
	TextView holidaysView;
	TextView studentGroupsView;
	TextView subjectsView;
	TextView eventsView;

	Course course;

	public CourseAdministrationFragment() {
	}

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		listener = (CourseAdministrationActionListener) context;
		setHasOptionsMenu(true);
	}

	@Override public void onDetach() {
		super.onDetach();
		setHasOptionsMenu(false);
		listener = null;
	}

	@Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
												 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_course_administration, container, false);
		setupViews(v);
		return v;
	}

	public void setupViews(View v) {
		courseNameView = (EditText) v.findViewById(R.id.course_administration_name);
		courseDescriptionView = (EditText) v.findViewById(R.id.course_administration_description);
		startDateView = (EditText) v.findViewById(R.id.course_administration_start_date);
		endDateView = (EditText) v.findViewById(R.id.course_administration_end_date);
		holidaysView = (TextView) v.findViewById(R.id.course_administration_holidays);
		studentGroupsView = (TextView) v.findViewById(R.id.course_administration_students);
		subjectsView = (TextView) v.findViewById(R.id.course_administration_subjects);
		eventsView = (TextView) v.findViewById(R.id.course_administration_events);

		setTags(0,0,0,0);
	}

	private void setTags(int groups, int holidays, int subjects, int events) {
		studentGroupsView.setText(getString(R.string.student_groups_marker, groups));
		holidaysView.setText(getString(R.string.holidays_marker, holidays));
		subjectsView.setText(getString(R.string.subjects_marker, subjects));
		eventsView.setText(getString(R.string.events_marker, events));
	}

	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_course_administration, menu);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_save_course:
				saveCourseInformation();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void saveCourseInformation() {
		if (course == null) {
			course = new Course();
		}
		try {
			course.title = courseNameView.getText().toString();
			course.description = courseDescriptionView.getText().toString();
			course.start = Dates.parseDate(startDateView.getText().toString());
			course.end = Dates.parseDate(endDateView.getText().toString());
		} catch (InvalidDateTimeException e) {
			e.printStackTrace();
		} finally {
			course.save();
			listener.onSaved(course);
		}
	}

	public void course(Course course) {
		this.course = course;
		courseNameView.setText(course.title);
		courseDescriptionView.setText(course.description);
		if (course.start != null) {
			startDateView.setText(Dates.formatDate(course.start));
		}
		if (course.end != null) {
			endDateView.setText(Dates.formatDate(course.end));
		}

		setTags(course.studentGroups().size(), course.holidays().size(),
				course.subjects().size(), course.events().size());
		studentGroupsView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onStudentGroupsInfoRequested();
			}
		});
		holidaysView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onHolidaysInfoRequested();
			}
		});
		eventsView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onEventsInfoRequested();
			}
		});
		subjectsView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onSubjectsInfoRequested();
			}
		});
	}

	public interface CourseAdministrationActionListener {

		void onSaved(Course course);

		void onStudentGroupsInfoRequested();

		void onHolidaysInfoRequested();

		void onEventsInfoRequested();

		void onSubjectsInfoRequested();
	}
}

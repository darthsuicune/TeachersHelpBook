package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.Holiday;
import com.dlgdev.teachers.helpbook.models.StudentGroup;
import com.dlgdev.teachers.helpbook.models.Subject;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.views.TitledRecyclerCardWithAddButton;

import org.joda.time.DateTime;

/**
 * This Fragment should offer the chance to add new subjects, view the current subjects, handle
 * the bank holiday, course start date, course end date, student groups for the course, etc
 */
public class CourseAdministrationFragment extends Fragment {
	CourseAdministrationActionListener listener;
	TextView courseNameView;
	TextView courseDescriptionView;
	TextView datesView;
	TitledRecyclerCardWithAddButton<Holiday> holidaysView;
	TitledRecyclerCardWithAddButton<Subject> subjectsView;
	TitledRecyclerCardWithAddButton<Event> eventsView;

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
		courseNameView = (TextView) v.findViewById(R.id.course_administration_name);
		courseDescriptionView = (TextView) v.findViewById(R.id.course_administration_description);
		datesView = (TextView) v.findViewById(R.id.course_administration_dates);
		holidaysView = (TitledRecyclerCardWithAddButton<Holiday>) v
				.findViewById(R.id.course_administration_holidays);
		subjectsView = (TitledRecyclerCardWithAddButton<Subject>) v
				.findViewById(R.id.course_administration_subjects);
		eventsView = (TitledRecyclerCardWithAddButton<Event>) v
				.findViewById(R.id.course_administration_events);
		holidaysView.setup(null, getString(R.string.holidays), R.layout.event_view_for_list);
		subjectsView.setup(null, "", R.layout.event_view_for_list);
		eventsView.setup(null, "", R.layout.event_view_for_list);
	}

	public void course(Course course) {
		this.course = course;
		courseNameView.setText(course.title);
		courseDescriptionView.setText(course.description);
		datesView.setText(Dates.formatDateRange(course.start, course.end));
		holidaysView.updateItems(course.holidays());
		subjectsView.updateItems(course.subjects());
		eventsView.updateItems(course.events());
	}

	public interface CourseAdministrationActionListener {
		void onNewSubjectRequested();

		void onSubjectSelected(Subject subject);

		void onNewBankHolidayRequested();

		void onBankHolidaySelected(DateTime date);

		void onNewGroupRequested();

		void onGroupSelected(StudentGroup group);
	}
}

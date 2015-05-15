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
import com.dlgdev.teachers.helpbook.models.Listable;
import com.dlgdev.teachers.helpbook.models.StudentGroup;
import com.dlgdev.teachers.helpbook.models.Subject;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.views.TitledRecyclerCardWithAddButton;
import com.dlgdev.teachers.helpbook.views.TitledRecyclerCardWithAddButton.RecyclerCardListener;

/**
 * This Fragment should offer the chance to add new subjects, view the current subjects, handle
 * the bank holiday, course start date, course end date, student groups for the course, etc
 */
public class CourseAdministrationFragment extends Fragment {
	CourseAdministrationActionListener listener;
	TextView courseNameView;
	TextView courseDescriptionView;
	TextView datesView;
	TitledRecyclerCardWithAddButton holidaysView;
	TitledRecyclerCardWithAddButton subjectsView;
	TitledRecyclerCardWithAddButton eventsView;

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
		holidaysView = (TitledRecyclerCardWithAddButton) v
				.findViewById(R.id.course_administration_holidays);
		subjectsView = (TitledRecyclerCardWithAddButton) v
				.findViewById(R.id.course_administration_subjects);
		eventsView = (TitledRecyclerCardWithAddButton) v
				.findViewById(R.id.course_administration_events);
		holidaysView.setup(getString(R.string.holidays), R.layout.list_item,
				new RecyclerCardListener() {
					@Override public void onNewItemRequested() {
						listener.onNewBankHolidayRequested();
					}

					@Override public <T extends Listable> void onItemSelected(T t) {
						listener.onBankHolidaySelected((Holiday) t);
					}
				});
		subjectsView.setup(getString(R.string.subjects), R.layout.list_item,
				new RecyclerCardListener() {
					@Override public void onNewItemRequested() {
						listener.onNewSubjectRequested();
					}

					@Override public <T extends Listable> void onItemSelected(T t) {
						listener.onSubjectSelected((Subject) t);
					}
				});
		eventsView.setup(getString(R.string.events), R.layout.list_item,
				new RecyclerCardListener() {
					@Override public void onNewItemRequested() {
						listener.onNewEventRequested();
					}

					@Override public <T extends Listable> void onItemSelected(T t) {
						listener.onEventSelected((Event) t);
					}
				});
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

		void onBankHolidaySelected(Holiday holiday);

		void onNewGroupRequested();

		void onGroupSelected(StudentGroup group);

		void onNewEventRequested();

		void onEventSelected(Event event);
	}
}

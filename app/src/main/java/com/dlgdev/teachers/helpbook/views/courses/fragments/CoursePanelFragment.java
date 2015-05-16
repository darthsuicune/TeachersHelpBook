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
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;

import java.util.List;

public class CoursePanelFragment extends Fragment {
	DateTime currentDate;
	DateTime referenceDate;
	CoursePanelListener listener;
	TextView currentWeek;
	TextView referenceWeek;
	TextView eventCounter;
	List<Event> eventList;
	Course course;

	public CoursePanelFragment() {
	}

	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (CoursePanelListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activities holding a CoursePanelFragment must implement CoursePanelListener");
		}
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
									   @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.course_panel_fragment, container, false);
		prepareViews(v);
		return v;
	}

	private void prepareViews(View v) {
		v.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onPanelTapped();
			}
		});
		currentWeek = (TextView) v.findViewById(R.id.current_date);
		referenceWeek = (TextView) v.findViewById(R.id.reference_week);
		eventCounter = (TextView) v.findViewById(R.id.event_counter);
	}

	public void updateDate(DateTime date) {
		showCurrentDate(date);
		this.referenceDate = date;
		DateTime startOfWeek = Dates.startOfWeek(date);
		DateTime endOfWeek = Dates.endOfWeek(date);
		if (referenceWeek != null) {
			referenceWeek.setText(Dates.formatDateRange(startOfWeek, endOfWeek));
		}
	}

	private void showCurrentDate(DateTime date) {
		if (currentDate == null) {
			currentDate = date;
			currentWeek.setText(Dates.formatDate(currentDate));
		}
	}

	public void eventList(List<Event> eventList) {
		this.eventList = eventList;
		eventCounter.setText(getString(R.string.event_count, eventList.size()));
	}

	public void updateCourse(Course course) {
		this.course = course;
		if(course.getId() != null) {
			eventList(course.events());
		}
	}

	public interface CoursePanelListener {
		void onPanelTapped();
	}
}

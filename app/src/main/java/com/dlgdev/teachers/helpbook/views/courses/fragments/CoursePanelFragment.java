package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;

import java.util.List;

public class CoursePanelFragment extends WeeklyEventsFragment {
	CoursePanelListener listener;
	DateTime currentDate;
	TextView currentWeek;
	TextView referenceWeek;
	TextView eventCounter;
	List<Event> eventList;

	public CoursePanelFragment() {
	}

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		listener = (CoursePanelListener) context;
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
									   @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_course_panel, container, false);
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

	@Override protected void onDateUpdated() {
		showCurrentDate();
		if (referenceWeek != null) {
			referenceWeek.setText(Dates.formatDateRange(startOfWeek, endOfWeek));
		}

	}

	private void showCurrentDate() {
		if (currentDate == null) {
			currentDate = DateTime.now();
			currentWeek.setText(Dates.formatDate(currentDate));
		}
	}

	@Override void loadEvents() {
		if(course != null) {
			eventList(course.events());
		}
	}

	public void eventList(List<Event> eventList) {
		this.eventList = eventList;
		eventCounter.setText(getString(R.string.event_count, eventList.size()));
	}

	public interface CoursePanelListener {
		void onPanelTapped();
	}
}

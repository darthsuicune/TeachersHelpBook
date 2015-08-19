package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;

public class WeeklyEventsPreviewFragment extends WeeklyEventsFragment {
	WeeklyPreviewListener previewListener;
	TextView dateView;

	public WeeklyEventsPreviewFragment() {
	}

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		previewListener = (WeeklyPreviewListener) context;
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
									   @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_weekly_events_preview, container, false);
		preparePreviewLayout(v);
		return v;
	}

	private void preparePreviewLayout(View v) {
		dateView = (TextView) v.findViewById(R.id.weekly_events_preview_text);
		dateView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				previewListener.onPreviewSelected(referenceDate);
			}
		});
	}

	@Override protected void onDateUpdated() {
		if (dateView != null) {
			dateView.setText(Dates.formatDateRange(startOfWeek, endOfWeek));
		}
	}

	@Override void loadEvents() {
		if (course != null) {
			this.eventList = course.eventsBetween(startOfWeek, endOfWeek);
		}
	}

	public interface WeeklyPreviewListener {
		void onPreviewSelected(DateTime referenceDate);
	}

}

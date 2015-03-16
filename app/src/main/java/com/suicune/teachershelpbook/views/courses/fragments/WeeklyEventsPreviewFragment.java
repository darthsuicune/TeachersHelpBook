package com.suicune.teachershelpbook.views.courses.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.utils.Dates;

public class WeeklyEventsPreviewFragment extends WeeklyEventsFragment {
	TextView dateView;

	public WeeklyEventsPreviewFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.weekly_events_preview_fragment, container, false);
		preparePreviewLayout(v);
		return v;
	}

	private void preparePreviewLayout(View v) {
		dateView = (TextView) v.findViewById(R.id.weekly_events_preview_text);
		dateView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				previewListener.onPreviewTapped(referenceDate);
			}
		});
	}

	@Override protected void onDateUpdated() {
		if(dateView != null) {
			dateView.setText(Dates.formatDateRange(startOfWeek, endOfWeek));
		}
	}

	@Override void onEventListUpdated() {

	}

}

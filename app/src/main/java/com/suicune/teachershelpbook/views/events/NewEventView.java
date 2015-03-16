package com.suicune.teachershelpbook.views.events;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.InvalidTimeException;
import com.suicune.teachershelpbook.utils.Dates;

import org.joda.time.DateTime;

public class NewEventView extends LinearLayout {

	EditText titleView;
	EditText descriptionView;
	TextView startDateView;
	TextView endDateView;
	TextView startTimeView;
	TextView endTimeView;
	CheckBox fullDayCheckBox;

	DateTime start;
	DateTime end;
	Event event;
	OnPickersRequestedListener listener;

	public NewEventView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	public void setup(Event event, OnPickersRequestedListener listener) {
		this.event = event;
		this.start = event.start();
		this.end = event.end();
		this.listener = listener;

		loadViews();
		prepareViews();
	}

	private void loadViews() {
		titleView = (EditText) findViewById(R.id.title);
		descriptionView = (EditText) findViewById(R.id.description);
		startTimeView = (TextView) findViewById(R.id.start_time);
		endTimeView = (TextView) findViewById(R.id.end_time);
		fullDayCheckBox = (CheckBox) findViewById(R.id.full_day_checkbox);
		startDateView = (TextView) findViewById(R.id.start_date);
		endDateView = (TextView) findViewById(R.id.end_date);
	}

	private void prepareViews() {
		startTimeView.setText(Dates.formatTime(start));
		endTimeView.setText(Dates.formatTime(end));
		startDateView.setText(Dates.formatDate(start));
		endDateView.setText(Dates.formatDate(end));

		startTimeView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				listener.onStartTimeRequested();
			}
		});
		endTimeView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				listener.onEndTimeRequested();
			}
		});
		startDateView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				listener.onStartDateRequested();
			}
		});
		endDateView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				listener.onEndDateRequested();
			}
		});

		fullDayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override public void onCheckedChanged(CompoundButton button, boolean checked) {
				onFullDayChanged(checked);
			}
		});
	}

	public void changeStartTime(DateTime newTime) {
		event.start(newTime);
		startTimeView.setText(Dates.formatTime(newTime));
	}

	public void changeEndTime(DateTime newTime) {
		event.end(newTime);
		endTimeView.setText(Dates.formatTime(newTime));
	}

	public void onFullDayChanged(boolean isFullDay) {
		endDateView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		endTimeView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		if (isFullDay) {
			changeStartTime(start.withTime(0, 0, 0, 0));
			changeEndTime(end.withTime(23, 59, 59, 999));
		} else {
			changeStartTime(start);
			changeEndTime(end);
		}
	}

	public void start(int year, int month, int day) {
		try {
			event.start(start.withYear(year).withMonthOfYear(month).withDayOfMonth(day));
			startDateView.setText(Dates.formatDate(event.start()));
		} catch (InvalidTimeException e) {
			Toast.makeText(getContext(), R.string.invalid_start_date, Toast.LENGTH_LONG).show();
		}
	}

	public void end(int year, int month, int day) {
		try {
			event.end(end.withYear(year).withMonthOfYear(month).withDayOfMonth(day));
			endDateView.setText(Dates.formatDate(event.end()));
		} catch (InvalidTimeException e) {
			Toast.makeText(getContext(), R.string.invalid_end_date, Toast.LENGTH_LONG).show();
		}
	}

	public interface OnPickersRequestedListener {
		void onStartDateRequested();

		void onEndDateRequested();

		void onStartTimeRequested();

		void onEndTimeRequested();
	}
}

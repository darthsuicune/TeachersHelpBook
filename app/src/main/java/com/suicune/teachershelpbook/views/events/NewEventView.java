package com.suicune.teachershelpbook.views.events;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.utils.Dates;

import org.joda.time.DateTime;

import static com.suicune.teachershelpbook.views.TimePickerDialog.TimePickerListener;

public class NewEventView extends LinearLayout implements TimePickerListener {

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

	public NewEventView(Context context) {
		super(context);
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

		startTimeView.setOnClickListener(requestTimePicker(R.string.start_time_title));
		endTimeView.setOnClickListener(requestTimePicker(R.string.start_time_title));
		startDateView.setOnClickListener(requestDatePicker(R.string.start_date_title));
		endDateView.setOnClickListener(requestDatePicker(R.string.end_date_title));
		startDateView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {

			}
		});

		fullDayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override public void onCheckedChanged(CompoundButton button, boolean checked) {
				startTimeView.setVisibility((checked) ? View.GONE : View.VISIBLE);
				endTimeView.setVisibility((checked) ? View.GONE : View.VISIBLE);
				onFullDayChanged(checked);
			}
		});
	}

	private OnClickListener requestDatePicker(int title) {
		return new OnClickListener() {
			@Override public void onClick(View view) {

			}
		};
	}

	private OnClickListener requestTimePicker(int title) {
		return new OnClickListener() {
			@Override public void onClick(View view) {

			}
		};
	}

	public void setup(Event event) {
		this.event = event;
		this.start = event.start();
		this.end = event.end();
	}

	public void onStartTimeChanged(DateTime newTime) {
		event.start(start, newTime);
		startTimeView.setText(Dates.formatTime(newTime));
	}

	public void onEndTimeChanged(DateTime newTime) {
		event.end(end, newTime);
		endTimeView.setText(Dates.formatTime(newTime));
	}

	public void onStartDateChanged(DateTime newDate) {
		event.startDate(start);
		startDateView.setText(Dates.formatDate(newDate));
	}

	public void onEndDateChanged(DateTime newDate) {
		event.endDate(end);
		endDateView.setText(Dates.formatDate(newDate));
	}

	public void onFullDayChanged(boolean isFullDay) {
		if(isFullDay) {
			onStartTimeChanged(start.withTime(0, 0, 0, 0));
			onEndTimeChanged(end.withTime(23, 59, 59, 999));
		}
	}

	@Override public void onNewTimePicked(DateTime time) {

	}
}

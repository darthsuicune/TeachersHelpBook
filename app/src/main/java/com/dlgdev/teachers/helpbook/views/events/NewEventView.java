package com.dlgdev.teachers.helpbook.views.events;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.model.events.Event;
import com.dlgdev.teachers.helpbook.model.events.InvalidDateTimeException;
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;

public class NewEventView extends LinearLayout {

	ImageView startDateIconView;
	ImageView endDateIconView;
	ImageView startTimeIconView;
	ImageView endTimeIconView;
	EditText titleView;
	EditText descriptionView;
	EditText startDateView;
	EditText endDateView;
	EditText startTimeView;
	EditText endTimeView;
	CheckBox fullDayCheckBox;

	Event event;
	OnPickersRequestedListener listener;

	public NewEventView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	public void setup(Event event, OnPickersRequestedListener listener) {
		this.event = event;
		this.listener = listener;

		loadViews();
		prepareViews();
	}

	private void loadViews() {
		titleView = (EditText) findViewById(R.id.create_event_dialog_title);
		descriptionView = (EditText) findViewById(R.id.create_event_dialog_description);
		startDateView = (EditText) findViewById(R.id.create_event_dialog_start_date);
		endDateView = (EditText) findViewById(R.id.create_event_dialog_end_date);
		startTimeView = (EditText) findViewById(R.id.create_event_dialog_start_time);
		endTimeView = (EditText) findViewById(R.id.create_event_dialog_end_time);
		fullDayCheckBox = (CheckBox) findViewById(R.id.create_event_dialog_full_day_checkbox);
		startDateIconView = (ImageView) findViewById(R.id.create_event_dialog_start_date_icon);
		endDateIconView = (ImageView) findViewById(R.id.create_event_dialog_end_date_icon);
		startTimeIconView = (ImageView) findViewById(R.id.create_event_dialog_start_time_icon);
		endTimeIconView = (ImageView) findViewById(R.id.create_event_dialog_end_time_icon);
	}

	private void prepareViews() {
		startTimeView.setText(Dates.formatTime(event.start()));
		endTimeView.setText(Dates.formatTime(event.end()));
		startDateView.setText(Dates.formatDate(event.start()));
		endDateView.setText(Dates.formatDate(event.end()));

		startDateView.addTextChangedListener(new EventTextWatcher() {
			@Override public void afterTextChanged(Editable editable) {
				onStartDateTextChanged(editable);
			}
		});
		endDateView.addTextChangedListener(new EventTextWatcher() {
			@Override public void afterTextChanged(Editable editable) {
				onEndDateTextChanged(editable);
			}
		});
		startTimeView.addTextChangedListener(new EventTextWatcher() {
			@Override public void afterTextChanged(Editable editable) {
				onStartTimeTextChanged(editable);
			}
		});
		endTimeView.addTextChangedListener(new EventTextWatcher() {
			@Override public void afterTextChanged(Editable editable) {
				onEndTimeTextChanged(editable);
			}
		});

		startTimeIconView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				listener.onStartTimeRequested();
			}
		});
		endTimeIconView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				listener.onEndTimeRequested();
			}
		});
		startDateIconView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				listener.onStartDateRequested();
			}
		});
		endDateIconView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				listener.onEndDateRequested();
			}
		});

		fullDayCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override public void onCheckedChanged(CompoundButton button, boolean checked) {
				onFullDayChanged(checked);
			}
		});
	}

	private void onStartDateTextChanged(Editable editable) {
		try {
			DateTime date =
					Dates.parseDate(editable.toString()).withTime(event.start().toLocalTime());
			event.start(date);
			startDateView.setError(null);
		} catch (InvalidDateTimeException e) {
			startDateView.setError(getContext().getString(R.string.error_invalid_start_date));
		}
	}

	private void onEndDateTextChanged(Editable editable) {
		try {
			DateTime date =
					Dates.parseDate(editable.toString()).withTime(event.end().toLocalTime());
			event.end(date);
			endDateView.setError(null);
		} catch (InvalidDateTimeException e) {
			endDateView.setError(getContext().getString(R.string.error_invalid_end_date));
		}
	}

	private void onStartTimeTextChanged(Editable editable) {
		try {
			DateTime time =
					Dates.parseTime(editable.toString()).withDate(event.start().toLocalDate());
			event.start(time);
			startTimeView.setError(null);
		} catch (InvalidDateTimeException e) {
			startTimeView.setError(getContext().getString(R.string.error_invalid_start_time));
		}
	}

	private void onEndTimeTextChanged(Editable editable) {
		try {
			DateTime time =
					Dates.parseTime(editable.toString()).withDate(event.start().toLocalDate());
			event.end(time);
			endTimeView.setError(null);
		} catch (InvalidDateTimeException e) {
			endTimeView.setError(getContext().getString(R.string.error_invalid_end_time));
		}
	}

	private void onFullDayChanged(boolean isFullDay) {
		startTimeView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		endDateView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		endTimeView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		startTimeIconView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		endDateIconView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		endTimeIconView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		if (isFullDay) {
			changeStartTime(event.start().withTime(0, 0, 0, 0));
			changeEndTime(event.end().withTime(23, 59, 59, 999));
		}
	}

	void changeStartDate(DateTime newDate) {
		startDateView.setText(Dates.formatDate(newDate));
	}

	void changeEndDate(DateTime newDate) {
		endDateView.setText(Dates.formatDate(newDate));
	}

	void changeStartTime(DateTime newTime) {
		startTimeView.setText(Dates.formatTime(newTime));
	}

	void changeEndTime(DateTime newTime) {
		endTimeView.setText(Dates.formatTime(newTime));
	}

	/**
	 * Created exclusively for convenience to cut the 2 useless calls from everywhere I have
	 * to add it. I haven't been able to figure out how to properly create a generic one.
	 */
	private abstract class EventTextWatcher implements TextWatcher {
		@Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}
	}
}

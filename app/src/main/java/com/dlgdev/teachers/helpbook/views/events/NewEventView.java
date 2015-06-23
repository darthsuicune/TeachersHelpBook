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
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.utils.InvalidDateTimeException;
import com.dlgdev.views.DateView;

import org.joda.time.DateTime;

public class NewEventView extends LinearLayout {

	EditText titleView;
	EditText descriptionView;
	DateView startDateView;
	DateView endDateView;
	ImageView startTimeIconView;
	ImageView endTimeIconView;
	EditText startTimeView;
	EditText endTimeView;
	CheckBox fullDayCheckBox;
	DateTime oldStart;
	DateTime oldEnd;

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
		startDateView = (DateView) findViewById(R.id.create_event_dialog_start_date);
		endDateView = (DateView) findViewById(R.id.create_event_dialog_end_date);
		startTimeView = (EditText) findViewById(R.id.create_event_dialog_start_time);
		endTimeView = (EditText) findViewById(R.id.create_event_dialog_end_time);
		fullDayCheckBox = (CheckBox) findViewById(R.id.create_event_dialog_full_day_checkbox);
		startTimeIconView = (ImageView) findViewById(R.id.create_event_dialog_start_time_icon);
		endTimeIconView = (ImageView) findViewById(R.id.create_event_dialog_end_time_icon);
	}

	private void prepareViews() {
		startTimeView.setText(Dates.formatTime(event.start()));
		endTimeView.setText(Dates.formatTime(event.end()));
		startDateView.setup(event.start(), new DateView.OnDatePickerRequestedListener() {
			@Override public void onDatePickerRequested(int viewId) {
				listener.onStartDateRequested();
			}
		});
		endDateView.setup(event.end(), new DateView.OnDatePickerRequestedListener() {
			@Override public void onDatePickerRequested(int viewId) {
				listener.onEndDateRequested();
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

		fullDayCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override public void onCheckedChanged(CompoundButton button, boolean checked) {
				onFullDayChanged(checked);
			}
		});
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
		endTimeIconView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		if (isFullDay) {
			oldStart = event.start();
			oldEnd = event.end();
			changeStartTime(event.start().withTimeAtStartOfDay());
			changeEndTime(event.end().withTime(23, 59, 59, 999));
		} else {
			changeStartTime(oldStart);
			changeEndTime(oldEnd);
		}
	}

	public void changeStartDate(DateTime newDate) {
		startDateView.setDate(newDate);
	}

	public void changeEndDate(DateTime newDate) {
		endDateView.setDate(newDate);
	}

	public void changeStartTime(DateTime newTime) {
		startTimeView.setText(Dates.formatTime(newTime));
	}

	public void changeEndTime(DateTime newTime) {
		endTimeView.setText(Dates.formatTime(newTime));
	}

	public String getTitle() {
		return titleView.getText().toString();
	}

	public String getDescription() {
		return descriptionView.getText().toString();
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

package com.dlgdev.teachers.helpbook.views.events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.views.DateView;
import com.dlgdev.views.TimeView;
import com.dlgdev.views.fragments.DatePickerFragment;
import com.dlgdev.views.fragments.TimePickerFragment;

import org.joda.time.DateTime;

public class NewEventView extends RelativeLayout {

	private static final String TAG_START_DATE_PICKER = "start date picker";
	private static final String TAG_END_DATE_PICKER = "end date picker";
	private static final String TAG_START_TIME_PICKER = "start time picker";
	private static final String TAG_END_TIME_PICKER = "end time picker";
	EditText titleView;
	EditText descriptionView;
	DateView startDateView;
	DateView endDateView;
	TimeView startTimeView;
	TimeView endTimeView;
	CheckBox fullDayCheckBox;
	DateTime start;
	DateTime end;
	FragmentManager manager;

	public NewEventView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	public void setup(FragmentManager manager, DateTime start, DateTime end) {
		this.manager = manager;
		this.start = start;
		this.end = end;
		loadViews();
		prepareViews();
	}

	private void loadViews() {
		titleView = (EditText) findViewById(R.id.create_event_dialog_title);
		descriptionView = (EditText) findViewById(R.id.create_event_dialog_description);
		startDateView = (DateView) findViewById(R.id.create_event_dialog_start_date);
		endDateView = (DateView) findViewById(R.id.create_event_dialog_end_date);
		startTimeView = (TimeView) findViewById(R.id.create_event_dialog_start_time);
		endTimeView = (TimeView) findViewById(R.id.create_event_dialog_end_time);
		fullDayCheckBox = (CheckBox) findViewById(R.id.create_event_dialog_full_day_checkbox);
	}

	private void prepareViews() {
		startTimeView.setText(Dates.formatTime(start));
		endTimeView.setText(Dates.formatTime(end));
		startDateView.setup(start, new DateView.OnDatePickerRequestedListener() {
			@Override public void requestDatePicker() {
				requestStartDatePicker();
			}
		});
		endDateView.setup(end, new DateView.OnDatePickerRequestedListener() {
			@Override public void requestDatePicker() {
				requestEndDatePicker();
			}
		});

		startTimeView.setup(start, new TimeView.OnTimePickerRequestedListener() {
			@Override public void requestTimePicker() {
				requestStartTimePicker();
			}
		});
		endTimeView.setup(end, new TimeView.OnTimePickerRequestedListener() {
			@Override public void requestTimePicker() {
				requestEndTimePicker();
			}
		});

		fullDayCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override public void onCheckedChanged(CompoundButton button, boolean checked) {
				onFullDayChanged(checked);
			}
		});

		titleView.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override public void afterTextChanged(Editable editable) {
				setTitleError(editable);
			}
		});
	}

	private void requestStartDatePicker() {
		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setDialog(
				new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker datePicker, int year, int month, int day) {
						changeStartDate(year, month, day);
					}
				}, start.getYear(), start.getMonthOfYear() - 1, start.getDayOfMonth()));
		fragment.show(manager, TAG_START_DATE_PICKER);
	}

	private void requestEndDatePicker() {
		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setDialog(
				new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker datePicker, int year, int month, int day) {
						changeEndDate(year, month, day);
					}
				}, end.getYear(), end.getMonthOfYear() - 1, end.getDayOfMonth()));
		fragment.show(manager, TAG_END_DATE_PICKER);
	}

	private void requestStartTimePicker() {
		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setDialog(
				new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
					@Override public void onTimeSet(TimePicker timePicker, int hour, int minute) {
						changeStartTime(hour, minute);
					}
				}, start.getHourOfDay(), start.getMinuteOfHour(),
						DateFormat.is24HourFormat(getContext())));
		fragment.show(manager, TAG_START_TIME_PICKER);
	}

	private void requestEndTimePicker() {
		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setDialog(
				new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
					@Override public void onTimeSet(TimePicker timePicker, int hour, int minute) {
						changeEndTime(hour, minute);
					}
				}, end.getHourOfDay(), end.getMinuteOfHour(),
						DateFormat.is24HourFormat(getContext())));
		fragment.show(manager, TAG_END_TIME_PICKER);

	}

	private void onFullDayChanged(boolean isFullDay) {
		startTimeView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		endTimeView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		if (isFullDay) {
			changeStartTime(start.withTimeAtStartOfDay());
			changeEndTime(end.withTime(23, 59, 59, 999));
		} else {
			changeStartTime(start);
			changeEndTime(end);
		}
	}

	public void changeStartDate(int year, int month, int day) {
		//F...ing month will come back in a 0..11 range instead of 1..12
		changeStartDate(start.withYear(year).withMonthOfYear(month + 1).withDayOfMonth(day));
	}

	public void changeStartDate(DateTime date) {
		start = date;
		startDateView.setDate(date);
		setDatesError();
	}

	public void changeEndDate(int year, int month, int day) {
		//F...ing month will come back in a 0..11 range instead of 1..12s
		changeEndDate(end.withYear(year).withMonthOfYear(month + 1).withDayOfMonth(day));
	}

	public void changeEndDate(DateTime date) {
		end = date;
		endDateView.setDate(date);
		setDatesError();
	}

	public void changeStartTime(int hour, int minute) {
		changeStartTime(start.withHourOfDay(hour).withMinuteOfHour(minute));
	}

	public void changeStartTime(DateTime time) {
		start = time;
		startTimeView.setTime(time);
		setDatesError();
	}

	public void changeEndTime(int hour, int minute) {
		changeEndTime(end.withHourOfDay(hour).withMinuteOfHour(minute));
	}

	public void changeEndTime(DateTime time) {
		end = time;
		endTimeView.setTime(time);
		setDatesError();
	}

	public String getTitle() {
		return titleView.getText().toString();
	}

	public String getDescription() {
		return descriptionView.getText().toString();
	}

	public DateTime getStart() {
		return startDateView.getDate().withTime(startTimeView.getTime().toLocalTime());
	}

	public DateTime getEnd() {
		return endDateView.getDate().withTime(endTimeView.getTime().toLocalTime());
	}

	public void setErrors() {
		setTitleError(titleView.getText());
		setDatesError();
	}

	private void setDatesError() {
		endDateView.setError((start.isAfter(end)) ?
				getContext().getString(R.string.error_start_cannot_be_after_end) : null);
	}

	private void setTitleError(CharSequence text) {
		titleView.setError((TextUtils.isEmpty(text)) ?
				getContext().getString(R.string.error_title_is_required) : null);
	}
}

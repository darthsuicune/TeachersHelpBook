package com.suicune.teachershelpbook.views.events;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.InvalidDateTimeException;
import com.suicune.teachershelpbook.utils.Dates;

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
		titleView = (EditText) findViewById(R.id.title);
		descriptionView = (EditText) findViewById(R.id.description);
		startDateView = (EditText) findViewById(R.id.start_date);
		endDateView = (EditText) findViewById(R.id.end_date);
		startTimeView = (EditText) findViewById(R.id.start_time);
		endTimeView = (EditText) findViewById(R.id.end_time);
		fullDayCheckBox = (CheckBox) findViewById(R.id.full_day_checkbox);
		startDateIconView = (ImageView) findViewById(R.id.start_date_icon);
		endDateIconView = (ImageView) findViewById(R.id.end_date_icon);
		startTimeIconView = (ImageView) findViewById(R.id.start_time_icon);
		endTimeIconView = (ImageView) findViewById(R.id.end_time_icon);
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

		fullDayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
		} catch (InvalidDateTimeException e) {
			startDateView.setError(getContext().getString(R.string.error_invalid_start_date));
		}
	}

	void changeStartDate(DateTime newDate) {
		try {
			event.start(newDate);
			startDateView.setText(Dates.formatDate(newDate));
		} catch (InvalidDateTimeException e) {
			Toast.makeText(getContext(), e.reason(), Toast.LENGTH_LONG).show();
			startDateView.setError(getContext().getString(R.string.error_invalid_start_date));
		}
	}

	private void onEndDateTextChanged(Editable editable) {
		try {
			DateTime date =
					Dates.parseDate(editable.toString()).withTime(event.end().toLocalTime());
			event.end(date);
		} catch (InvalidDateTimeException e) {
			endDateView.setError(getContext().getString(R.string.error_invalid_end_date));
		}
	}

	void changeEndDate(DateTime newDate) {
		try {
			event.end(newDate);
			endDateView.setText(Dates.formatDate(newDate));
		} catch (InvalidDateTimeException e) {
			endDateView.setError(getContext().getString(R.string.error_invalid_end_date));
		}

	}

	private void onStartTimeTextChanged(Editable editable) {
		try {
			DateTime time =
					Dates.parseTime(editable.toString()).withDate(event.start().toLocalDate());
			event.start(time);
		} catch (InvalidDateTimeException e) {

		}
	}

	void changeStartTime(DateTime newTime) {
		try {
			event.start(newTime);
			startTimeView.setText(Dates.formatTime(newTime));
		} catch (InvalidDateTimeException e) {
			Toast.makeText(getContext(), e.reason(), Toast.LENGTH_LONG).show();
		}
	}

	private void onEndTimeTextChanged(Editable editable) {
		try {
			DateTime time =
					Dates.parseTime(editable.toString()).withDate(event.start().toLocalDate());
			event.end(time);
		} catch (InvalidDateTimeException e) {

		}
	}

	void changeEndTime(DateTime newTime) {
		try {
			event.end(newTime);
			endTimeView.setText(Dates.formatTime(newTime));
		} catch (InvalidDateTimeException e) {
			Toast.makeText(getContext(), e.reason(), Toast.LENGTH_LONG).show();
		}
	}

	private void onFullDayChanged(boolean isFullDay) {
		startTimeView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		endDateView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		endTimeView.setVisibility((isFullDay) ? View.GONE : View.VISIBLE);
		if (isFullDay) {
			changeStartTime(event.start().withTime(0, 0, 0, 0));
			changeEndTime(event.end().withTime(23, 59, 59, 999));
		}
	}

	/**
	 * Users of this view are expected to implement this interface for communication of requests
	 */
	public interface OnPickersRequestedListener {
		void onStartDateRequested();

		void onEndDateRequested();

		void onStartTimeRequested();

		void onEndTimeRequested();
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

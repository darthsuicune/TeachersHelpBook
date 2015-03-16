package com.suicune.teachershelpbook.views.events;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.utils.Dates;

import org.joda.time.DateTime;

import static android.content.DialogInterface.OnClickListener;

public class NewEventDialog extends DialogFragment
		implements NewEventView.OnPickersRequestedListener {
	private static final String ARG_PARENT_ID = "parent id";
	private static final String ARG_EVENT = "event";
	private static final String START_DATE_DIALOG_TAG = "start date dialog";
	private static final String END_DATE_DIALOG_TAG = "end date dialog";
	private static final String START_TIME_DIALOG_TAG = "start time dialog";
	private static final String END_TIME_DIALOG_TAG = "end time dialog";


	NewEventDialogListener listener;
	int parentFragmentId;

	NewEventView newEventView;
	Event event;

	public NewEventDialog() {
	}

	public void setup(NewEventDialogListener listener, Event event, int parentId) {
		this.listener = listener;
		this.parentFragmentId = parentId;
		this.event = event;
	}

	@Override public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_PARENT_ID, parentFragmentId);
		outState.putSerializable(ARG_EVENT, event);
	}

	public void restoreFragment(Bundle savedInstanceState) {
		parentFragmentId = savedInstanceState.getInt(ARG_PARENT_ID);
		listener = (NewEventDialogListener) getFragmentManager().findFragmentById(parentFragmentId);
		event = (Event) savedInstanceState.getSerializable(ARG_EVENT);
	}

	@Override @NonNull public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			restoreFragment(savedInstanceState);
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String title = String.format("%s at %s", getString(R.string.create_event_dialog_title),
				Dates.formatDate(event.start()));
		builder.setTitle(title);
		builder.setView(getDialogView(R.layout.create_event_dialog));
		builder.setPositiveButton(R.string.create_event, new OnClickListener() {
			@Override public void onClick(DialogInterface dialogInterface, int which) {
				listener.onNewEventCreated(event);
			}
		});
		builder.setNegativeButton(android.R.string.cancel, new OnClickListener() {
			@Override public void onClick(DialogInterface dialogInterface, int which) {
				listener.onDialogCanceled();
			}
		});
		return builder.create();
	}

	private View getDialogView(int layout) {
		newEventView = (NewEventView) getActivity().getLayoutInflater().inflate(layout, null);
		newEventView.setup(event, this);
		return newEventView;
	}

	@Override public void onStartDateRequested() {
		final DateTime start = event.start();
		DatePickerDialog dialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {
			@Override public void onDateSet(DatePicker datePicker, int year, int month, int day) {
				newEventView.start(year, month, day);
			}
		}, start.getYear(), start.getMonthOfYear(), start.getDayOfMonth());
		launchDateTimeDialog(dialog, START_DATE_DIALOG_TAG);
	}

	@Override public void onEndDateRequested() {
		final DateTime end = event.end();
		DatePickerDialog dialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {
			@Override public void onDateSet(DatePicker datePicker, int year, int month, int day) {
				newEventView.end(year, month, day);
			}
		}, end.getYear(), end.getMonthOfYear(), end.getDayOfMonth());
		launchDateTimeDialog(dialog, END_DATE_DIALOG_TAG);
	}

	@Override public void onStartTimeRequested() {
		final DateTime start = event.start();
		TimePickerDialog dialog = new TimePickerDialog(getActivity(), new OnTimeSetListener() {
			@Override public void onTimeSet(TimePicker timePicker, int hour, int minute) {
				event.start(start.withHourOfDay(hour).withMinuteOfHour(minute));
				newEventView.startTimeView.setText(Dates.formatTime(event.start()));
			}
		}, start.getHourOfDay(), start.getMinuteOfHour(), true);
		launchDateTimeDialog(dialog, START_TIME_DIALOG_TAG);
	}

	@Override public void onEndTimeRequested() {
		final DateTime end = event.end();
		TimePickerDialog dialog = new TimePickerDialog(getActivity(), new OnTimeSetListener() {
			@Override public void onTimeSet(TimePicker timePicker, int hour, int minute) {
				event.end(end.withHourOfDay(hour).withMinuteOfHour(minute));
				newEventView.endTimeView.setText(Dates.formatTime(event.end()));
			}
		}, end.getHourOfDay(), end.getMinuteOfHour(), true);
		launchDateTimeDialog(dialog, END_TIME_DIALOG_TAG);
	}

	void launchDateTimeDialog(final Dialog dialog, String tag) {
		DialogFragment fragment = new DialogFragment(){
			@Override @NonNull public Dialog onCreateDialog(Bundle savedInstanceState) {
				return dialog;
			}
		};
		fragment.show(getFragmentManager(), tag);
	}

	public interface NewEventDialogListener {
		void onNewEventCreated(Event event);

		void onDialogCanceled();
	}
}

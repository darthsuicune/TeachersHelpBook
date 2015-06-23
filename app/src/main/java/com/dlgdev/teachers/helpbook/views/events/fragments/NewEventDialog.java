package com.dlgdev.teachers.helpbook.views.events.fragments;

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

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.views.events.NewEventView;
import com.dlgdev.teachers.helpbook.views.events.OnPickersRequestedListener;

import org.joda.time.DateTime;

public class NewEventDialog extends DialogFragment implements OnPickersRequestedListener {
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

	@Override @NonNull public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			restoreFragment(savedInstanceState);
		}
		return buildDialog(getDialogView(R.layout.create_event_dialog));
	}

	private void restoreFragment(Bundle savedInstanceState) {
		parentFragmentId = savedInstanceState.getInt(ARG_PARENT_ID);
		listener = (NewEventDialogListener) getFragmentManager().findFragmentById(parentFragmentId);
		event = (Event) savedInstanceState.getSerializable(ARG_EVENT);
	}

	private AlertDialog buildDialog(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(String.format("%s at %s", getString(R.string.create_event_dialog_title),
				Dates.formatDate(event.start())));
		builder.setView(view);
		builder.setPositiveButton(R.string.create_event, new DialogInterface.OnClickListener() {
			@Override public void onClick(DialogInterface dialogInterface, int which) {
				save();
			}
		});
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override public void onClick(DialogInterface dialogInterface, int which) {
				listener.onDialogCancelled();
			}
		});
		return builder.create();
	}

	private View getDialogView(int layout) {
		newEventView = (NewEventView) getActivity().getLayoutInflater().inflate(layout, null);
		newEventView.setup(event, this);
		return newEventView;
	}

	private void save() {
		event.title(newEventView.getTitle());
		event.description(newEventView.getDescription());
		listener.onNewEventCreated(event);
	}

	@Override public void onStartDateRequested() {
		final DateTime start = event.start();
		DatePickerDialog dialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {
			@Override public void onDateSet(DatePicker datePicker, int year, int month, int day) {
				newEventView.changeStartDate(start.withDate(year, month, day));
			}
		}, start.getYear(), start.getMonthOfYear(), start.getDayOfMonth());
		showDateTimeDialog(dialog, START_DATE_DIALOG_TAG);
	}

	@Override public void onEndDateRequested() {
		final DateTime end = event.end();
		DatePickerDialog dialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {
			@Override public void onDateSet(DatePicker datePicker, int year, int month, int day) {
				newEventView.changeEndDate(end.withDate(year, month, day));
			}
		}, end.getYear(), end.getMonthOfYear(), end.getDayOfMonth());
		showDateTimeDialog(dialog, END_DATE_DIALOG_TAG);
	}

	@Override public void onStartTimeRequested() {
		final DateTime start = event.start();
		TimePickerDialog dialog = new TimePickerDialog(getActivity(), new OnTimeSetListener() {
			@Override public void onTimeSet(TimePicker timePicker, int hour, int minute) {
				newEventView.changeStartTime(start.withHourOfDay(hour).withMinuteOfHour(minute));
			}
		}, start.getHourOfDay(), start.getMinuteOfHour(), true);
		showDateTimeDialog(dialog, START_TIME_DIALOG_TAG);
	}

	@Override public void onEndTimeRequested() {
		final DateTime end = event.end();
		TimePickerDialog dialog = new TimePickerDialog(getActivity(), new OnTimeSetListener() {
			@Override public void onTimeSet(TimePicker timePicker, int hour, int minute) {
				newEventView.changeEndTime(end.withHourOfDay(hour).withMinuteOfHour(minute));
			}
		}, end.getHourOfDay(), end.getMinuteOfHour(), true);
		showDateTimeDialog(dialog, END_TIME_DIALOG_TAG);
	}

	void showDateTimeDialog(final Dialog dialog, String tag) {
		DialogFragment fragment = new DialogFragment() {
			@Override @NonNull public Dialog onCreateDialog(Bundle savedInstanceState) {
				return dialog;
			}
		};
		fragment.show(getFragmentManager(), tag);
	}

	public interface NewEventDialogListener {
		void onNewEventCreated(Event event);

		void onDialogCancelled();
	}
}

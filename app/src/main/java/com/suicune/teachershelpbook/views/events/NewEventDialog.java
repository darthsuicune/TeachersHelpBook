package com.suicune.teachershelpbook.views.events;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.EventsProvider;
import com.suicune.teachershelpbook.utils.Dates;

import org.joda.time.DateTime;

import static android.content.DialogInterface.OnClickListener;

public class NewEventDialog extends DialogFragment {
	private static final String ARG_DATE = "date";
	private static final String ARG_LISTENER = "listener";
	private static final String ARG_PROVIDER = "provider";
	private static final String ARG_EVENT = "event";

	NewEventDialogListener listener;
	EventsProvider provider;
	DateTime date;
	int parentFragmentId;

	NewEventView newEventView;
	Event event;

	public NewEventDialog() {
	}

	public void setup(DateTime date, NewEventDialogListener listener, EventsProvider provider,
					  int parentFragmentId) {
		this.date = date;
		this.listener = listener;
		this.provider = provider;
		this.parentFragmentId = parentFragmentId;
		this.event = provider.createEmpty(date.withHourOfDay(DateTime.now().getHourOfDay()));
	}

	@Override public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(ARG_DATE, date);
		outState.putSerializable(ARG_PROVIDER, provider);
		outState.putInt(ARG_LISTENER, parentFragmentId);
		outState.putSerializable(ARG_EVENT, event);
	}

	public void restoreFragment(Bundle savedInstanceState) {
		date = new DateTime(savedInstanceState.getSerializable(ARG_DATE));
		provider = (EventsProvider) savedInstanceState.getSerializable(ARG_PROVIDER);
		parentFragmentId = savedInstanceState.getInt(ARG_LISTENER);
		listener = (NewEventDialogListener) getFragmentManager().findFragmentById(parentFragmentId);
		event = (Event) savedInstanceState.getSerializable(ARG_EVENT);
	}

	@Override @NonNull public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			restoreFragment(savedInstanceState);
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String title = String.format("%s at %s", getString(R.string.create_event_dialog_title),
				Dates.formatDate(date));
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
		newEventView.setup(event);
		return newEventView;
	}

	public interface NewEventDialogListener {
		void onNewEventCreated(Event event);

		void onDialogCanceled();
	}
}

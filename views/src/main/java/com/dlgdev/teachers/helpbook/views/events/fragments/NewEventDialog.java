package com.dlgdev.teachers.helpbook.views.events.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.domain.models.Event;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.views.ModelCreationDialogFragment;
import com.dlgdev.teachers.helpbook.views.events.NewEventView;

import ollie.query.Select;

public class NewEventDialog extends ModelCreationDialogFragment {

	NewEventView newEventView;
	Event event;
	NewEventDialogListener listener;

	public NewEventDialog() {
	}

	public void setup(NewEventDialogListener listener, Event event, int parentId) {
		super.setup(listener, parentId);
		this.listener = listener;
		this.event = event;
	}

	@Override public void restoreState(ModelCreationDialogListener listener, Long id) {
		this.listener = (NewEventDialogListener) listener;
		event = Select.from(Event.class).where("_id=?", id).fetchSingle();

	}

	@Override public AlertDialog buildDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(String.format("%s at %s", getString(R.string.create_event_dialog_title),
				Dates.formatDate(event.start())));
		builder.setView(getDialogView(R.layout.dialog_create_event));
		builder.setPositiveButton(R.string.create_event, new DialogInterface.OnClickListener() {
			@Override public void onClick(DialogInterface dialogInterface, int which) {
				saveAndGetId();
				listener.onNewEventCreated(event);
			}
		});
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override public void onClick(DialogInterface dialogInterface, int which) {
				if(event.id != null) {
					event.delete();
				}
				listener.onDialogCancelled();
			}
		});
		return builder.create();
	}

	private View getDialogView(int layout) {
		newEventView = (NewEventView) getActivity().getLayoutInflater().inflate(layout, null);
		newEventView.setup(getFragmentManager(), event.start(), event.end());
		return newEventView;
	}

	@Override public Long saveAndGetId() {
		event.title(newEventView.getTitle());
		event.description(newEventView.getDescription());
		return event.save();
	}

	public interface NewEventDialogListener extends ModelCreationDialogListener {
		void onNewEventCreated(Event event);
	}
}

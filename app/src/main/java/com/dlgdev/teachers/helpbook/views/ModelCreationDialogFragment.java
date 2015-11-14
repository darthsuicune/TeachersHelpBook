package com.dlgdev.teachers.helpbook.views;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public abstract class ModelCreationDialogFragment extends DialogFragment {
	private static final String ARG_PARENT_ID = "parent id";
	private static final String KEY_MODEL_ID = "model";
	public ModelCreationDialogListener listener;
	int parentId;

	public void setup(ModelCreationDialogListener listener, int parentId) {
		this.listener = listener;
		this.parentId = parentId;
	}

	@Override public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		save();
		outState.putInt(ARG_PARENT_ID, parentId);
		outState.putLong(KEY_MODEL_ID, modelId());
	}

	public abstract Long modelId();

	@Override @NonNull public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			restoreState(savedInstanceState);
		}
		return buildDialog();
	}

	private void restoreState(Bundle savedInstanceState) {
		parentId = savedInstanceState.getInt(ARG_PARENT_ID);
		listener = (ModelCreationDialogListener) getFragmentManager().findFragmentById(parentId);
		restoreModel(savedInstanceState.getLong(KEY_MODEL_ID));
	}

	public abstract void restoreModel(Long id);

	public abstract Dialog buildDialog();

	public abstract void save();

	public interface ModelCreationDialogListener {
		void onDialogCancelled();
	}
}

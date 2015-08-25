package com.dlgdev.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {
	Dialog dialog;

	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}

	@NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
		return dialog;
	}
}

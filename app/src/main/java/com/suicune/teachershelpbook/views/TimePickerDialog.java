package com.suicune.teachershelpbook.views;

import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import org.joda.time.DateTime;

/**
 * Created by lapuente on 13.03.15.
 */
public class TimePickerDialog extends DialogFragment {
	TimePicker picker;

	public interface TimePickerListener {
		void onNewTimePicked(DateTime time);
	}
}

package com.dlgdev.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;

public class TimeView extends TextView {
	DateTime time;
	public TimeView(Context context) {
		super(context);
	}

	public TimeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setup(DateTime time, final OnTimePickerRequestedListener listener) {
		this.time = time;
		this.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				listener.requestTimePicker();
			}
		});
	}

	public DateTime getTime() {
		return time;
	}

	public void setTime(DateTime time) {
		this.time = time;
		this.setText(Dates.formatTime(time));
	}

	public interface OnTimePickerRequestedListener {
		void requestTimePicker();
	}
}

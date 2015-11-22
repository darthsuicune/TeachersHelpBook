package com.dlgdev.views;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;

public class DateView extends TextView {

	OnDatePickerRequestedListener listener;
	DateTime date;

	public DateView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setup(DateTime initialDate, OnDatePickerRequestedListener listener) {
		this.listener = listener;
		prepareView();
		setDate(initialDate);
	}

	private void prepareView() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			setCompoundDrawables(null, null,
					getResources().getDrawable(android.R.drawable.ic_menu_my_calendar, null), null);
		} else {
			setCompoundDrawables(null, null,
					getResources().getDrawable(android.R.drawable.ic_menu_my_calendar), null);
		}
		setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				listener.requestDatePicker();
			}
		});
	}

	public void setDate(DateTime date) {
		this.date = date;
		setText(Dates.formatDate(date));
	}

	public DateTime getDate() {
		return date;
	}

	public interface OnDatePickerRequestedListener {
		void requestDatePicker();
	}
}

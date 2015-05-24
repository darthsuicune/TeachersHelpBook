package com.dlgdev.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;

public class DateView extends LinearLayout implements TextWatcher {

	OnDatePickerRequestedListener listener;
	DateTime date;
	ImageView iconView;
	EditText dateAsEditTextView;
	String dateFormat;

	public DateView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setup(DateTime initialDate, OnDatePickerRequestedListener listener) {
		this.listener = listener;
		prepareView();
		setDate(initialDate);
		dateAsEditTextView.addTextChangedListener(this);
	}

	private void prepareView() {
		dateAsEditTextView = (EditText) findViewById(R.id.date_text);
		iconView = (ImageView) findViewById(R.id.select_date_icon);
		iconView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				listener.onDatePickerRequested(DateView.this.getId());
			}
		});
	}

	public void setDate(DateTime date) {
		this.date = date;
		dateAsEditTextView.setText(Dates.formatDate(date));
	}

	public void setFormat(String format) {
		dateFormat = format;
	}

	public DateTime getDate() {
		return date;
	}

	@Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	}

	@Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	}

	@Override public void afterTextChanged(Editable editable) {
		date = Dates.parseDate(editable.toString());
	}

	public interface OnDatePickerRequestedListener {
		void onDatePickerRequested(int viewId);
	}
}

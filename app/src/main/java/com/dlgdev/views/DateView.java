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
import com.dlgdev.teachers.helpbook.utils.InvalidDateTimeException;

import org.joda.time.DateTime;

public class DateView extends LinearLayout implements TextWatcher {

	OnDatePickerRequestedListener listener;
	DateTime date;
	ImageView iconView;
	EditText dateAsTextView;
	String dateFormat;

	public DateView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setup(DateTime initialDate, OnDatePickerRequestedListener listener) {
		this.listener = listener;
		prepareView();
		setDate(initialDate);
		dateAsTextView.addTextChangedListener(this);
	}

	private void prepareView() {
		dateAsTextView = (EditText) findViewById(R.id.date_text);
		iconView = (ImageView) findViewById(R.id.select_date_icon);
		iconView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				listener.onDatePickerRequested(DateView.this.getId());
			}
		});
	}

	public void setDate(DateTime date) {
		this.date = date;
		dateAsTextView.setText(Dates.formatDate(date));
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
		try {
			date = Dates.parseDate(editable.toString());
			dateAsTextView.setError(null);
		} catch (InvalidDateTimeException e) {
			dateAsTextView.setError(getContext().getString(R.string.error_invalid_start_date));
		}
	}

	public interface OnDatePickerRequestedListener {
		void onDatePickerRequested(int viewId);
	}
}

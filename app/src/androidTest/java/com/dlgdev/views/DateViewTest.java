package com.dlgdev.views;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DateViewTest {
	private static final String VALID_DATE = "12/12/2012";
	private static final String INVALID_VALUE = "somethingsomething";

	DateView view;
	DateView.OnDatePickerRequestedListener listener;
	DateTime date;

	@Before public void setUp() throws Exception {
		listener = mock(DateView.OnDatePickerRequestedListener.class);

		view = (DateView) LayoutInflater.from(InstrumentationRegistry.getTargetContext())
				.inflate(R.layout.date_view, null);
		date = DateTime.now();
		view.setup(date, listener);
	}

	@Test public void afterLoadingTheView() {
		assertNotNull(view.dateAsTextView);
		assertNotNull(view.iconView);
		assertEquals(Dates.formatDate(date), view.dateAsTextView.getText().toString());
	}

	@Test public void theIconViewHasAnOnClickListenerAddedThatCallsItsCallback() {
		view.iconView.performClick();
		verify(listener).onDatePickerRequested(view.getId());
	}

	@Test public void theDateCanBeChangedByTheHolderOfTheView() throws Exception {
		DateTime newDate = DateTime.now().plusWeeks(1).withTimeAtStartOfDay();
		view.setDate(newDate);
		assertEquals(newDate, view.date);
		assertEquals(Dates.formatDate(newDate), view.dateAsTextView.getText().toString());
	}

	@Test public void testOnStartDateTextChangedWithValidDateDoesntDisplayError() throws Exception {
		whenWeWriteAValueInTheEditTextViews(view.dateAsTextView, VALID_DATE);
		itsParsedWithoutError(view.dateAsTextView);
	}

	private void whenWeWriteAValueInTheEditTextViews(EditText view, String value) {
		view.setText(value);
	}

	private void itsParsedWithoutError(EditText view) {
		assertTrue(TextUtils.isEmpty(view.getError()));
	}

	@Test public void testOnStartDateTextChangedWithValidDateUpdatesTheDate() throws Exception {
		whenWeWriteAValueInTheEditTextViews(view.dateAsTextView, VALID_DATE);
		itUpdatesTheDateTo(VALID_DATE);
	}

	private void itUpdatesTheDateTo(String validDate) {
		assertEquals(Dates.parseDate(validDate), view.date);
	}

	@Test public void testWhenAnInvalidEndDateIsInputAnErrorIsSet() throws Exception {
		whenWeWriteAValueInTheEditTextViews(view.dateAsTextView, INVALID_VALUE);
		theEditTextViewShowsAnError(view.dateAsTextView);
	}

	private void theEditTextViewShowsAnError(TextView v) {
		assertFalse(TextUtils.isEmpty(v.getError()));
	}
}
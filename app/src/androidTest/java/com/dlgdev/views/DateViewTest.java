package com.dlgdev.views;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
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

		view = new DateView(InstrumentationRegistry.getTargetContext(), null);
		date = DateTime.now();
		view.setup(date, listener);
	}

	@Test public void settingTheDateSetsTheDate() throws Exception {
		view.setDate(date);
		assertEquals(view.getDate(), date);
	}

	@Test public void settingTheDateSetsATextWithTheFormattedText() throws Exception {
		view.setDate(date);
		assertEquals(view.getText(), Dates.formatDate(date));
	}

	@Test public void theViewHasAnOnClickListenerAddedThatCallsItsCallback() {
		view.performClick();
		verify(listener).requestDatePicker();
	}

	@Test public void theDateCanBeChangedByTheHolderOfTheView() throws Exception {
		DateTime newDate = DateTime.now().plusWeeks(1).withTimeAtStartOfDay();
		view.setDate(newDate);
		assertEquals(newDate, view.date);
	}
}
package com.dlgdev.views;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DateViewTest {

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
		assertNotNull(view.dateAsEditTextView);
		assertNotNull(view.iconView);
		assertEquals(Dates.formatDate(date), view.dateAsEditTextView.getText().toString());
	}

	@Test public void theIconViewHasAnOnClickListenerAdded() {
		view.iconView.performClick();
		verify(listener).onDatePickerRequested(view.getId());
	}

	@Test public void theDateCanBeChangedByTheHolderOfTheView() throws Exception {
		DateTime newDate = DateTime.now().plusWeeks(1);
		view.setDate(newDate);
		assertEquals(newDate, view.date);
		assertEquals(Dates.formatDate(newDate), view.dateAsEditTextView.getText().toString());
	}
}
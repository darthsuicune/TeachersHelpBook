package com.dlgdev.teachers.helpbook.views.events;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.factories.EventsFactory;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class NewEventViewTest {
	static final String VALID_TIME = "12:12";
	static final String INVALID_VALUE = "somethingsomething";
	static final DateTime VALID_START_DATE = DateTime.now().withTimeAtStartOfDay();
	static final DateTime VALID_END_DATE = DateTime.now().withTimeAtStartOfDay().plusDays(1);

	NewEventView view;
	OnPickersRequestedListener mockListener;

	@Before public void setUp() throws Exception {
		mockListener = mock(OnPickersRequestedListener.class);
		Event event = new EventsFactory().createEmpty();

		view = (NewEventView) LayoutInflater.from(InstrumentationRegistry.getTargetContext())
				.inflate(R.layout.dialog_create_event, null);
		view.setup(event, mockListener);
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testSetup() throws Exception {
		afterSetupEverythingIsInPlace();
	}

	private void afterSetupEverythingIsInPlace() {
		assertNotNull(view.event);
		assertNotNull(view.listener);
		assertNotNull(view.startTimeIconView);
		assertNotNull(view.endTimeIconView);
		assertNotNull(view.titleView);
		assertNotNull(view.descriptionView);
		assertNotNull(view.startDateView);
		assertNotNull(view.endDateView);
		assertNotNull(view.startTimeView);
		assertNotNull(view.endTimeView);
		assertNotNull(view.fullDayCheckBox);
	}

	@Test public void testStartTimeIconCallsTheCallback() throws Exception {
		ifWeClickOnTheIcon(view.startTimeIconView);
		onStartTimeRequestedIsCalled();
	}

	private void ifWeClickOnTheIcon(ImageView iconView) {
		iconView.performClick();
	}

	private void onStartTimeRequestedIsCalled() {
		verify(mockListener).onStartTimeRequested();
	}

	@Test public void testClickingTheEndTimeIconCallsTheCallback() throws Exception {
		ifWeClickOnTheIcon(view.endTimeIconView);
		onEndTimeRequestedIsCalled();
	}

	private void onEndTimeRequestedIsCalled() {
		verify(mockListener).onEndTimeRequested();
	}

	@Test public void testFullDayCheckBoxHidesEndDateTimeWhenActivated() throws Exception {
		whenWeToggleTheFullDayCheckBox(true);
		theEndDateTimeViewsAre(GONE);
	}

	private void whenWeToggleTheFullDayCheckBox(final boolean checked) {
		InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
			@Override public void run() {
				//If the box is in the state we want it, we change it first to the other one.
				if (view.fullDayCheckBox.isChecked() == checked) {
					view.fullDayCheckBox.setChecked(!checked);
				}
				view.fullDayCheckBox.setChecked(checked);
			}
		});
	}

	private void theEndDateTimeViewsAre(int visibility) {
		assertEquals(view.startTimeView.getVisibility(), visibility);
		assertEquals(view.endDateView.getVisibility(), visibility);
		assertEquals(view.endTimeView.getVisibility(), visibility);
		assertEquals(view.startTimeIconView.getVisibility(), visibility);
		assertEquals(view.endTimeIconView.getVisibility(), visibility);
	}

	@Test public void testFullDayCheckBoxShowsEndDateTimeWhenDeactivated() throws Exception {
		whenWeToggleTheFullDayCheckBox(false);
		theEndDateTimeViewsAre(VISIBLE);
	}

	@Test public void testOnStartTimeTextChangedWithValidTime() throws Exception {
		givenTheStartDateIsPreviousToTheNewEndDate();
		whenWeWriteAValueInTheEditTextViews(view.startTimeView, VALID_TIME);
		theEventDateAndTimeAreUpdated(view.event.start());
	}

	private void givenTheStartDateIsPreviousToTheNewEndDate() {
		view.startDateView.setDate(VALID_START_DATE);
		view.endDateView.setDate(VALID_END_DATE);
	}

	private void whenWeWriteAValueInTheEditTextViews(EditText view, String value) {
		view.setText(value);
	}

	private void theEventDateAndTimeAreUpdated(DateTime date) {
		assertTrue(date.isAfter(VALID_START_DATE) && date.isBefore(VALID_END_DATE));
	}

	@Test public void testOnEndTimeTextChangedWithValidTime() throws Exception {
		givenTheStartDateIsPreviousToTheNewEndDate();
		whenWeWriteAValueInTheEditTextViews(view.endTimeView, VALID_TIME);
		theEventDateAndTimeAreUpdated(view.event.end());
	}

	private void theEditTextViewShowsAnError(TextView v) {
		assertFalse(TextUtils.isEmpty(v.getError()));
	}

	@Test public void testWhenAnInvalidTimeIsInputAnErrorIsSet() throws Exception {
		whenWeWriteAValueInTheEditTextViews(view.startTimeView, INVALID_VALUE);
		theEditTextViewShowsAnError(view.startTimeView);
	}

	@Test public void testWhenAnInvalidEndTimeIsInputAnErrorIsSet() throws Exception {
		whenWeWriteAValueInTheEditTextViews(view.endTimeView, INVALID_VALUE);
		theEditTextViewShowsAnError(view.endTimeView);
	}
}
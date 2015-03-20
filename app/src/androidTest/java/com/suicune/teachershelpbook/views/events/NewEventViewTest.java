package com.suicune.teachershelpbook.views.events;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.EventsProvider;
import com.suicune.teachershelpbook.views.courses.activities.CourseOverviewActivity;

import org.joda.time.DateTime;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NewEventViewTest extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	private static final String VALID_START_DATE = "12/12/2012";
	private static final String VALID_DATE = "12/12/2012";
	private static final String VALID_START_TIME = "11:00";
	private static final String VALID_TIME = "12:12";
	private static final String INVALID_VALUE = "somethingsomething";
	private static final DateTime VALID_START = new DateTime(2012, 12, 12, 0, 0);
	private static final DateTime VALID_END = new DateTime(2012, 12, 13, 0, 0);

	NewEventView view;
	OnPickersRequestedListener mockListener;

	public NewEventViewTest() {
		super(CourseOverviewActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		mockListener = mock(OnPickersRequestedListener.class);
		Event event = new EventsProvider().createEmpty();
		view = (NewEventView) getActivity().getLayoutInflater()
				.inflate(R.layout.create_event_dialog, null);
		view.setup(event, mockListener);
	}

	public void testSetup() throws Exception {
		afterSetupEverythingIsInPlace();
	}

	private void afterSetupEverythingIsInPlace() {
		assertNotNull(view.event);
		assertNotNull(view.listener);
		assertNotNull(view.startDateIconView);
		assertNotNull(view.endDateIconView);
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

	public void testStartDateIconCallsTheCallback() throws Exception {
		ifWeClickOnTheIcon(view.startDateIconView);
		onStartDateRequestedIsCalled();
	}

	private void ifWeClickOnTheIcon(ImageView iconView) {
		iconView.performClick();
	}

	private void onStartDateRequestedIsCalled() {
		verify(mockListener).onStartDateRequested();
	}

	public void testEndDateIconCallsTheCallback() throws Exception {
		ifWeClickOnTheIcon(view.endDateIconView);
		onEndDateRequestedIsCalled();
	}

	private void onEndDateRequestedIsCalled() {
		verify(mockListener).onEndDateRequested();
	}

	public void testStartTimeIconCallsTheCallback() throws Exception {
		ifWeClickOnTheIcon(view.startTimeIconView);
		onStartTimeRequestedIsCalled();
	}

	private void onStartTimeRequestedIsCalled() {
		verify(mockListener).onStartTimeRequested();
	}

	public void testClickingTheEndTimeIconCallsTheCallback() throws Exception {
		ifWeClickOnTheIcon(view.endTimeIconView);
		onEndTimeRequestedIsCalled();
	}

	private void onEndTimeRequestedIsCalled() {
		verify(mockListener).onEndTimeRequested();
	}

	@UiThreadTest public void testFullDayCheckBoxHidesEndDateTimeWhenActivated() throws Exception {
		whenWeToggleTheFullDayCheckBox(true);
		theEndDateTimeViewsAre(GONE);
	}

	private void whenWeToggleTheFullDayCheckBox(boolean checked) {
		view.fullDayCheckBox.setChecked(checked);
	}

	private void theEndDateTimeViewsAre(int visibility) {
		assertEquals(view.startTimeView.getVisibility(), visibility);
		assertEquals(view.endDateView.getVisibility(), visibility);
		assertEquals(view.endTimeView.getVisibility(), visibility);
		assertEquals(view.startTimeIconView.getVisibility(), visibility);
		assertEquals(view.endDateIconView.getVisibility(), visibility);
		assertEquals(view.endTimeIconView.getVisibility(), visibility);
	}

	@UiThreadTest public void testFullDayCheckBoxShowsEndDateTimeWhenDeactivated()
			throws Exception {
		whenWeToggleTheFullDayCheckBox(false);
		theEndDateTimeViewsAre(VISIBLE);
	}

	@UiThreadTest public void testOnStartDateTextChangedWithValidDateDoesntDisplayError()
			throws Exception {
		whenWeWriteAValueInTheEditTextViews(view.startDateView, VALID_DATE);
		itsParsedWithoutError(view.startDateView);
	}

	private void whenWeWriteAValueInTheEditTextViews(EditText view, String value) {
		view.setText(value);
	}

	private void itsParsedWithoutError(EditText view) {
		assertTrue(TextUtils.isEmpty(view.getError()));
	}

	@UiThreadTest public void testOnStartDateTextChangedWithValidDateUpdatesTheEvent()
			throws Exception {
		whenWeWriteAValueInTheEditTextViews(view.startDateView, VALID_DATE);
		theEventDateAndTimeAreUpdated(view.event.start());
	}

	private void theEventDateAndTimeAreUpdated(DateTime date) {
		assertTrue(date.isAfter(VALID_START) && date.isBefore(VALID_END));
	}

	@UiThreadTest public void testOnEndDateTextChangedWithValidDateDoesntDisplayError()
			throws Exception {
		givenTheStartDateIsPreviousToTheNewEndDate();
		whenWeWriteAValueInTheEditTextViews(view.endDateView, VALID_DATE);
		itsParsedWithoutError(view.endDateView);

	}

	private void givenTheStartDateIsPreviousToTheNewEndDate() {
		view.startDateView.setText(VALID_START_DATE);
		view.startTimeView.setText(VALID_START_TIME);
	}

	@UiThreadTest public void testOnEndDateTextChangedWithValidDateUpdatesTheEvent()
			throws Exception {
		givenTheStartDateIsPreviousToTheNewEndDate();
		whenWeWriteAValueInTheEditTextViews(view.endDateView, VALID_DATE);
		theEventDateAndTimeAreUpdated(view.event.end());
	}

	@UiThreadTest public void testOnStartTimeTextChangedWithValidTime() throws Exception {
		givenTheStartDateIsPreviousToTheNewEndDate();
		whenWeWriteAValueInTheEditTextViews(view.startTimeView, VALID_TIME);
		theEventDateAndTimeAreUpdated(view.event.start());
	}

	@UiThreadTest public void testOnEndTimeTextChangedWithValidTime() throws Exception {
		givenTheStartDateIsPreviousToTheNewEndDate();
		whenWeWriteAValueInTheEditTextViews(view.endTimeView, VALID_TIME);
		theEventDateAndTimeAreUpdated(view.event.end());
	}

	@UiThreadTest public void testWhenAnInvalidDateIsInputAnErrorIsSet() throws Exception {
		whenWeWriteAValueInTheEditTextViews(view.startDateView, INVALID_VALUE);
		theEditTextViewShowsAnError(view.startDateView);
	}

	private void theEditTextViewShowsAnError(TextView v) {
		assertFalse(TextUtils.isEmpty(v.getError()));
	}

	@UiThreadTest public void testWhenAnInvalidEndDateIsInputAnErrorIsSet() throws Exception {
		whenWeWriteAValueInTheEditTextViews(view.endDateView, INVALID_VALUE);
		theEditTextViewShowsAnError(view.endDateView);
	}

	@UiThreadTest public void testWhenAnInvalidTimeIsInputAnErrorIsSet() throws Exception {
		whenWeWriteAValueInTheEditTextViews(view.startTimeView, INVALID_VALUE);
		theEditTextViewShowsAnError(view.startTimeView);
	}

	@UiThreadTest public void testWhenAnInvalidEndTimeIsInputAnErrorIsSet() throws Exception {
		whenWeWriteAValueInTheEditTextViews(view.endTimeView, INVALID_VALUE);
		theEditTextViewShowsAnError(view.endTimeView);
	}
}
package com.suicune.teachershelpbook.views.events;

import android.test.ActivityInstrumentationTestCase2;
import android.text.TextUtils;
import android.widget.ImageView;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.EventsProvider;
import com.suicune.teachershelpbook.views.courses.activities.CourseOverviewActivity;

public class NewEventViewTest extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	NewEventView view;
	CourseOverviewActivity activity;
	EventsProvider provider;
	MockListener mockListener;

	public NewEventViewTest() {
		super(CourseOverviewActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		mockListener = new MockListener();
		provider = new EventsProvider();
		Event event = provider.createEmpty();
		view = (NewEventView) activity.getLayoutInflater()
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

	public void testEveryDateTimeFieldHasATextWatcherThatMarksErrorsIfNeeded() throws Exception {

	}

	public void testStartDateIconsHaveClickListeners() throws Exception {
		ifWeClickOnTheIcon(view.startDateIconView);
		theCorrectCallbackIsCalled(mockListener.startDateRequested);
	}

	public void testEndDateIconsHaveClickListeners() throws Exception {
		ifWeClickOnTheIcon(view.endDateIconView);
		theCorrectCallbackIsCalled(mockListener.endDateRequested);
	}

	public void testStartTimeIconsHaveClickListeners() throws Exception {
		ifWeClickOnTheIcon(view.startTimeIconView);
		theCorrectCallbackIsCalled(mockListener.startTimeRequested);
	}

	public void testEndTimeIconsHaveClickListeners() throws Exception {
		ifWeClickOnTheIcon(view.endTimeIconView);
		theCorrectCallbackIsCalled(mockListener.endTimeRequested);
	}

	private void ifWeClickOnTheIcon(ImageView iconView) {
		iconView.performClick();
	}

	private void theCorrectCallbackIsCalled(boolean check) {
		assertTrue(check);
	}

	public void testFullDayCheckBoxChangesTheBehaviourBasedOnTheFullDayOption() throws Exception {

	}

	public void testChangeStartDateWithValidDate() throws Exception {

	}

	public void testChangeEndDateWithValidDate() throws Exception {

	}

	public void testChangeStartTimeWithValidTime() throws Exception {

	}

	public void testChangeEndTimeWithValidTime() throws Exception {

	}

	public void testWhenAnInvalidDateIsInputAnErrorIsSet() throws Exception {
		whenWeWriteAnInvalidDate();
		theEditTextViewShowsAnError();
	}

	private void whenWeWriteAnInvalidDate() {
		view.startDateView.setText("something");
	}

	private void theEditTextViewShowsAnError() {
		assertFalse(TextUtils.isEmpty(view.startDateView.getError()));
	}

	private class MockListener implements NewEventView.OnPickersRequestedListener {
		public boolean startDateRequested;
		boolean endDateRequested;
		boolean startTimeRequested;
		boolean endTimeRequested;

		@Override public void onStartDateRequested() {
			startDateRequested = true;
		}

		@Override public void onEndDateRequested() {
			endDateRequested = true;
		}

		@Override public void onStartTimeRequested() {
			startTimeRequested = true;
		}

		@Override public void onEndTimeRequested() {
			endTimeRequested = true;
		}
	}
}
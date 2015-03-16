package com.suicune.teachershelpbook.views.events;

import android.test.ActivityInstrumentationTestCase2;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.EventsProvider;
import com.suicune.teachershelpbook.views.courses.activities.CourseOverviewActivity;

public class NewEventDialogTest extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	NewEventDialog dialog;
	CourseOverviewActivity activity;
	MockDialogListener mockListener;
	EventsProvider provider;

	public NewEventDialogTest() {
		super(CourseOverviewActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
	}

	public void testSetup() throws Exception {
		whenWeCallSetup();
		allParametersAreSetInPlace();
	}

	void whenWeCallSetup() {
		dialog = new NewEventDialog();
		provider = new EventsProvider();
		Event event = provider.createEmpty();
		dialog.setup(mockListener, event, R.id.course_weekly_main_fragment);
	}

	private void allParametersAreSetInPlace() {
		assertNotNull(dialog.listener);
		assertNotNull(dialog.parentFragmentId);
		assertNotNull(dialog.event);
	}

	private class MockDialogListener implements NewEventDialog.NewEventDialogListener{
		@Override public void onNewEventCreated(Event event) {

		}

		@Override public void onDialogCanceled() {

		}
	}
}
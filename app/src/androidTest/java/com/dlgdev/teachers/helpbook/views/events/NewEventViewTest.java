package com.dlgdev.teachers.helpbook.views.events;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

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
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class NewEventViewTest {
	static final String VALID_TIME = "12:12";
	static final String INVALID_VALUE = "somethingsomething";
	static final DateTime VALID_START_DATE = DateTime.now().withTimeAtStartOfDay();
	static final DateTime VALID_END_DATE = DateTime.now().withTimeAtStartOfDay().plusDays(1);

	NewEventView view;
	FragmentManager manager;

	@Before public void setUp() throws Exception {
		Event event = new EventsFactory().createEmpty();
		manager = mock(FragmentManager.class);

		view = (NewEventView) LayoutInflater.from(InstrumentationRegistry.getTargetContext())
				.inflate(R.layout.dialog_create_event, null);
		view.setup(manager, event.start(), event.end());
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testSetup() throws Exception {
		afterSetupEverythingIsInPlace();
	}

	private void afterSetupEverythingIsInPlace() {
		assertNotNull(view.titleView);
		assertNotNull(view.descriptionView);
		assertNotNull(view.startDateView);
		assertNotNull(view.endDateView);
		assertNotNull(view.startTimeView);
		assertNotNull(view.endTimeView);
		assertNotNull(view.fullDayCheckBox);
	}

	@Test public void testStartTimeIconCallsTheCallback() throws Exception {
		ifWeClickOnTheView(view.startTimeView);
		verify(manager).beginTransaction();
	}

	private void ifWeClickOnTheView(View v) {
		v.performClick();
	}

	@Test public void testClickingTheEndTimeIconCallsTheCallback() throws Exception {
		ifWeClickOnTheView(view.endTimeView);
		onEndTimeRequestedIsCalled();
	}

	private void onEndTimeRequestedIsCalled() {
		verify(manager).beginTransaction();
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
		assertEquals(view.endTimeView.getVisibility(), visibility);
	}

	@Test public void testFullDayCheckBoxShowsEndDateTimeWhenDeactivated() throws Exception {
		whenWeToggleTheFullDayCheckBox(false);
		theEndDateTimeViewsAre(VISIBLE);
	}
}
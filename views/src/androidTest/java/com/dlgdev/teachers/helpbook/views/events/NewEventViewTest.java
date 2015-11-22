package com.dlgdev.teachers.helpbook.views.events;

import android.support.v4.app.FragmentTransaction;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.domain.models.Event;
import com.dlgdev.teachers.helpbook.domain.models.repositories.EventsRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class NewEventViewTest {

	NewEventView view;
	FragmentManager manager;

	@Before public void setUp() throws Exception {
		Event event = new EventsRepository().createEmpty();

		view = (NewEventView) LayoutInflater.from(InstrumentationRegistry.getTargetContext())
				.inflate(R.layout.dialog_create_event, null);
		manager = mock(FragmentManager.class);
		FragmentTransaction ft = mock(FragmentTransaction.class);
		when(manager.beginTransaction()).thenReturn(ft);
		view.setup(manager, event.start(), event.end());
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testStartTimeIconCallsTheCallback() throws Exception {
		ifWeClickOnTheView(view.startTimeView);
		verify(manager).beginTransaction();
	}

	private void ifWeClickOnTheView(final View v) {
		InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
			@Override public void run() {
				v.performClick();
			}
		});
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

	@Test public void errorsAppearWhenSettingAWrongDate() throws Exception {
		view.changeStartDate(view.end.plusDays(1));
		assertNotNull(view.endDateView.getError());
	}

	@Test public void errorDisappearsWhenProperDateIsSet() throws Exception {
		view.changeStartDate(view.end.minusDays(1));
		assertNull(view.endDateView.getError());
	}

	@Test public void errorAppearsWithEmptyTitle() throws Exception {
		view.titleView.setText("");
		assertNotNull(view.titleView.getError());
	}

	@Test public void errorDisappearsWhenTitleIsSet() throws Exception {
		view.titleView.setText("asdf");
		assertNull(view.titleView.getError());
	}

}
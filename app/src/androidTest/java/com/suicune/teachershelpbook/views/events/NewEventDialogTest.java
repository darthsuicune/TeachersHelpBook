package com.suicune.teachershelpbook.views.events;

import android.support.test.espresso.Espresso;
import android.test.ActivityInstrumentationTestCase2;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.EventsProvider;
import com.suicune.teachershelpbook.views.courses.activities.CourseOverviewActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class NewEventDialogTest extends ActivityInstrumentationTestCase2<CourseOverviewActivity> {
	private static final String TAG = "dialog";
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
		mockListener = new MockDialogListener();
		dialog = new NewEventDialog();
		provider = new EventsProvider();
		Event event = provider.createEmpty();
		dialog.setup(mockListener, event, R.id.course_weekly_main_fragment);
		dialog.show(activity.getSupportFragmentManager(), TAG);
	}

	public void testSetup() throws Exception {
		allParametersAreSetInPlaceAfterCreation();
	}

	private void allParametersAreSetInPlaceAfterCreation() {
		assertNotNull(dialog.listener);
		assertNotNull(dialog.parentFragmentId);
		assertNotNull(dialog.event);
	}

	public void testRotationHoldsTheData() throws Throwable {
		String someData = "someData";
		whenWeEnterSomeData(someData);
		andDestroyRestoreTheState();
		theDataIsHeld(someData);
	}

	private void whenWeEnterSomeData(String s) throws Throwable{
		onView(withId(R.id.title)).perform(typeText(s));
		Espresso.closeSoftKeyboard();
		Thread.sleep(1000); //Delay needed because espresso doesn't wait until the kb is gone
	}

	private void andDestroyRestoreTheState() throws InterruptedException {
		activity.finish();
		activity = getActivity();
		dialog = (NewEventDialog) activity.getSupportFragmentManager().findFragmentByTag(TAG);
	}

	private void theDataIsHeld(String s) throws InterruptedException {
		assertEquals(s, dialog.newEventView.titleView.getText().toString());
	}

	public void testClickingOnCreateSavesTheEvent() throws Throwable {
		String someData = "someData";
		whenWeEnterSomeData(someData);
		andWeClickOnCreate();
		anEventWithTheCorrectDataIsCreated(someData);
	}

	private void andWeClickOnCreate() {
		onView(withId(android.R.id.button1)).perform(click());
	}

	private void anEventWithTheCorrectDataIsCreated(String data) {
		assertEquals(data, mockListener.event.title());
	}

	public void testClickingOnCancelTriggersTheCallback() throws Exception {
		whenWeClickOnCancel();
		theCallbackGetsTheEvent();
	}

	private void whenWeClickOnCancel() {
		onView(withId(android.R.id.button2)).perform(click());
	}

	private void theCallbackGetsTheEvent() {
		assertTrue(mockListener.dialogCancelled);
	}


	private class MockDialogListener implements NewEventDialog.NewEventDialogListener {
		Event event;
		boolean dialogCancelled = false;

		@Override public void onNewEventCreated(Event event) {
			this.event = event;
		}

		@Override public void onDialogCancelled() {
			dialogCancelled = true;
		}
	}
}
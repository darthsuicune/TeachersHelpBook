package com.dlgdev.teachers.helpbook.views.events.fragments;


import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.factories.EventsFactory;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class NewEventDialogTest {
	private static final String TAG = "dialog";

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class, true, false);
	NewEventDialog dialog;
	CourseOverviewActivity activity;
	NewEventDialog.NewEventDialogListener mockListener;
	EventsFactory provider;

	String someData = "someData";

	@Before public void setUp() throws Exception {
		launchActivity();
		mockListener = Mockito.mock(NewEventDialog.NewEventDialogListener.class);
		dialog = new NewEventDialog();
		provider = new EventsFactory();
		Event event = provider.createEmpty();
		dialog.setup(mockListener, event, R.id.course_weekly_main_fragment);
		dialog.show(activity.getSupportFragmentManager(), TAG);
	}

	private void launchActivity() {
		Course course = new Course();
		course.save();
		Intent intent = new Intent();
		intent.putExtra(CourseOverviewActivity.KEY_MODEL_ID, course.getId());
		activity = rule.launchActivity(intent);

	}

	@After public void teardown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testSetup() throws Exception {
		allParametersAreSetInPlaceAfterCreation();
	}

	private void allParametersAreSetInPlaceAfterCreation() {
		assertNotNull(dialog.listener);
		assertNotNull(dialog.event);
	}

	@Test public void testDestructionCycleHoldsTheData() throws Throwable {
		whenWeEnterSomeData(someData);
		andDestroyRestoreTheState();
		theDataIsHeld(someData);
	}

	private void whenWeEnterSomeData(String s) throws Throwable {
		onView(withId(R.id.create_event_dialog_title)).perform(typeText(s));
		Espresso.closeSoftKeyboard();
		Thread.sleep(1000); //Delay needed because espresso doesn't wait until the kb is gone
	}

	private void andDestroyRestoreTheState() throws InterruptedException {
		activity.finish();
		activity = rule.getActivity();
		dialog = (NewEventDialog) activity.getSupportFragmentManager().findFragmentByTag(TAG);
	}

	private void theDataIsHeld(String s) throws InterruptedException {
		assertEquals(s, dialog.newEventView.getTitle());
	}

	@Test public void testClickingOnCreateSavesTheEvent() throws Throwable {
		whenWeEnterSomeData(someData);
		andWeClickOnCreate();
		anEventWithTheCorrectDataIsCreated(someData);
	}

	private void andWeClickOnCreate() {
		onView(withId(android.R.id.button1)).perform(click());
	}

	private void anEventWithTheCorrectDataIsCreated(String data) {
		verify(mockListener).onNewEventCreated(argThat(matchesEventWithTitle(data)));
	}

	private ArgumentMatcher<Event> matchesEventWithTitle(final String data) {
		return new ArgumentMatcher<Event>() {
			@Override public boolean matches(Object o) {
				if(o != null) {
					Event event = (Event) o;
					return event.title().equals(data);
				}
				return false;
			}
		};
	}

	@Test public void testClickingOnCancelTriggersTheCallback() throws Exception {
		whenWeClickOnCancel();
		theCallbackGetsTheEvent();
	}

	private void whenWeClickOnCancel() {
		onView(withId(android.R.id.button2)).perform(click());
	}

	private void theCallbackGetsTheEvent() {
		verify(mockListener).onDialogCancelled();
	}
}
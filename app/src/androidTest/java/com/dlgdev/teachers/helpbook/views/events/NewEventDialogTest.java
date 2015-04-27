package com.dlgdev.teachers.helpbook.views.events;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.model.events.Event;
import com.dlgdev.teachers.helpbook.model.events.EventsProvider;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;
import com.dlgdev.teachers.helpbook.views.events.NewEventDialog.NewEventDialogListener;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
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
			new ActivityTestRule<>(CourseOverviewActivity.class);
	NewEventDialog dialog;
	CourseOverviewActivity activity;
	NewEventDialogListener mockListener;
	EventsProvider provider;

	String someData = "someData";

	@Before public void setUp() throws Exception {
		activity = rule.getActivity();
		mockListener = Mockito.mock(NewEventDialogListener.class);
		dialog = new NewEventDialog();
		provider = new EventsProvider();
		Event event = provider.createEmpty();
		dialog.setup(mockListener, event, R.id.course_weekly_main_fragment);
		dialog.show(activity.getSupportFragmentManager(), TAG);
	}

	@Test public void testSetup() throws Exception {
		allParametersAreSetInPlaceAfterCreation();
	}

	private void allParametersAreSetInPlaceAfterCreation() {
		assertNotNull(dialog.listener);
		assertNotNull(dialog.parentFragmentId);
		assertNotNull(dialog.event);
	}

	@Test public void testDestructionCycleHoldsTheData() throws Throwable {
		whenWeEnterSomeData(someData);
		andDestroyRestoreTheState();
		theDataIsHeld(someData);
	}

	private void whenWeEnterSomeData(String s) throws Throwable {
		onView(withId(R.id.title)).perform(typeText(s));
		Espresso.closeSoftKeyboard();
		Thread.sleep(1000); //Delay needed because espresso doesn't wait until the kb is gone
	}

	private void andDestroyRestoreTheState() throws InterruptedException {
		activity.finish();
		activity = rule.getActivity();
		dialog = (NewEventDialog) activity.getSupportFragmentManager().findFragmentByTag(TAG);
	}

	private void theDataIsHeld(String s) throws InterruptedException {
		assertEquals(s, dialog.newEventView.titleView.getText().toString());
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

	private Matcher<Event> matchesEventWithTitle(final String data) {
		return new BaseMatcher<Event>() {
			@Override public boolean matches(Object o) {
				if(o != null) {
					Event event = (Event) o;
					return event.title().equals(data);
				}
				return false;
			}

			@Override public void describeTo(Description description) {

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
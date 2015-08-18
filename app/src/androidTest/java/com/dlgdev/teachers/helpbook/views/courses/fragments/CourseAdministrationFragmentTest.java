package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.query.Select;
import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.factories.EventsFactory;
import com.dlgdev.teachers.helpbook.models.factories.SubjectsFactory;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseAdministrationActivity;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CourseAdministrationFragment.CourseAdministrationActionListener;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class CourseAdministrationFragmentTest {
	static final String EVENT_TITLE = "event_title";
	static final String EVENT_DESC = "desc";
	static final String SUBJECT_TITLE = "subject_title";
	public static final String COURSE_TITLE = "Curse";
	public static final String COURSE_DESC = "This is such a curse";
	public static final String HOLIDAY_TITLE = "Something";
	private static final String COURSE_START = "01/01/2010";
	private static final String COURSE_END = "12/12/2011";

	CourseAdministrationFragment fragment;
	CourseAdministrationActivity activity;
	Course course;
	SubjectsFactory subjectsFactory;
	EventsFactory eventsFactory;
	CourseAdministrationActionListener listener;

	@Rule public ActivityTestRule<CourseAdministrationActivity> rule =
			new ActivityTestRule<>(CourseAdministrationActivity.class, true, false);

	@Before public void setUp() throws Exception {
		DatabaseUtils.intializeDb(InstrumentationRegistry.getTargetContext());
		listener = Mockito.mock(CourseAdministrationActionListener.class);
		subjectsFactory = new SubjectsFactory();
		eventsFactory = new EventsFactory();
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void afterLoadActivityLoadsTheCorrectCourse() throws Exception {
		launchActivity();
		assertEquals(course.getId(), fragment.course.getId());
	}

	private void launchActivity() {
		createStuffForTheCourse();
		Intent intent = new Intent();
		intent.putExtra(CourseAdministrationActivity.KEY_COURSE, course.getId());
		launchActivityWithIntent(intent);
	}

	private void launchActivityWithIntent(Intent intent) {
		activity = rule.launchActivity(intent);
		fragment = (CourseAdministrationFragment) activity.getSupportFragmentManager()
				.findFragmentById(R.id.course_administration_fragment);
	}

	@Test public void afterLaunchTheAvailableInformationIsDisplayed() throws Exception {
		launchActivity();
		onView(withText(COURSE_TITLE)).check(matches(isDisplayed()));
		onView(withText(COURSE_DESC)).check(matches(isDisplayed()));
		onView(withText(EVENT_TITLE)).check(matches(isEnabled()));
		onView(withText(EVENT_DESC)).check(matches(isEnabled()));
		onView(withText(SUBJECT_TITLE)).check(matches(isDisplayed()));
		onView(withText(HOLIDAY_TITLE)).check(matches(isDisplayed()));
	}

	private void createStuffForTheCourse() {
		course = new Course();
		course.save();
		course.addSubject(subjectsFactory.createAndSave(SUBJECT_TITLE));
		course.addBankHoliday(DateTime.now(), HOLIDAY_TITLE);
		course.addEvent(eventsFactory.createAndSave(EVENT_TITLE, EVENT_DESC));
		course.start = DateTime.now().minusWeeks(5);
		course.end = DateTime.now().plusWeeks(5);
		course.title = COURSE_TITLE;
		course.description = COURSE_DESC;
		course.save();
	}

	@Test public void displaysTheMenuOptionToSaveTheCourseInformation() throws Exception {
		launchActivity();
		onView(withId(R.id.menu_save_course)).check(matches(isDisplayed()));
	}

	@Test public void theSaveButtonWorks() throws Exception {
		launchActivityWithIntent(new Intent());
		assertNull(fragment.course);
		onView(withId(R.id.menu_save_course)).perform(click());
		assertNotNull(fragment.course.getId());
	}

	@Test public void theSaveButtonForwardsToTheOverviewActivity() throws Exception {
		launchActivity();
		onView(withId(R.id.menu_save_course)).perform(click());
		onView(withId(R.id.course_overview_panel)).check(matches(isDisplayed()));
	}

	@Test public void introducingSomeDataSavesItAfterPressingSave() throws Exception {
		launchActivityWithIntent(new Intent());
		fragment.listener = listener;
		onView(withId(R.id.course_administration_name)).perform(typeText(COURSE_TITLE));
		Espresso.closeSoftKeyboard();
		Thread.sleep(500);
		onView(withId(R.id.course_administration_description)).perform(typeText(COURSE_DESC));
		Espresso.closeSoftKeyboard();
		Thread.sleep(500);
		onView(withId(R.id.course_administration_start_date)).perform(typeText(COURSE_START));
		Espresso.closeSoftKeyboard();
		Thread.sleep(500);
		onView(withId(R.id.course_administration_end_date)).perform(typeText(COURSE_END));
		Espresso.closeSoftKeyboard();
		Thread.sleep(500);
		onView(withId(R.id.menu_save_course)).perform(click());
		course = new Select().from(Course.class).orderBy("_ID DESC").executeSingle();
		verify(listener).onSaved(course);
	}
}
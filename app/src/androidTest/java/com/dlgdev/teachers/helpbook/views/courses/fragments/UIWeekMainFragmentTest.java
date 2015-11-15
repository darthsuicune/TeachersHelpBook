package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;
import com.dlgdev.teachers.helpbook.views.courses.fragments.WeekMainFragment.WeeklyEventsListener;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class UIWeekMainFragmentTest {
    WeekMainFragment fragment;
    WeeklyEventsListener listener = mock(WeeklyEventsListener.class);
    String text = "someText";
    DateTime date = DateTime.now().withDayOfWeek(DateTimeConstants.MONDAY);
    Course course;

    @Rule
    public ActivityTestRule<CourseOverviewActivity> rule =
            new ActivityTestRule<>(CourseOverviewActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        course = new Course();
        course.title = "Title";
        course.save();
        getFragment();
    }

    public void getFragment() {
        Intent intent = new Intent();
        intent.putExtra(CourseOverviewActivity.KEY_MODEL_ID, course.id);
        rule.launchActivity(intent);
        fragment = (WeekMainFragment) rule.getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.course_weekly_main_fragment);
        fragment.listener = listener;
    }

    @After
    public void tearDown() throws Exception {
        DatabaseUtils.clearDatabase();
    }

    @Test
    public void testOnNewEventRequestedOpensADialogToCreateAnEvent() throws Exception {
        whenWeRequestANewEvent();
        aNewEventDialogIsOpened();
    }

    private void whenWeRequestANewEvent() {
        fragment.onNewEventRequested(date);
    }

    private void aNewEventDialogIsOpened() {
        onView(withId(R.id.new_event_dialog)).check(matches(isDisplayed()));
    }

    @Test
    public void testCreatingAnEventDisplaysItInTheList() throws Throwable {
        whenWeRequestANewEvent();
        fillSomeDataAndCreateTheEvent();
        theEventDataIsDisplayedInTheProperDay();
    }

    private void fillSomeDataAndCreateTheEvent() throws Throwable {
        onView(withId(R.id.create_event_dialog_title)).perform(typeText(text));
        Espresso.closeSoftKeyboard();
        onView(withText(R.string.create_event)).perform(click());
    }

    private void theEventDataIsDisplayedInTheProperDay() {
        int parentId = fragment.dailyCards.get(date.getDayOfWeek()).getId();
        onView(allOf(withId(R.id.event_entry_name), isDescendantOfA(withId(parentId))))
                .check(matches(withText(text)));
    }
}

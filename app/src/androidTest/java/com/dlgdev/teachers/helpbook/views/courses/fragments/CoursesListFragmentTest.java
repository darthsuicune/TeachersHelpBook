package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.views.courses.activities.CoursesListActivity;
import com.dlgdev.teachers.helpbook.views.courses.fragments.CoursesListFragment.OnCourseListInteractionListener;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class CoursesListFragmentTest {
	CoursesListFragment fragment;
	Course course;
	OnCourseListInteractionListener listener;

	@Rule public ActivityTestRule<CoursesListActivity> rule =
			new ActivityTestRule<>(CoursesListActivity.class);

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void theAddCourseMenuIconIsAlwaysPresent() throws Exception {
		onView(withId(R.id.add_new_course)).check(matches(isDisplayed()));
	}

	@Test public void withoutACurrentCourseItShouldDisplayAnEmptyList() throws Exception {
		onView(withId(R.id.course_list_empty)).check(matches(isDisplayed()));
		onView(withId(R.id.course_list)).check(matches(not(isDisplayed())));
	}

	@Test public void withACourseNotCurrentItShouldDisplayItInTheList() throws Exception {
		afterCreatingACourse();
		onView(withId(R.id.course_list)).check(matches(isDisplayed()));
		onView(withId(R.id.course_list_empty)).check(matches(not(isDisplayed())));
	}

	private void afterCreatingACourse() {
		course = new Course(DateTime.now().minusWeeks(1), DateTime.now().plusWeeks(1));
		course.save();
	}

	@Test public void clickTheAddCourseIconPassesTheCallToTheListener() throws Exception {
		whenWeSetupTheFragment();
		onView(withId(R.id.add_new_course)).perform(click());
		verify(listener).onNewCourseRequested();
	}

	private void whenWeSetupTheFragment() {
		getFragment();
		listener = mock(OnCourseListInteractionListener.class);
		fragment.listener = listener;
	}

	public void getFragment() {
		fragment = (CoursesListFragment) rule.getActivity().getSupportFragmentManager()
				.findFragmentById(R.id.courses_list_fragment);
	}
}
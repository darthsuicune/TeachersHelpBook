package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.teachers.helpbook.views.courses.activities.CourseOverviewActivity;

import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class WeeklyEventsPreviewFragmentTest {

	@Rule public ActivityTestRule<CourseOverviewActivity> rule =
			new ActivityTestRule<>(CourseOverviewActivity.class);

	@Test public void testClickingNextWeekFragmentViewShowsFollowingWeekInOwnPanel()
			throws Exception {
		int panel = R.id.course_weekly_next;
		whenWeClickOnThePanel(panel);
		DateTime nextWeekDate = DateTime.now().plusWeeks(2);
		thePanelShowsTheStartAndEndOfWeekForDate(panel, nextWeekDate); //2 weeks from current
	}

	private void whenWeClickOnThePanel(int panel) {
		onView(withId(panel)).perform(click());
	}

	private void thePanelShowsTheStartAndEndOfWeekForDate(int parent, DateTime date) {
		String range = Dates.formatDateRange(Dates.startOfWeek(date), Dates.endOfWeek(date));
		onView(allOf(withId(R.id.weekly_events_preview_text),
					 isDescendantOfA(withId(parent))))
				.check(matches(withText(range)));
	}

	@Test public void testClickingSecondNextWeekFragmentViewShowsSecondNextWeekInMain()
			throws Exception {
		int panel = R.id.course_weekly_second_next;
		whenWeClickOnThePanel(panel);
		DateTime secondNextWeekDate = DateTime.now().plusWeeks(4);
		thePanelShowsTheStartAndEndOfWeekForDate(panel, secondNextWeekDate); // 4 weeks from current
	}

	@Test public void testClickingPreviousWeekFragmentViewShowsPreviousWeekInMain()
			throws Exception {
		int panel = R.id.course_weekly_previous;
		whenWeClickOnThePanel(panel);
		DateTime previousWeekDate = DateTime.now().minusWeeks(2);
		thePanelShowsTheStartAndEndOfWeekForDate(panel, previousWeekDate); //2 weeks before current
	}
}
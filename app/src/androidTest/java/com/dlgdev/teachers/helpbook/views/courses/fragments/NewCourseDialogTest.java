package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.pm.ActivityInfo;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.DatabaseUtils;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.views.courses.activities.CoursesListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class NewCourseDialogTest {
	private static final String TAG = "dialog";

	@Rule public ActivityTestRule<CoursesListActivity> rule =
			new ActivityTestRule<>(CoursesListActivity.class);
	NewCourseDialog dialog;
	CoursesListActivity activity;
	NewCourseDialog.CourseCreationDialogListener listener;

	@Before public void setUp() throws Exception {
		activity = rule.getActivity();
		listener = Mockito.mock(NewCourseDialog.CourseCreationDialogListener.class);
		dialog = new NewCourseDialog();
		dialog.setup(listener, R.id.courses_list_fragment);
		dialog.show(activity.getSupportFragmentManager(), TAG);
	}

	@After public void teardown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void hittingSaveWillShowErrorsAndNotCancelTheDialog() throws Exception {
		onView(withText(R.string.create_course)).perform(click());
		verify(listener, never()).onCourseCreated(any(Course.class));
	}

	@Test public void creatingADialogRotatingAndCancellingDoesntCrash() throws Exception {
		restartActivity();
		onView(withText(android.R.string.cancel)).perform(click());
	}

	private void restartActivity() {
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
}
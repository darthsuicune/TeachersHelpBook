package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.views.courses.activities.CoursesListActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnOverviewDrawerListener} interface
 * to handle interaction events.
 */
public class CourseOverviewDrawerFragment extends Fragment implements CourseInfoHolder {

	private static final String PREFERENCE_IS_LEARNED = "overviewDrawerFragmentLearned";
	OnOverviewDrawerListener listener;
	ActionBarDrawerToggle drawerToggle;
	DrawerLayout drawerLayout;
	boolean userLearnedDrawer;
	View fragmentContainerView;
	TextView subjectsView;
	TextView studentGroupsView;
	TextView holidaysView;
	TextView eventsView;
	TextView headerView;
	TextView otherCoursesView;

	public CourseOverviewDrawerFragment() {
		// Required empty public constructor
	}

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
									   Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_course_overview_drawer, container, false);
		prepareViews(v);
		return v;
	}

	private void prepareViews(View v) {
		subjectsView = (TextView) v.findViewById(R.id.course_overview_drawer_subjects);
		studentGroupsView = (TextView) v.findViewById(R.id.course_overview_drawer_student_groups);
		holidaysView = (TextView) v.findViewById(R.id.course_overview_drawer_holidays);
		eventsView = (TextView) v.findViewById(R.id.course_overview_drawer_events);
		headerView = (TextView) v.findViewById(R.id.course_overview_drawer_header);
		otherCoursesView = (TextView) v.findViewById(R.id.course_overview_drawer_other_courses);

		subjectsView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onSubjectsRequested();
			}
		});
		studentGroupsView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onStudentGroupsRequested();
			}
		});
		holidaysView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onHolidaysRequested();
			}
		});
		eventsView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onEventsRequested();
			}
		});
		headerView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onCourseInfoRequested();
			}
		});
		otherCoursesView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				openCourseList();
			}
		});
	}

	private void openCourseList() {
		Intent intent = new Intent(getActivity(), CoursesListActivity.class);
		startActivity(intent);
	}

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		listener = (OnOverviewDrawerListener) context;
	}

	@Override public void onDetach() {
		super.onDetach();
		listener = null;
	}

	public void setup(DrawerLayout layout, View fragmentView, Toolbar toolbar,
					  final SharedPreferences prefs) {
		this.drawerLayout = layout;
		this.fragmentContainerView = fragmentView;
		userLearnedDrawer = prefs.getBoolean(PREFERENCE_IS_LEARNED, false);
		// set a custom shadow that overlays the main content when the drawer opens
//		layout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the navigation drawer and the action bar app icon.
		drawerToggle = new ActionBarDrawerToggle(getActivity(), layout, toolbar,
				R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
			@Override public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (isAdded()) {
					getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
				}
			}

			@Override public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (isAdded()) {
					getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
					if (!userLearnedDrawer) {
						userLearnedDrawer = true;
						prefs.edit().putBoolean(PREFERENCE_IS_LEARNED, true).apply();
					}
				}
			}
		};

		// If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
		// per the navigation drawer design guidelines.
		if (!userLearnedDrawer) {
			layout.openDrawer(fragmentView);
		}

		// Defer code dependent on restoration of previous instance state.
		layout.post(new Runnable() {
			@Override public void run() {
				drawerToggle.syncState();
			}
		});

		layout.setDrawerListener(drawerToggle);
	}

	@Override public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		drawerToggle.onConfigurationChanged(newConfig);
	}

	public boolean isDrawerOpen() {
		return drawerLayout != null && drawerLayout.isDrawerOpen(fragmentContainerView);
	}

	public void openDrawer() {
		if (drawerLayout != null) {
			drawerLayout.openDrawer(fragmentContainerView);
		}
	}

	public void closeDrawer() {
		if (drawerLayout != null) {
			drawerLayout.closeDrawer(fragmentContainerView);
		}
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
	}

	@Override public void updateCourse(Course course) {
		headerView.setText(getString(R.string.course_header, course.title));
		subjectsView.setText(getString(R.string.subjects_marker, course.subjects().size()));
		studentGroupsView
				.setText(getString(R.string.student_groups_marker, course.studentGroups().size()));
		holidaysView.setText(getString(R.string.holidays_marker, course.holidays().size()));
		eventsView.setText(getString(R.string.events_marker, course.events().size()));
	}

	public interface OnOverviewDrawerListener {
		void onEventsRequested();

		void onSubjectsRequested();

		void onStudentGroupsRequested();

		void onHolidaysRequested();

		void onCourseInfoRequested();
	}

}

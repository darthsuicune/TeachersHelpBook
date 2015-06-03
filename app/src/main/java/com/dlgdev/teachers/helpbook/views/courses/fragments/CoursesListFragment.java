package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.views.ClickableViewHolder;
import com.dlgdev.views.DividerWrappedRecyclerView;
import com.dlgdev.views.ListElementViewHolder;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CoursesListFragment extends Fragment {
	private static final int LOADER_COURSE = 1;
	List<Course> courses = new ArrayList<>();

	DividerWrappedRecyclerView courseList;
	TextView emptyList;
	OnCourseListInteractionListener listener;
	CoursesAdapter adapter;

	public CoursesListFragment() {
	}

	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (OnCourseListInteractionListener) activity;
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_courses_list, container, false);
		setViews(v);
		return v;
	}

	private void setViews(View v) {
		courseList = (DividerWrappedRecyclerView) v.findViewById(R.id.course_list);
		emptyList = (TextView) v.findViewById(R.id.course_list_empty);
	}

	@Override public void onResume() {
		super.onResume();
		getLoaderManager().initLoader(LOADER_COURSE, null, new CourseLoaderHelper());
	}

	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_courses_list, menu);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.add_new_course:
				addNewCourse();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void addNewCourse() {
		listener.onNewCourseRequested();
	}

	private class CourseLoaderHelper implements LoaderManager.LoaderCallbacks<Cursor> {
		@Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			Uri uri = TeachersDBContract.Courses.URI;
			return new CursorLoader(getActivity(), uri, null, null, null, null);
		}

		@Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			List<Course> courses = new ArrayList<>();
			if (data != null && data.moveToFirst()) {
				do {
					Course course = new Course();
					course.loadFromCursor(data);
					courses.add(course);
				} while (data.moveToNext());
			}
			updateCourses(courses);
		}

		@Override public void onLoaderReset(Loader<Cursor> loader) {

		}
	}

	public void updateCourses(List<Course> courses) {
		this.courses = courses;
		if (courses.size() > 0) {
			courseList.setVisibility(VISIBLE);
			emptyList.setVisibility(GONE);
			loadData();
		} else {
			courseList.setVisibility(GONE);
			emptyList.setVisibility(VISIBLE);
		}
	}

	private void loadData() {
		if (adapter == null) {
			adapter = new CoursesAdapter();
			courseList.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged(courses);

	}

	public interface OnCourseListInteractionListener {
		void onCourseSelected(Course course);
		void onNewCourseRequested();
	}

	private class CoursesAdapter extends RecyclerView.Adapter<ListElementViewHolder>
			implements ClickableViewHolder.RecyclerItemListener {
		List<Course> courses;

		public CoursesAdapter() {
			courses = CoursesListFragment.this.courses;
		}

		@Override
		public ListElementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(getActivity()).inflate(R.layout.list_item, parent, false);
			ListElementViewHolder holder = new ListElementViewHolder(v, this);
			v.setOnClickListener(holder);
			return holder;
		}

		@Override
		public void onBindViewHolder(ListElementViewHolder holder, int position) {
			Course course = courses.get(position);
			holder.position(position);
			holder.title(course.title);
			holder.description(course.description);
		}

		@Override public int getItemCount() {
			return courses.size();
		}

		@Override public void onItemSelected(int position) {
			listener.onCourseSelected(courses.get(position));
		}

		public void notifyDataSetChanged(List<Course> courses) {
			this.courses = courses;
			notifyDataSetChanged();
		}
	}
}

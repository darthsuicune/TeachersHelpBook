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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.views.ClickableListElementViewHolder;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CoursesListActivityFragment extends Fragment {
	private static final int LOADER_COURSE = 1;
	List<Course> courses = new ArrayList<>();

	RecyclerView courseList;
	TextView emptyList;
	OnCourseSelectedListener listener;

	public CoursesListActivityFragment() {
	}

	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (OnCourseSelectedListener) activity;
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_courses_list, container, false);
		setViews(v);
		return v;
	}

	private void setViews(View v) {
		courseList = (RecyclerView) v.findViewById(R.id.course_list);
		emptyList = (TextView) v.findViewById(R.id.course_list_empty);
		courseList.setAdapter(new CoursesAdapter());
	}

	@Override public void onResume() {
		super.onResume();
		getLoaderManager().initLoader(LOADER_COURSE, null, new CourseLoaderHelper());
	}

	private class CourseLoaderHelper implements LoaderManager.LoaderCallbacks<Cursor> {
		@Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			Uri uri = TeachersDBContract.Courses.URI;
			return new CursorLoader(getActivity(), uri, null, null, null, null);
		}

		@Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			if (data != null && data.moveToFirst()) {
				Course course = new Course();
				course.loadFromCursor(data);
				courses.add(course);
			}
			displayData();
		}

		@Override public void onLoaderReset(Loader<Cursor> loader) {

		}
	}

	private void displayData() {
		courseList.setVisibility((courses.size() > 0) ? VISIBLE : GONE);
		emptyList.setVisibility((courses.size() > 0) ? GONE : VISIBLE);

	}

	public interface OnCourseSelectedListener {
		void onCourseSelected(Course course);
	}

	private class CoursesAdapter extends RecyclerView.Adapter<ClickableListElementViewHolder>
			implements ClickableListElementViewHolder.RecyclerItemListener {
		@Override
		public ClickableListElementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(getActivity()).inflate(R.layout.list_item, parent, false);
			ClickableListElementViewHolder holder = new ClickableListElementViewHolder(v, this);
			v.setOnClickListener(holder);
			return holder;
		}

		@Override
		public void onBindViewHolder(ClickableListElementViewHolder holder, int position) {
			holder.position(position);
		}

		@Override public int getItemCount() {
			return courses.size();
		}

		@Override public void onItemSelected(int position) {
			listener.onCourseSelected(courses.get(position));
		}
	}
}

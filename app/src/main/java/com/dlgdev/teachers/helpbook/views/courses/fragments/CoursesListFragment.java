package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.dlgdev.views.ClickableViewHolder;
import com.dlgdev.views.DividerWrappedRecyclerView;
import com.dlgdev.views.ListElementViewHolder;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CoursesListFragment extends Fragment
		implements NewCourseDialog.CourseCreationDialogListener {
	static final String TAG_NEW_COURSE_DIALOG = "new course";
	static final String KEY_DIALOG_IS_SHOWN = "dialog is shown";
	private static final int LOADER_COURSE = 1;
	List<Course> courses = new ArrayList<>();
	boolean isDisplayingDialog = false;

	DividerWrappedRecyclerView courseList;
	TextView emptyList;
	OnCourseListInteractionListener listener;
	CoursesAdapter adapter;
	FloatingActionButton addCourseButton;

	public CoursesListFragment() {
	}

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		listener = (OnCourseListInteractionListener) context;
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
		addCourseButton = (FloatingActionButton) v.findViewById(R.id.add_new_course);
		addCourseButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				requestNewCourse();
			}
		});
	}

	private void requestNewCourse() {
		isDisplayingDialog = true;
		NewCourseDialog dialog = new NewCourseDialog();
		dialog.setup(this, this.getId());
		dialog.show(getFragmentManager(), TAG_NEW_COURSE_DIALOG);
	}

	@Override public void onCourseCreated(Course course) {
		listener.onCourseSelected(course);
		isDisplayingDialog = false;
	}

	@Override public void onDialogCancelled() {
		isDisplayingDialog = false;
	}

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(KEY_DIALOG_IS_SHOWN, isDisplayingDialog);
	}

	@Override public void onResume() {
		super.onResume();
		getLoaderManager().initLoader(LOADER_COURSE, null, new CourseLoaderHelper());
	}

	public boolean canSkip() {
		return !isDisplayingDialog;
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
	}

	private class CoursesAdapter extends RecyclerView.Adapter<ListElementViewHolder>
			implements ClickableViewHolder.RecyclerItemListener {
		List<Course> courses;

		public CoursesAdapter() {
			courses = CoursesListFragment.this.courses;
		}

		@Override public ListElementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(getActivity()).inflate(R.layout.list_item, parent, false);
			ListElementViewHolder holder = new ListElementViewHolder(v, this);
			v.setOnClickListener(holder);
			return holder;
		}

		@Override public void onBindViewHolder(ListElementViewHolder holder, int position) {
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

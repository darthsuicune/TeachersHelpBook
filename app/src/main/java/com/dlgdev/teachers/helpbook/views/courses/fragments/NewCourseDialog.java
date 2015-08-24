package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.views.courses.NewCourseView;

public class NewCourseDialog extends DialogFragment {
	private static final String ARG_PARENT_ID = "parent id";
	Course course;
	NewCourseDialogListener listener;
	int parentId;
	NewCourseView newCourseView;

	public NewCourseDialog() {
	}

	public void setup(NewCourseDialogListener listener, int parentId) {
		this.listener = listener;
		this.parentId = parentId;
		course = new Course();
	}

	@Override public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_PARENT_ID, parentId);
		course.save();
	}

	@Override @NonNull public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			restoreFragment(savedInstanceState);
		}
		return buildDialog(getDialogView(R.layout.dialog_create_course));
	}

	private void restoreFragment(Bundle savedInstanceState) {
		parentId = savedInstanceState.getInt(ARG_PARENT_ID);
		listener = (NewCourseDialogListener) getFragmentManager().findFragmentById(parentId);
	}

	private AlertDialog buildDialog(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getString(R.string.create_course_dialog_title));
		builder.setView(view);
		builder.setPositiveButton(R.string.create_course, new DialogInterface.OnClickListener() {
			@Override public void onClick(DialogInterface dialogInterface, int which) {
				save();
				listener.onCourseCreated(course);
			}
		});
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override public void onClick(DialogInterface dialogInterface, int which) {
				if(course.getId() != null) {
					course.delete();
				}
				listener.onDialogCancelled();
			}
		});
		return builder.create();
	}

	private View getDialogView(int layout) {
		newCourseView = (NewCourseView) getActivity().getLayoutInflater().inflate(layout, null);
		newCourseView.setup(course);
		return newCourseView;
	}

	private void save() {
		course.title = newCourseView.getTitle();
		course.description = newCourseView.getDescription();
		course.start = newCourseView.getCourseStart();
		course.end = newCourseView.getCourseEnd();
		course.save();
	}

	public interface NewCourseDialogListener {
		void onCourseCreated(Course course);

		void onDialogCancelled();
	}
}

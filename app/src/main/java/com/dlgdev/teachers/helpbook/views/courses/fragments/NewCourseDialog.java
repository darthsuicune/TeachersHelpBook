package com.dlgdev.teachers.helpbook.views.courses.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.InvalidModelException;
import com.dlgdev.teachers.helpbook.views.ModelCreationDialogFragment;
import com.dlgdev.teachers.helpbook.views.events.NewEventView;

import org.joda.time.DateTime;

import java.util.List;

import ollie.query.Select;

public class NewCourseDialog extends ModelCreationDialogFragment {
	Course course = new Course();
	NewEventView newCourseView;

	public NewCourseDialog() {
	}

	@Override public void restoreModel(Long id) {
		course = Select.from(Course.class).where(TeachersDBContract.Courses._ID + "=?", id).fetchSingle();
	}

    @Override public AlertDialog buildDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getString(R.string.create_course_dialog_title));
		builder.setView(getDialogView(R.layout.dialog_create_event));
		builder.setPositiveButton(R.string.create_course, new DialogInterface.OnClickListener() {
			@Override public void onClick(DialogInterface dialogInterface, int which) {
				saveAndGetId();
				verifySavedData();
			}
		});
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override public void onClick(DialogInterface dialogInterface, int which) {
				if (course.id != null) {
					course.delete();
				}
				listener.onDialogCancelled();
			}
		});
		return builder.create();
	}

	private void verifySavedData() {
		try {
			course.safelySave();
			((CourseCreationDialogListener) listener).onCourseCreated(course);
		} catch (InvalidModelException e) {
			newCourseView.setErrors();
		}
	}

	private View getDialogView(int layout) {
		newCourseView = (NewEventView) getActivity().getLayoutInflater().inflate(layout, null);
		DateTime start = (course.start != null) ? course.start : DateTime.now();
		DateTime end =  (course.end != null) ? course.end : DateTime.now();
		newCourseView.setup(getFragmentManager(), start, end);
		return newCourseView;
	}

	@Override public Long saveAndGetId() {
		course.title = newCourseView.getTitle();
		course.description = newCourseView.getDescription();
		course.start = newCourseView.getStart();
		course.end = newCourseView.getEnd();
		return course.save();
	}

	public interface CourseCreationDialogListener extends ModelCreationDialogListener {
		void onCourseCreated(Course course);
	}
}

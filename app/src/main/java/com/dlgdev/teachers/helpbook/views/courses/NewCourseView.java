package com.dlgdev.teachers.helpbook.views.courses;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.dlgdev.teachers.helpbook.models.Course;

import org.joda.time.DateTime;

public class NewCourseView extends RelativeLayout {
	Course course;
	EditText titleView;
	EditText descriptionView;

	public NewCourseView(Context context) {
		super(context);
	}

	public NewCourseView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setup(Course course) {
		this.course = course;

	}

	public String getTitle() {
		return titleView.getText().toString();
	}

	public String getDescription() {
		return descriptionView.getText().toString();
	}

	public DateTime getCourseStart() {
		return null;
	}

	public DateTime getCourseEnd() {
		return null;
	}
}

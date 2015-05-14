package com.dlgdev.teachers.helpbook.models.db;

import android.net.Uri;
import android.provider.BaseColumns;

import com.activeandroid.content.ContentProvider;
import com.dlgdev.teachers.helpbook.models.courses.Course;
import com.dlgdev.teachers.helpbook.models.events.Event;
import com.dlgdev.teachers.helpbook.models.subjects.Subject;
import com.dlgdev.teachers.helpbook.models.subjects.TimeTableEntry;

public class TeachersDBContract {
	public static final String AUTHORITY = "com.dlgdev.teachers.helpbook";

	public static final class Events implements BaseColumns {
		public static final String TABLE_NAME = "Events";
		public static final Uri URI = ContentProvider.createUri(Event.class, null);
		public static final String TITLE = "title";
		public static final String DESCRIPTION = "description";
		public static final String TYPE = "type";
		public static final String END = "end";
		public static final String START = "start";
		public static final String COURSE = "course";
		public static final String SUBJECT = "subject";
	}

	public static final class Courses implements BaseColumns {
		public static final String TABLE_NAME = "Courses";
		public static final Uri URI = ContentProvider.createUri(Course.class, null);
		public static final String TITLE = "title";
		public static final String DESCRIPTION = "description";
		public static final String END = "end";
		public static final String START = "start";
	}

	public static final class Subjects implements BaseColumns {
		public static final String TABLE_NAME = "Subjects";
		public static final Uri URI = ContentProvider.createUri(Subject.class, null);
		public static final String TITLE = "title";
		public static final String DESCRIPTION = "description";
		public static final String COURSE = "course";
		public static final String START = "start";
		public static final String END = "end";
	}

	public static final class TimeTableEntries implements BaseColumns {
		public static final String TABLE_NAME = "TimeTableEntries";
		public static final Uri URI = ContentProvider.createUri(TimeTableEntry.class, null);
		public static final String START = "start";
		public static final String END = "end";
		public static final String SUBJECT = "subject";
	}
}

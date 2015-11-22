package com.dlgdev.teachers.helpbook.domain.db;

import android.net.Uri;
import android.provider.BaseColumns;

import com.dlgdev.teachers.helpbook.domain.models.Course;
import com.dlgdev.teachers.helpbook.domain.models.Event;
import com.dlgdev.teachers.helpbook.domain.models.Grade;
import com.dlgdev.teachers.helpbook.domain.models.GroupTakesSubject;
import com.dlgdev.teachers.helpbook.domain.models.Holiday;
import com.dlgdev.teachers.helpbook.domain.models.Student;
import com.dlgdev.teachers.helpbook.domain.models.StudentGroup;
import com.dlgdev.teachers.helpbook.domain.models.Subject;
import com.dlgdev.teachers.helpbook.domain.models.TimeTableEntry;

import ollie.OllieProvider;

public class TeachersDBContract {
	public static final class Events implements BaseColumns {
		public static final String TABLE_NAME = "Events";
		public static final Uri URI = OllieProvider.createUri(Event.class);
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
		public static final Uri URI = OllieProvider.createUri(Course.class);
		public static final String TITLE = "title";
		public static final String DESCRIPTION = "description";
		public static final String END = "end";
		public static final String START = "start";
	}

	public static final class Subjects implements BaseColumns {
		public static final String TABLE_NAME = "Subjects";
		public static final Uri URI = OllieProvider.createUri(Subject.class);
		public static final String TITLE = "title";
		public static final String DESCRIPTION = "description";
		public static final String COURSE = "course";
		public static final String START = "start";
		public static final String END = "end";
	}

	public static final class TimeTableEntries implements BaseColumns {
		public static final String TABLE_NAME = "TimeTableEntries";
		public static final Uri URI = OllieProvider.createUri(TimeTableEntry.class);
		public static final String START = "start";
		public static final String END = "end";
		public static final String SUBJECT = "subject";
	}

	public static final class Holidays implements BaseColumns {
		public static final String TABLE_NAME = "Holidays";
		public static final Uri URI = OllieProvider.createUri(Holiday.class);
		public static final String DATE = "holiday_date";
		public static final String NAME = "holiday_name";
		public static final String COURSE = "course";
	}

	public static final class Students implements BaseColumns {
		public static final String TABLE_NAME = "Students";
		public static final Uri URI = OllieProvider.createUri(Student.class);
		public static final String NAME = "name";
		public static final String SURNAME = "surname";
		public static final String BIRTHDAY = "birthday";
		public static final String GROUP = "student_group";
		public static final String OBSERVATIONS = "observations";
		public static final String GRADES = "grades";

	}

	public static final class StudentGroups implements BaseColumns {
		public static final String TABLE_NAME = "StudentGroups";
		public static final Uri URI = OllieProvider.createUri(StudentGroup.class);
		public static final String NAME = "name";
		public static final String COURSE = "course";
	}

	public static final class Grades implements BaseColumns {
		public static final String TABLE_NAME = "Grades";
		public static final Uri URI = OllieProvider.createUri(Grade.class);
		public static final String GRADE = "grade";
		public static final String STUDENT = "student";
		public static final String SUBJECT = "subject";
	}

	public static final class GroupTakesSubjects implements BaseColumns {
		public static final String TABLE_NAME = "GroupTakesSubjects";
		public static final Uri URI = OllieProvider.createUri(GroupTakesSubject.class);
		public static final String STUDENT_GROUP = "student_group";
		public static final String SUBJECT = "subject";
	}
}

package com.dlgdev.teachers.helpbook;

import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.Grade;
import com.dlgdev.teachers.helpbook.models.GroupTakesSubject;
import com.dlgdev.teachers.helpbook.models.Holiday;
import com.dlgdev.teachers.helpbook.models.Student;
import com.dlgdev.teachers.helpbook.models.StudentGroup;
import com.dlgdev.teachers.helpbook.models.Subject;
import com.dlgdev.teachers.helpbook.models.TimeTableEntry;

import ollie.query.Delete;

public class DatabaseUtils {
	public static void clearDatabase() {
		Delete.from(Event.class).execute();
		Delete.from(Grade.class).execute();
		Delete.from(Holiday.class).execute();
		Delete.from(TimeTableEntry.class).execute();
		Delete.from(GroupTakesSubject.class).execute();
		Delete.from(StudentGroup.class).execute();
		Delete.from(Student.class).execute();
		Delete.from(Subject.class).execute();
		Delete.from(Course.class).execute();
	}
}

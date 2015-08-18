package com.dlgdev.teachers.helpbook;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.query.Delete;
import com.dlgdev.teachers.helpbook.models.Course;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.Grade;
import com.dlgdev.teachers.helpbook.models.GroupTakesSubject;
import com.dlgdev.teachers.helpbook.models.Holiday;
import com.dlgdev.teachers.helpbook.models.Student;
import com.dlgdev.teachers.helpbook.models.StudentGroup;
import com.dlgdev.teachers.helpbook.models.Subject;
import com.dlgdev.teachers.helpbook.models.TimeTableEntry;

public class DatabaseUtils {
	public static void clearDatabase() {
		new Delete().from(GroupTakesSubject.class).execute();
		new Delete().from(Holiday.class).execute();
		new Delete().from(TimeTableEntry.class).execute();
		new Delete().from(Event.class).execute();
		new Delete().from(Grade.class).execute();
		new Delete().from(Student.class).execute();
		new Delete().from(Subject.class).execute();
		new Delete().from(StudentGroup.class).execute();
		new Delete().from(Course.class).execute();
	}

	public static void intializeDb(Context context) {
		intializeDb(context, "tests.db");
	}

	public static void intializeDb(Context context, String name) {
		ActiveAndroid.dispose();
		Configuration config = new Configuration.Builder(context)
				.setModelClasses(Course.class, Event.class, Grade.class, GroupTakesSubject.class,
						Holiday.class, Student.class, StudentGroup.class, Subject.class,
						TimeTableEntry.class)
				.setDatabaseName(name)
				.create();
		ActiveAndroid.initialize(config);
	}
}

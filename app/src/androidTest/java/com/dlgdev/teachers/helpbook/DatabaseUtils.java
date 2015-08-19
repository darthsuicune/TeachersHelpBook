package com.dlgdev.teachers.helpbook;

import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.ActiveAndroid;
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

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
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

	@Test public void validNameIsValid() throws Exception {
		String name = ActiveAndroid.getDatabase().getPath();
		assertTrue(name.contains("tests"));
	}
}

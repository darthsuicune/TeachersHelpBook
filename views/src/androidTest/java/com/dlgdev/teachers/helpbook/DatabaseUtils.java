package com.dlgdev.teachers.helpbook;

import android.support.test.InstrumentationRegistry;

import com.dlgdev.teachers.helpbook.domain.db.TeachersProvider;
import com.dlgdev.teachers.helpbook.domain.models.Course;
import com.dlgdev.teachers.helpbook.domain.models.Event;
import com.dlgdev.teachers.helpbook.domain.models.Grade;
import com.dlgdev.teachers.helpbook.domain.models.GroupTakesSubject;
import com.dlgdev.teachers.helpbook.domain.models.Holiday;
import com.dlgdev.teachers.helpbook.domain.models.Student;
import com.dlgdev.teachers.helpbook.domain.models.StudentGroup;
import com.dlgdev.teachers.helpbook.domain.models.Subject;
import com.dlgdev.teachers.helpbook.domain.models.TimeTableEntry;

import ollie.Ollie;
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


    public static void init() {
        Ollie.init(InstrumentationRegistry.getContext(), TeachersProvider.DB_NAME, TeachersProvider.DB_VERSION);
    }
}

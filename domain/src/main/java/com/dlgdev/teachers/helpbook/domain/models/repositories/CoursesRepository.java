package com.dlgdev.teachers.helpbook.domain.models.repositories;

import com.dlgdev.teachers.helpbook.domain.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.domain.models.Course;

import org.joda.time.DateTime;

import ollie.query.Select;

public class CoursesRepository {

    public Course current() {
        long millis = DateTime.now().getMillis();
        return Select.from(Course.class)
                .where(TeachersDBContract.Courses.START + "<? AND " + TeachersDBContract.Courses.END + ">?", millis,
                        millis)
                .fetchSingle();
    }

    public Course create() {
        return new Course(DateTime.now(), DateTime.now());
    }
}

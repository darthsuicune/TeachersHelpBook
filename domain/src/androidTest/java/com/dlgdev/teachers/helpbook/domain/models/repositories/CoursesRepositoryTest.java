package com.dlgdev.teachers.helpbook.domain.models.repositories;

import android.support.test.runner.AndroidJUnit4;

import com.dlgdev.teachers.helpbook.domain.DatabaseUtils;
import com.dlgdev.teachers.helpbook.domain.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.domain.models.Course;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import ollie.query.Delete;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CoursesRepositoryTest {
    CoursesRepository repository;

    @BeforeClass public static void init() {
        DatabaseUtils.init();
    }
    @Before public void setup() {
        repository = new CoursesRepository();
    }

    @After public void dispose() {
        DatabaseUtils.clearDatabase();
    }

    @Test public void currentReturnsACourseIfAnyMatchesTheCurrentDate() throws Exception {
        addSeveralCoursesWithOneToday();
        Course course = repository.current();
        assertNotNull(course);
        assertTrue(course.start.isBefore(DateTime.now()) && course.end.isAfter(DateTime.now()));
    }

    private long addSeveralCoursesWithOneToday() {
        Course course = new Course(DateTime.now().minusYears(1), DateTime.now().minusMonths(3));
        course.save();
        course = new Course(DateTime.now().minusMonths(2), DateTime.now().plusMonths(2));
        course.save();
        long result = course.id;
        course = new Course(DateTime.now().plusMonths(3), DateTime.now().plusYears(1));
        course.save();
        return result;
    }

    @Test public void currentReturnsNullIfNoneMatchesTheCurrentDate() throws Exception {
        long current = addSeveralCoursesWithOneToday();
        Delete.from(Course.class).where(TeachersDBContract.Courses._ID + "=?", current).execute();
        Course course = repository.current();
        assertNull(course);
    }
}
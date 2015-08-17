package com.dlgdev.teachers.helpbook.models;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.query.Select;
import com.dlgdev.teachers.helpbook.DatabaseUtils;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CourseTest {
	Course course;
	int count;

	@Before public void setUp() throws Exception {
		DatabaseUtils.getDatabase(InstrumentationRegistry.getTargetContext());
		count = new Select().from(Course.class).count();
	}

	@After public void tearDown() throws Exception {
		DatabaseUtils.clearDatabase();
	}

	@Test public void testModelGetsAnIdAfterSaving() throws Exception {
		afterCreatingACourse();
		assertTrue(course.getId() > 0);
	}

	private void afterCreatingACourse() {
		DateTime start = new DateTime();
		DateTime end = new DateTime();
		course = new Course(start, end);
		course.save();
	}

	@Test public void testModelIsStoredInTheDb() throws Exception {
		afterCreatingACourse();
		Course course = whenWeQueryTheDatabaseForOne();
		assertNotNull(course);
	}

	private Course whenWeQueryTheDatabaseForOne() {
		return new Select().from(Course.class).orderBy("_ID DESC").limit(1).executeSingle();
	}

	@Test public void testModelEqualsTheStoredObject() throws Exception {
		afterCreatingACourse();
		Course course = whenWeQueryTheDatabaseForOne();
		assertEquals(course, this.course);
	}

	@Test public void currentReturnsACourseIfAnyMatchesTheCurrentDate() throws Exception {
		addSeveralCoursesWithOneToday();
		Course course = Course.current();
		assertNotNull(course);
		assertTrue(course.start.isBefore(DateTime.now()) && course.end.isAfter(DateTime.now()));
	}

	private long addSeveralCoursesWithOneToday() {
		Course course = new Course(DateTime.now().minusYears(1), DateTime.now().minusMonths(3));
		course.save();
		course = new Course(DateTime.now().minusMonths(2), DateTime.now().plusMonths(2));
		course.save();
		long result = course.getId();
		course = new Course(DateTime.now().plusMonths(3), DateTime.now().plusYears(1));
		course.save();
		return result;
	}

	@Test public void currentReturnsNullIfNoneMatchesTheCurrentDate() throws Exception {
		long current = addSeveralCoursesWithOneToday();
		Course.delete(Course.class, current);
		Course course = Course.current();
		assertNull(course);
	}
}

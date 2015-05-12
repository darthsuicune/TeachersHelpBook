package com.dlgdev.teachers.helpbook.models.courses;

import android.support.test.runner.AndroidJUnit4;

import com.activeandroid.query.Select;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CourseTest {
	Course course;
	int count;

	@Before public void setUp() throws Exception {
		DateTime start = new DateTime();
		DateTime end = new DateTime();
		count = new Select().from(Course.class).count();
		course = new Course(start, end);
		course.save();
	}

	@After public void tearDown() throws Exception {
		course.delete();
	}

	@Test public void testModelGetsAnIdAfterSaving() throws Exception {
		assertTrue(course.getId() > 0);
	}
	@Test public void testModelIsStoredInTheDb() throws Exception {
		Course course = whenWeQueryTheDatabaseForOne();
		assertNotNull(course);
	}

	private Course whenWeQueryTheDatabaseForOne() {
		return new Select().from(Course.class).orderBy("_ID DESC").limit(1).executeSingle();
	}

	@Test public void testModelEqualsTheStoredObject() throws Exception {
		Course course = whenWeQueryTheDatabaseForOne();
		assertEquals(course, this.course);
	}
}

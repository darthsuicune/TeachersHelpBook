package com.dlgdev.teachers.helpbook.model.courses;

import com.activeandroid.query.Select;

import junit.framework.TestCase;

import org.joda.time.DateTime;

public class CourseTest extends TestCase {
	Course course;
	int count;

	@Override public void setUp() throws Exception {
		DateTime start = new DateTime();
		DateTime end = new DateTime();
		count = new Select().from(Course.class).count();
		course = new Course(start, end);
		course.save();
	}

	@Override protected void tearDown() throws Exception {
		super.tearDown();
		course.delete();
	}

	public void testModelGetsAnIdAfterSaving() throws Exception {
		assertTrue(course.getId() > 0);
	}
	public void testModelIsStoredInTheDb() throws Exception {
		Course course = whenWeQueryTheDatabaseForOne();
		assertNotNull(course);
	}

	private Course whenWeQueryTheDatabaseForOne() {
		return new Select().from(Course.class).orderBy("_ID DESC").limit(1).executeSingle();
	}

	public void testModelEqualsTheStoredObject() throws Exception {
		Course course = whenWeQueryTheDatabaseForOne();
		assertEquals(course, this.course);
	}
}

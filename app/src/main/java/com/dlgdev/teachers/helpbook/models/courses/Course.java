package com.dlgdev.teachers.helpbook.models.courses;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dlgdev.teachers.helpbook.models.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.models.events.Event;
import com.dlgdev.teachers.helpbook.models.events.EventList;

import org.joda.time.DateTime;

import java.util.List;

@Table(name = TeachersDBContract.Courses.TABLE_NAME, id = TeachersDBContract.Courses._ID)
public class Course extends Model {
	@Column(name = TeachersDBContract.Courses.START) public DateTime start;
	@Column(name = TeachersDBContract.Courses.END) public DateTime end;
	@Column(name = TeachersDBContract.Courses.TITLE) public String title;
	@Column(name = TeachersDBContract.Courses.DESCRIPTION) public String description;

	//For use only through ActiveAndroid
	public Course() {
		super();
	}

	public Course(DateTime start, DateTime end) {
		this.start = start;
		this.end = end;
	}

	public EventList eventsBetween(DateTime start, DateTime end) {
		return null;
	}

	public List<Event> events() {
		return getMany(Event.class, TeachersDBContract.Courses.TABLE_NAME);
	}
}

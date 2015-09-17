package com.dlgdev.teachers.helpbook.models;


import com.dlgdev.teachers.helpbook.db.TeachersDBContract;

import org.joda.time.DateTime;

import java.util.List;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;
import ollie.query.Select;

@Table(TeachersDBContract.Subjects.TABLE_NAME)
public class Subject extends Model implements Listable {
	@Column(TeachersDBContract.Subjects.TITLE) public String title;
	@Column(TeachersDBContract.Subjects.DESCRIPTION) public String description;
	@Column(TeachersDBContract.Subjects.COURSE) public Course course;
	@Column(TeachersDBContract.Subjects.START) public DateTime start;
	@Column(TeachersDBContract.Subjects.END) public DateTime end;

	public Subject() {}

	public List<Event> events() {
		return Select.from(Event.class).fetch();
	}

	public List<TimeTableEntry> timeTable() {
		return Select.from(TimeTableEntry.class).fetch();
	}

	public List<GroupTakesSubject> groupsWithSubject() {
		return Select.from(GroupTakesSubject.class).fetch();
	}

	public void course(Course course) {
		this.course = course;
	}

	@Override public String title() {
		return title;
	}

	@Override public String description() {
		return description;
	}
}

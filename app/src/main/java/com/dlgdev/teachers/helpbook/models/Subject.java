package com.dlgdev.teachers.helpbook.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract;

import org.joda.time.DateTime;

import java.util.List;

@Table(name = TeachersDBContract.Subjects.TABLE_NAME, id = TeachersDBContract.Subjects._ID)
public class Subject extends Model {
	@Column(name = TeachersDBContract.Subjects.TITLE) public String title;
	@Column(name = TeachersDBContract.Subjects.DESCRIPTION) public String description;
	@Column(name = TeachersDBContract.Subjects.COURSE) public Course course;
	@Column(name = TeachersDBContract.Subjects.START) public DateTime start;
	@Column(name = TeachersDBContract.Subjects.END) public DateTime end;

	public Subject() {}

	public List<Event> events() {
		return getMany(Event.class, TeachersDBContract.Events.SUBJECT);
	}

	public List<TimeTableEntry> timeTable() {
		return getMany(TimeTableEntry.class, TeachersDBContract.TimeTableEntries.SUBJECT);
	}

	public List<GroupTakesSubject> groupsWithSubject() {
		return getMany(GroupTakesSubject.class, TeachersDBContract.GroupTakesSubjects.SUBJECT);
	}

	public void course(Course course) {
		this.course = course;
	}
}

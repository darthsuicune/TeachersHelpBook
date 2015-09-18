package com.dlgdev.teachers.helpbook.models;


import java.util.List;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.ForeignKey;
import ollie.annotation.Table;
import ollie.query.Select;

import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.StudentGroups;

@Table(StudentGroups.TABLE_NAME)
public class StudentGroup extends Model implements Listable {
	@Column(StudentGroups.NAME) public String name;
	@ForeignKey
	@Column(StudentGroups.COURSE) public Course course;

	public List<Student> students() {
		return Select.from(Student.class).fetch();
	}

	public List<GroupTakesSubject> subjectsTaken() {
		return Select.from(GroupTakesSubject.class).fetch();
	}

	public Course course() {
		return course;
	}

	@Override public String title() {
		return name;
	}

	@Override public String description() {
		return Integer.toString(students().size());
	}
}

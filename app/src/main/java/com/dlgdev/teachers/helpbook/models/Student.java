package com.dlgdev.teachers.helpbook.models;


import com.dlgdev.teachers.helpbook.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract.Students;

import org.joda.time.DateTime;

import java.util.List;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;
import ollie.query.Select;

@Table(Students.TABLE_NAME)
public class Student extends Model {
	@Column(Students.NAME) String name;
	@Column(Students.SURNAME) String surname;
	@Column(Students.BIRTHDAY)
	DateTime birthday;
	@Column(Students.GROUP) StudentGroup group;
	@Column(Students.OBSERVATIONS) String observations;

	public Student() {}

	public List<Grade> grades() {
		return Select.from(Grade.class).fetch();
	}
}

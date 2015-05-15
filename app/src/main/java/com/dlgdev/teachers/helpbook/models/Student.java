package com.dlgdev.teachers.helpbook.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.joda.time.DateTime;

import java.util.List;

import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.Grades;
import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.Students;

@Table(name = Students.TABLE_NAME, id = Students._ID)
public class Student extends Model {
	@Column(name = Students.NAME) String name;
	@Column(name = Students.SURNAME) String surname;
	@Column(name = Students.BIRTHDAY) DateTime birthday;
	@Column(name = Students.GROUP) StudentGroup group;
	@Column(name = Students.OBSERVATIONS) String observations;

	public Student() {}

	public List<Grade> grades() {
		return getMany(Grade.class, Grades.STUDENT);
	}
}

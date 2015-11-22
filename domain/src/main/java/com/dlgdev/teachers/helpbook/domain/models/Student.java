package com.dlgdev.teachers.helpbook.domain.models;


import com.dlgdev.teachers.helpbook.domain.db.TeachersDBContract.Students;

import org.joda.time.DateTime;

import java.util.List;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.ForeignKey;
import ollie.annotation.Table;
import ollie.query.Select;

/**
 * TODO: Requires extension: Remove student from group hardcoded.
 * Add new model: StudentInGroup.
 * That way a student might take part in several courses/groups through the years.
 */
@Table(Students.TABLE_NAME)
public class Student extends Model {
	@Column(Students.NAME) public String name;
	@Column(Students.SURNAME) public String surname;
	@Column(Students.BIRTHDAY) public DateTime birthday;
	@ForeignKey
	@Column(Students.GROUP) public StudentGroup group;
	@Column(Students.OBSERVATIONS) public String observations;

	public Student() {}

	public List<Grade> grades() {
		return Select.from(Grade.class).fetch();
	}
}

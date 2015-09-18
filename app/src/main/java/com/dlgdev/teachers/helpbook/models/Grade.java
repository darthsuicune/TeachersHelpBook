package com.dlgdev.teachers.helpbook.models;


import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.ForeignKey;
import ollie.annotation.Table;

import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.Grades;

@Table(Grades.TABLE_NAME)
public class Grade extends Model {
	@Column(Grades.GRADE) public String grade;
	@ForeignKey
	@Column(Grades.STUDENT) public Student student;
	@ForeignKey
	@Column(Grades.SUBJECT) public Subject subject;
}

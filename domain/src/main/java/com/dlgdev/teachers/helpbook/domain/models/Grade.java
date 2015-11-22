package com.dlgdev.teachers.helpbook.domain.models;


import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.ForeignKey;
import ollie.annotation.Table;

import static com.dlgdev.teachers.helpbook.domain.db.TeachersDBContract.Grades;

@Table(Grades.TABLE_NAME)
public class Grade extends Model {
	@Column(Grades.GRADE) public String grade;
	@ForeignKey(onDelete = ForeignKey.ReferentialAction.SET_DEFAULT)
	@Column(Grades.STUDENT) public Student student;
	@ForeignKey(onDelete = ForeignKey.ReferentialAction.SET_DEFAULT)
	@Column(Grades.SUBJECT) public Subject subject;

    public Grade() {}
}

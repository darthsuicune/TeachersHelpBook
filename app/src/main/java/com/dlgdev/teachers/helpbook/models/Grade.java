package com.dlgdev.teachers.helpbook.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.Grades;

@Table(name = Grades.TABLE_NAME, id = Grades._ID)
public class Grade extends Model {
	@Column(name = Grades.GRADE) String grade;
	@Column(name = Grades.STUDENT) Student student;
}

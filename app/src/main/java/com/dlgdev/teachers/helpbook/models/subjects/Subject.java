package com.dlgdev.teachers.helpbook.models.subjects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dlgdev.teachers.helpbook.models.db.TeachersDBContract;

@Table(name = TeachersDBContract.Subjects.TABLE_NAME, id = TeachersDBContract.Subjects._ID)
public class Subject extends Model {
	@Column(name = TeachersDBContract.Subjects.TITLE) public String title;
	@Column(name = TeachersDBContract.Subjects.DESCRIPTION) public String description;

	public Subject() {}
}

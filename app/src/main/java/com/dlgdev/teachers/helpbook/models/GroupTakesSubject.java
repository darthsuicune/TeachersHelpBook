package com.dlgdev.teachers.helpbook.models;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract.GroupTakesSubjects;

@Table(name = GroupTakesSubjects.TABLE_NAME, id = GroupTakesSubjects._ID)
public class GroupTakesSubject extends Model {
	@Column(name = GroupTakesSubjects.SUBJECT) Subject subject;
	@Column(name = GroupTakesSubjects.STUDENT_GROUP) StudentGroup group;
}

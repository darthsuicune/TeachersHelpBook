package com.dlgdev.teachers.helpbook.domain.models;


import com.dlgdev.teachers.helpbook.domain.db.TeachersDBContract.GroupTakesSubjects;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table(GroupTakesSubjects.TABLE_NAME)
public class GroupTakesSubject extends Model {
	@Column(GroupTakesSubjects.SUBJECT) public Subject subject;
	@Column(GroupTakesSubjects.STUDENT_GROUP) public StudentGroup group;

    public GroupTakesSubject() {}
}

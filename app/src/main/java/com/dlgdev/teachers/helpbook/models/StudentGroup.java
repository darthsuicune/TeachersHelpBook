package com.dlgdev.teachers.helpbook.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.GroupTakesSubjects;
import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.StudentGroups;
import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.Students;
@Table(name = StudentGroups.TABLE_NAME, id = StudentGroups._ID)
public class StudentGroup extends Model implements Listable {
	@Column(name = StudentGroups.NAME) String name;
	@Column(name = StudentGroups.COURSE, index = true) Course course;

	public List<Student> students() {
		return getMany(Student.class, Students.GROUP);
	}

	public List<GroupTakesSubject> subjectsTaken() {
		return getMany(GroupTakesSubject.class, GroupTakesSubjects.STUDENT_GROUP);
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

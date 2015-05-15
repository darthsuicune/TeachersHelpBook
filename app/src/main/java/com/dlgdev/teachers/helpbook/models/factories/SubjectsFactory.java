package com.dlgdev.teachers.helpbook.models.factories;


import com.dlgdev.teachers.helpbook.models.Subject;

public class SubjectsFactory {
	public Subject createEmpty() {
		return new Subject();
	}

	public Subject createAndSave(String title) {
		Subject subject = createEmpty();
		subject.title = title;
		subject.save();
		return subject;
	}
}

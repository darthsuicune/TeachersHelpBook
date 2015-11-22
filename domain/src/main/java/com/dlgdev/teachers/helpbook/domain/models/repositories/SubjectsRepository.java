package com.dlgdev.teachers.helpbook.domain.models.repositories;


import com.dlgdev.teachers.helpbook.domain.models.Subject;

public class SubjectsRepository {
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

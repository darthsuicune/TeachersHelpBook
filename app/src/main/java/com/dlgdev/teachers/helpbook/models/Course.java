package com.dlgdev.teachers.helpbook.models;

import android.text.TextUtils;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.Courses;
import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.Events;
import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.Holidays;
import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.StudentGroups;
import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.Subjects;

@Table(name = Courses.TABLE_NAME, id = Courses._ID)
public class Course extends ValidatingModel {
	@Column(name = Courses.START) public DateTime start;
	@Column(name = Courses.END) public DateTime end;
	@Column(name = Courses.TITLE) public String title;
	@Column(name = Courses.DESCRIPTION) public String description;

	//For use only through ActiveAndroid
	public Course() {
		super();
	}

	public Course(DateTime start, DateTime end) {
		this.start = start;
		this.end = end;
	}

	public EventList eventsBetween(DateTime start, DateTime end) {
		List<Event> events = new ArrayList<>();
		for (Event event : events()) {
			if (event.isBetween(start, end)) {
				events.add(event);
			}
		}
		return new EventList(events);
	}

	public List<Event> events() {
		if(getId() == null) {
			return new ArrayList<>();
		}
		return getMany(Event.class, Events.COURSE);
	}

	public List<Subject> subjects() {
		if(getId() == null) {
			return new ArrayList<>();
		}
		return getMany(Subject.class, Subjects.COURSE);
	}

	public List<Holiday> holidays() {
		if(getId() == null) {
			return new ArrayList<>();
		}
		return getMany(Holiday.class, Holidays.COURSE);
	}

	public List<StudentGroup> studentGroups() {
		if(getId() == null) {
			return new ArrayList<>();
		}
		return getMany(StudentGroup.class, StudentGroups.COURSE);
	}

	public Subject addSubject(Subject subject) {
		subject.course(this);
		subject.save();
		return subject;
	}

	public Holiday addBankHoliday(DateTime date, String name) {
		return Holiday.addBankHoliday(this, date, name);
	}

	public Event addEvent(Event event) {
		event.course(this);
		event.save();
		return event;
	}

	public static Course current() {
		return new Select().from(Course.class)
				.where(Courses.START + "<?", DateTime.now().getMillis())
				.and(Courses.END + ">?", DateTime.now().getMillis())
				.executeSingle();
	}

	@Override protected boolean checkConstraints() {
		return !TextUtils.isEmpty(title) && start != null && end != null;
	}
}

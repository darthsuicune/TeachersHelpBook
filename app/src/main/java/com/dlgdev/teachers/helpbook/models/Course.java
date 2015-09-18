package com.dlgdev.teachers.helpbook.models;

import android.text.TextUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ollie.annotation.Column;
import ollie.annotation.Table;
import ollie.query.Select;

import static com.dlgdev.teachers.helpbook.db.TeachersDBContract.Courses;

@Table(Courses.TABLE_NAME)
public class Course extends ValidatingModel {
    @Column(Courses.START) public DateTime start;
    @Column(Courses.END) public DateTime end;
    @Column(Courses.TITLE) public String title;
    @Column(Courses.DESCRIPTION) public String description;

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
        if (this.id == null) {
            return new ArrayList<>();
        }
        return Select.from(Event.class).fetch();
    }

    public List<Subject> subjects() {
        if (this.id == null) {
            return new ArrayList<>();
        }
        return Select.from(Subject.class).fetch();
    }

    public List<Holiday> holidays() {
        if (this.id == null) {
            return new ArrayList<>();
        }
        return Select.from(Holiday.class).fetch();
    }

    public List<StudentGroup> studentGroups() {
        if (this.id == null) {
            return new ArrayList<>();
        }
        return Select.from(StudentGroup.class).fetch();
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
        return Select.from(Course.class)
                .where(Courses.START + "<? AND " + Courses.END + ">?", DateTime.now().getMillis(),
                        DateTime.now().getMillis())
        .fetchSingle();
    }

    @Override
    protected boolean checkConstraints() {
        return !TextUtils.isEmpty(title) && start != null && end != null && end.isAfter(start);
    }
}

package com.dlgdev.teachers.helpbook.models.events;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.courses.Course;
import com.dlgdev.teachers.helpbook.models.db.TeachersDBContract;

import org.joda.time.DateTime;

import java.io.Serializable;

@Table(name = TeachersDBContract.Events.TABLE_NAME, id = TeachersDBContract.Events._ID)
public class Event extends Model implements Serializable {
    public static final int DEFAULT_EVENT_DURATION_IN_HOURS = 1;
    @Column(name = TeachersDBContract.Events.START, index = true)
    DateTime start;
    @Column(name = TeachersDBContract.Events.END, index = true)
    DateTime end;
    @Column(name = TeachersDBContract.Events.TITLE)
    String title;
    @Column(name = TeachersDBContract.Events.DESCRIPTION)
    String description;
    @Column(name = TeachersDBContract.Events.COURSE, index = true)
    Course course;
    @Column(name = TeachersDBContract.Events.SUBJECT, index = true)
    Course subject;
    @Column(name = TeachersDBContract.Events.TYPE, index = true)
    String type;

    // For use only through ActiveAndroid
    public Event() {
        super();
    }

    public Event(DateTime start, DateTime end) {
        this.start = start;
        this.end = end;
    }

    public boolean isAt(DateTime time) {
        return time.compareTo(start) == 0 || (time.isAfter(start) && time.isBefore(end)) ||
                time.compareTo(end) == 0;
    }

    public boolean isBetween(DateTime start, DateTime end) {
        return start.compareTo(start) == 0 || end.compareTo(end) == 0 ||
                (this.start.isBefore(start) && this.end.isAfter(end));
    }

    public boolean isInDay(DateTime day) {
        return start.isBefore(day.withTime(23, 59, 59, 999)) &&
                end.isAfter(day.withTimeAtStartOfDay());
    }

    public void title(String title) {
        this.title = title;
    }

    public void description(String description) {
        this.description = description;
    }

    public void course(Course course) {
        this.course = course;
    }

    public void type(String type) {
        this.type = type;
    }

    public DateTime start() {
        return start;
    }

    public DateTime end() {
        return end;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public Course course() {
        return course;
    }

    public String type() {
        return type;
    }

    public void start(DateTime newDateTime) {
        if (newDateTime.isAfter(end)) {
            throw new InvalidDateTimeException(R.string.start_cannot_be_after_end);
        }
        start = newDateTime;
    }

    public void end(DateTime newDateTime) {
        if (newDateTime.isBefore(start)) {
            throw new InvalidDateTimeException(R.string.start_cannot_be_after_end);
        }
        end = newDateTime;
    }
}

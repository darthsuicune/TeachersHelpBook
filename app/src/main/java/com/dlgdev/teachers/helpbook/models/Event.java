package com.dlgdev.teachers.helpbook.models;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.utils.InvalidDateTimeException;

import org.joda.time.DateTime;

import java.io.Serializable;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.ForeignKey;
import ollie.annotation.Table;

@Table(TeachersDBContract.Events.TABLE_NAME)
public class Event extends Model implements Serializable, Listable {
    public static final int DEFAULT_EVENT_DURATION_IN_HOURS = 1;
    @Column(TeachersDBContract.Events.START) public DateTime start;
    @Column(TeachersDBContract.Events.END) public DateTime end;
    @Column(TeachersDBContract.Events.TITLE) public String title;
    @Column(TeachersDBContract.Events.DESCRIPTION) public String description;
    @ForeignKey(onDelete = ForeignKey.ReferentialAction.SET_DEFAULT)
    @Column(TeachersDBContract.Events.COURSE) public Course course;
    @ForeignKey(onDelete = ForeignKey.ReferentialAction.SET_DEFAULT)
    @Column(TeachersDBContract.Events.SUBJECT) public Subject subject;
    @Column(TeachersDBContract.Events.TYPE) public String type;

    // For use only through Ollie
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
            throw new InvalidDateTimeException(R.string.error_start_cannot_be_after_end);
        }
        start = newDateTime;
    }

    public void end(DateTime newDateTime) {
        if (newDateTime.isBefore(start)) {
            throw new InvalidDateTimeException(R.string.error_start_cannot_be_after_end);
        }
        end = newDateTime;
    }
}

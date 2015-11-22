package com.dlgdev.teachers.helpbook.domain.models;

import com.dlgdev.teachers.helpbook.domain.db.TeachersDBContract.TimeTableEntries;

import org.joda.time.DateTime;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.ForeignKey;
import ollie.annotation.Table;

@Table(TimeTableEntries.TABLE_NAME)
public class TimeTableEntry extends Model {
	@ForeignKey
	@Column(TimeTableEntries.SUBJECT) public Subject subject;
	@Column(TimeTableEntries.START) public DateTime start;
	@Column(TimeTableEntries.END) public DateTime end;

    public TimeTableEntry() {}
}

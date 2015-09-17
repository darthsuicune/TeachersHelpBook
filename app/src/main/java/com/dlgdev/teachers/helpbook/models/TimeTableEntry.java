package com.dlgdev.teachers.helpbook.models;

import com.dlgdev.teachers.helpbook.db.TeachersDBContract.TimeTableEntries;

import org.joda.time.DateTime;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table(TimeTableEntries.TABLE_NAME)
public class TimeTableEntry extends Model {
	@Column(TimeTableEntries.SUBJECT) Subject subject;
	@Column(TimeTableEntries.START) DateTime start;
	@Column(TimeTableEntries.END) DateTime end;
}

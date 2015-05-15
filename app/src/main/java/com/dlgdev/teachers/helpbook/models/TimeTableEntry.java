package com.dlgdev.teachers.helpbook.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract.TimeTableEntries;

import org.joda.time.DateTime;

@Table(name = TimeTableEntries.TABLE_NAME, id = TimeTableEntries._ID)
public class TimeTableEntry extends Model {
	@Column(name = TimeTableEntries.SUBJECT, index = true) Subject subject;
	@Column(name = TimeTableEntries.START) DateTime start;
	@Column(name = TimeTableEntries.END) DateTime end;
}

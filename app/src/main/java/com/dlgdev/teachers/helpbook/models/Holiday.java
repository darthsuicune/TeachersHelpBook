package com.dlgdev.teachers.helpbook.models;


import com.dlgdev.teachers.helpbook.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.ForeignKey;
import ollie.annotation.Table;

@Table(TeachersDBContract.Holidays.TABLE_NAME)
public class Holiday extends Model implements Listable {
	@Column(TeachersDBContract.Holidays.DATE) public DateTime date;
	@Column(TeachersDBContract.Holidays.NAME) public String name;
	@ForeignKey
	@Column(TeachersDBContract.Holidays.COURSE) public Course course;

	public static Holiday addBankHoliday(Course course, DateTime date, String name) {
		Holiday holiday = new Holiday();
		holiday.course = course;
		holiday.date = date;
		holiday.name = name;
		holiday.save();
		return holiday;
	}

	@Override public String title() {
		return name;
	}

	@Override public String description() {
		return Dates.formatDate(date);
	}
}

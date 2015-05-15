package com.dlgdev.teachers.helpbook.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dlgdev.teachers.helpbook.db.TeachersDBContract;
import com.dlgdev.teachers.helpbook.utils.Dates;

import org.joda.time.DateTime;

@Table(name = TeachersDBContract.Holidays.TABLE_NAME, id = TeachersDBContract.Holidays._ID)
public class Holiday extends Model implements Listable {
	@Column(name = TeachersDBContract.Holidays.DATE) public DateTime date;
	@Column(name = TeachersDBContract.Holidays.NAME) public String name;
	@Column(name = TeachersDBContract.Holidays.COURSE) public Course course;

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

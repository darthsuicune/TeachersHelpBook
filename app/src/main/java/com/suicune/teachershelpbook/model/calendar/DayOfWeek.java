package com.suicune.teachershelpbook.model.calendar;

import android.text.format.Time;

import com.suicune.teachershelpbook.R;

/**
 * Created by lapuente on 13.02.15.
 */
public enum DayOfWeek {
	MONDAY(0, R.string.monday),
	TUESDAY(1, R.string.tuesday),
	WEDNESDAY(2, R.string.wednesday),
	THURSDAY(3, R.string.thursday),
	FRIDAY(4, R.string.friday),
	SATURDAY(5, R.string.saturday),
	SUNDAY(6, R.string.sunday);

	public int nameResource;
	public int index;

	DayOfWeek(int index, int nameResource) {
		this.index = index;
		this.nameResource = nameResource;
	}

	public int nameRes() {
		return nameResource;
	}

	public boolean isWorkingDay() {
		return this != SUNDAY && this != SATURDAY;
	}

	public boolean inRange(Time startingTime, Time endingTime) {
		return index >= startingTime.weekDay && index <= endingTime.weekDay;
	}
}

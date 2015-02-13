package com.suicune.teachershelpbook.model.calendar;

import com.suicune.teachershelpbook.R;

/**
 * Created by lapuente on 13.02.15.
 */
public enum DayOfWeek {
	MONDAY(R.string.monday),
	TUESDAY(R.string.tuesday),
	WEDNESDAY(R.string.wednesday),
	THURSDAY(R.string.thursday),
	FRIDAY(R.string.friday),
	SATURDAY(R.string.saturday),
	SUNDAY(R.string.sunday);

	public int nameResource;

	DayOfWeek(int nameResource) {
		this.nameResource = nameResource;
	}

	public int nameRes() {
		return nameResource;
	}

	public boolean isWorkingDay() {
		return this != SUNDAY && this != SATURDAY;
	}

}

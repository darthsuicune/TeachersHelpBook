package com.suicune.teachershelpbook.model.calendar;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lapuente on 13.02.15.
 */
public class Week {
	public Date start;

	public Week(Date start) {
		this.start = start;
	}

	public Date day(DayOfWeek day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.set(Calendar.DAY_OF_WEEK, day.ordinal());
		return calendar.getTime();
	}
}

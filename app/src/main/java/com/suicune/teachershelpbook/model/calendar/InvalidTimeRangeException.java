package com.suicune.teachershelpbook.model.calendar;

import android.text.format.Time;

/**
 * Created by lapuente on 13.02.15.
 */
public class InvalidTimeRangeException extends RuntimeException {

	public InvalidTimeRangeException(Time start, Time end) {
		super();
	}
}

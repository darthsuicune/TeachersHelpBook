package com.suicune.teachershelpbook.model;

import android.text.TextUtils;
import android.text.format.Time;

/**
 * Created by lapuente on 13.02.15.
 */
public abstract class Event {
	Time start;
	Time end;
	String title;
	String description;

	public Event() {

	}

	public Event(Time time) {
		this.start = time;
		this.end = new Time(time);
		end.hour += 1;
	}

	public Event(Time start, Time end) {
		this.start = start;
		this.end = end;
	}

	public boolean isEmpty() {
		return TextUtils.isEmpty(title);
	}

	public boolean isAt(Time time) {
		return false;
	}

	public boolean isBetween(Time start, Time end) {
		return false;
	}
}

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

	public Event(Time start) {
		this.start = start;
		this.end = new Time(start);
		this.end.hour += 1;
	}

	public Event(Time start, Time end) {
		this.start = start;
		this.end = end;
	}

	public boolean isValid() {
		return TextUtils.isEmpty(title);
	}

	public boolean isAt(Time time) {
		return Time.compare(time, start) == 0 ||
                (time.after(start) && time.before(end)) ||
                Time.compare(time, end) == 0;
	}

	public boolean isBetween(Time start, Time end) {
		return isAt(start) || isAt(end) || this.start.before(start) || this.end.before(end);
	}
}

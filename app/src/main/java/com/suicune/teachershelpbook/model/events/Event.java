package com.suicune.teachershelpbook.model.events;

import android.text.TextUtils;
import android.text.format.Time;

import java.util.Date;

/**
 * Created by lapuente on 13.02.15.
 */
public abstract class Event {
	Time start;
	Time end;
	String title;
	String description;

	public Event(Time start, Time end) {
		this.start = start;
		this.end = end;
	}

	public boolean isValid() {
		return TextUtils.isEmpty(title);
	}

	public boolean isAt(Date date) {
		return Time.compare(date, start) == 0 ||
                (date.after(start) && date.before(end)) ||
                Time.compare(date, end) == 0;
	}

	public boolean isBetween(Time start, Time end) {
		return isAt(start) || isAt(end) || this.start.before(start) || this.end.before(end);
	}

	public void title(String title) {
		this.title = title;
	}

	public void description(String description) {
		this.description = description;
	}
}

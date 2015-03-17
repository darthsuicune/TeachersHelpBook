package com.suicune.teachershelpbook.model.events;

import com.suicune.teachershelpbook.R;

import org.joda.time.DateTime;

import java.io.Serializable;


public abstract class Event implements Serializable{
	public static final int DEFAULT_EVENT_DURATION_IN_HOURS = 1;
    DateTime start;
    DateTime end;
    String title;
    String description;

    public Event(DateTime start, DateTime end) {
        this.start = start;
        this.end = end;
    }

    public boolean isAt(DateTime time) {
        return time.compareTo(start) == 0 || (time.isAfter(start) && time.isBefore(end)) ||
                time.compareTo(end) == 0;
    }

    public boolean isBetween(DateTime start, DateTime end) {
        return start.compareTo(start) == 0 || end.compareTo(end) == 0 ||
                (this.start.isBefore(start) && this.end.isAfter(end));
    }

    public void title(String title) {
        this.title = title;
    }

    public void description(String description) {
        this.description = description;
    }

	public DateTime start() {
		return start;
	}

	public DateTime end() {
		return end;
	}

	public String title() {
		return title;
	}

	public String description() {
		return description;
	}

	public void start(DateTime newDateTime) {
        if(newDateTime.isAfter(end)) {
            throw new InvalidDateTimeException(R.string.start_cannot_be_after_end);
        }
		start = newDateTime;
	}

	public void end(DateTime newDateTime) {
        if(newDateTime.isBefore(start)) {
            throw new InvalidDateTimeException(R.string.start_cannot_be_after_end);
        }
		end = newDateTime;
	}
}

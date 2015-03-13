package com.suicune.teachershelpbook.model.events;

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

	public void start(DateTime date, DateTime newTime) {
		start = new DateTime(newTime).withDate(date.toLocalDate());
	}

	public void end(DateTime date, DateTime newTime) {
		end = new DateTime(newTime).withDate(date.toLocalDate());
	}

	public void startDate(DateTime date) {
		start = start.withDate(date.toLocalDate());
	}
	public void endDate(DateTime date) {
		end = end.withDate(date.toLocalDate());
	}
}

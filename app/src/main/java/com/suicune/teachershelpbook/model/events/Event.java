package com.suicune.teachershelpbook.model.events;

import org.joda.time.DateTime;


public abstract class Event {
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
}

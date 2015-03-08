package com.suicune.teachershelpbook.model.courses;

import android.content.ContentResolver;

import com.suicune.teachershelpbook.model.events.EventList;

import org.joda.time.DateTime;


public class Course {
	public DateTime start;
	public DateTime end;
	ContentResolver resolver;

	public Course(DateTime start, DateTime end, ContentResolver resolver) {
		this.start = start;
		this.end = end;
		this.resolver = resolver;
	}

	public EventList eventsBetween(DateTime start, DateTime end) {
		return null;
	}
}

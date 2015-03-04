package com.suicune.teachershelpbook.model.courses;

import android.content.ContentResolver;

import com.suicune.teachershelpbook.model.events.EventList;

import java.util.Date;

/**
 * Created by lapuente on 03.03.15.
 */
public class Course {
	public Date start;
	public Date end;
	ContentResolver resolver;

	public Course(Date start, Date end, ContentResolver resolver) {
		this.start = start;
		this.end = end;
		this.resolver = resolver;
	}

	public EventList eventsBetween(Date start, Date end) {
		return null;
	}
}

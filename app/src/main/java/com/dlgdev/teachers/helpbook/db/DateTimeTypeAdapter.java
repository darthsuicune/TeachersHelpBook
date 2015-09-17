package com.dlgdev.teachers.helpbook.db;

import org.joda.time.DateTime;

import ollie.TypeAdapter;

public class DateTimeTypeAdapter extends TypeAdapter<DateTime, Long> {
    @Override
    public Long serialize(DateTime dateTime) {
        return dateTime.getMillis();
    }

    @Override
    public DateTime deserialize(Long date) {
        return new DateTime(date.longValue());
    }
}

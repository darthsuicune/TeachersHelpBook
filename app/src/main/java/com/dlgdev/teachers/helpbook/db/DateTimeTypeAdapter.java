package com.dlgdev.teachers.helpbook.db;

import org.joda.time.DateTime;

import ollie.TypeAdapter;

public class DateTimeTypeAdapter extends TypeAdapter<DateTime, Long> {
    @Override
    public Long serialize(DateTime dateTime) {
        if(dateTime == null) {
            return Long.valueOf(0);
        }
        return dateTime.getMillis();
    }

    @Override
    public DateTime deserialize(Long date) {
        if(date == 0) {
            return null;
        }
        return new DateTime(date.longValue());
    }
}

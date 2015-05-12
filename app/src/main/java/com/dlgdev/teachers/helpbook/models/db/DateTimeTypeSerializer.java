package com.dlgdev.teachers.helpbook.models.db;

import com.activeandroid.serializer.TypeSerializer;

import org.joda.time.DateTime;

/**
 * Created by lapuente on 22.04.15.
 */
public class DateTimeTypeSerializer extends TypeSerializer {
	@Override public Class<?> getDeserializedType() {
		return DateTime.class;
	}

	@Override public Class<?> getSerializedType() {
		return Long.class;
	}

	@Override public Object serialize(Object data) {
		return ((DateTime) data).getMillis();
	}

	@Override public Object deserialize(Object data) {
		return new DateTime(((Long)data).longValue());
	}
}

package com.dlgdev.teachers.helpbook.utils;

public class InvalidDateTimeException extends RuntimeException {
	int resId;
	public InvalidDateTimeException(int errorResId) {
		this.resId = errorResId;
	}
	public int reason() {
		return resId;
	}
}

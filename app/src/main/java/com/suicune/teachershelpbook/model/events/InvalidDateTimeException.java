package com.suicune.teachershelpbook.model.events;

/**
 * Created by lapuente on 16.03.15.
 */
public class InvalidDateTimeException extends RuntimeException {
	int resId;
	public InvalidDateTimeException(int errorResId) {
		this.resId = errorResId;
	}
	public int reason() {
		return resId;
	}
}

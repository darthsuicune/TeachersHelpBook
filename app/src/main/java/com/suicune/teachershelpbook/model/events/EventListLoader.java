package com.suicune.teachershelpbook.model.events;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import java.util.Date;

/**
 * Created by lapuente on 06.03.15.
 */
public class EventListLoader extends AsyncTaskLoader<EventList> {
	public static final String KEY_END = "end";
	public static final String KEY_START = "start";
	private boolean isStarted;
	ContentResolver cr;
	Date startDate;
	Date endDate;

	public EventListLoader(Context context, Bundle args) {
		super(context);
		cr = context.getContentResolver();
		startDate = new Date(args.getLong(KEY_START));
		endDate = new Date(args.getLong(KEY_END));
	}

	@Override protected void onStartLoading() {
		super.onStartLoading();
		if(!isStarted) {
			forceLoad();
		}
	}

	@Override public EventList loadInBackground() {
		isStarted = true;
		Uri uri = null;
		String[] projection = null;
		String selection = null;
		String[] selectionArgs = null;
		//Cursor cursor = cr.query(uri, projection, selection, selectionArgs, null);
		return EventsProvider.listFromCursor(null);
	}
}

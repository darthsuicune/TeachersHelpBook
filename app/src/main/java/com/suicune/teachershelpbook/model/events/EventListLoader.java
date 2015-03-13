package com.suicune.teachershelpbook.model.events;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.content.AsyncTaskLoader;

import org.joda.time.DateTime;

public class EventListLoader extends AsyncTaskLoader<EventList> {
	public static final String KEY_END = "end";
	public static final String KEY_START = "start";
    EventsProvider provider;
    ContentResolver cr;
    DateTime startDate;
    DateTime endDate;

	public EventListLoader(Context context, Bundle args) {
		super(context);
		cr = context.getContentResolver();
		startDate = new DateTime(args.getLong(KEY_START));
		endDate = new DateTime(args.getLong(KEY_END));
	}

	@Override protected void onStartLoading() {
		super.onStartLoading();
		if(provider == null) {
			forceLoad();
		}
	}

	@Override public EventList loadInBackground() {
		provider = new EventsProvider();
		Uri uri = null;
		String[] projection = null;
		String selection = null;
		String[] selectionArgs = null;
		//Cursor cursor = cr.query(uri, projection, selection, selectionArgs, null);
		EventList list = provider.listFromCursor(null);
		//cursor.close();
		return list;
	}
}

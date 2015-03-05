package com.suicune.teachershelpbook.views.fragments.courses;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;

import java.util.Date;

/**
 * Created by lapuente on 04.03.15.
 */
public class WeeklyEventsFragment extends Fragment {
	private static final long MILLIS_IN_DAY = 24*60*60*1000;
	private static final int LOADER_EVENTS = 1;

	private WeeklyEventsListener eventsListener;
	private WeeklyPreviewListener previewListener;
	private Date referenceDate;
	private TextView dateView;

	public WeeklyEventsFragment() {
	}

	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			eventsListener = (WeeklyEventsListener) activity;
			previewListener = (WeeklyPreviewListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					activity.getLocalClassName() + " should implement the callback interfaces");
		}
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
									   @Nullable Bundle savedInstanceState) {
		View v;
		if(this.getId() == R.id.course_weekly_main_fragment) {
			v = inflater.inflate(R.layout.weekly_events_fragment, container, false);
			prepareMainLayout(v);
		} else {
			v = inflater.inflate(R.layout.weekly_events_preview_fragment, container, false);
			preparePreviewLayout(v);
		}
		return v;
	}

	private void prepareMainLayout(View v) {
		dateView = (TextView) v.findViewById(R.id.weekly_events_text);
	}

	private void preparePreviewLayout(View v) {
		dateView = (TextView) v.findViewById(R.id.weekly_events_preview_text);
		dateView.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				previewListener.onPreviewTapped(referenceDate);
			}
		});
	}

	public void updateDate(Date currentDate) {
		this.referenceDate = currentDate;
		if(dateView != null) {
			dateView.setText(currentDate.toString());
		}
		loadEvents();
	}

	//TODO: Implement
	private void loadEvents() {
//		Bundle args = new Bundle();
//		getLoaderManager().restartLoader(LOADER_EVENTS, args, new EventLoaderHelper());
	}

	public void updateDate(Date currentDate, int offsetInDays) {
		long time = currentDate.getTime();
		updateDate(new Date(time + offsetInDays * MILLIS_IN_DAY));
	}

	public interface WeeklyPreviewListener {
		public void onPreviewTapped(Date referenceDate);
	}

	public interface WeeklyEventsListener {
		public void onNewEventRequested(Time time);

		public void onExistingEventSelected(Event event);

		public void onNewDaySelected(Date newDate);
	}

	private class EventLoaderHelper implements LoaderManager.LoaderCallbacks<Cursor> {
		@Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			return null;
		}

		@Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

		}

		@Override public void onLoaderReset(Loader<Cursor> loader) {

		}
	}
}

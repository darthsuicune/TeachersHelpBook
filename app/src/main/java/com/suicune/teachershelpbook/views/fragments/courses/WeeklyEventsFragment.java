package com.suicune.teachershelpbook.views.fragments.courses;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
	protected int viewLayoutId;

	public WeeklyEventsFragment() {
	}

	public static WeeklyEventsFragment getInstance() {
		WeeklyEventsFragment fragment = new WeeklyEventsFragment();
		fragment.viewLayoutId = R.layout.weekly_events_fragment;
		return fragment;
	}

	public static WeeklyEventsFragment getPreviewInstance() {
		WeeklyEventsFragment fragment = new WeeklyEventsFragment();
		fragment.viewLayoutId = R.layout.weekly_events_preview_fragment;
		return fragment;
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
		loadEvents();
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
									   @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(viewLayoutId, container, false);
		prepareViews(v);
		return v;
	}

	private void prepareViews(View v) {
		if(viewLayoutId == R.layout.weekly_events_preview_fragment) {
			prepareMainLayout(v);
		} else {
			preparePreviewLayout(v);
		}
	}

	private void prepareMainLayout(View v) {

	}

	private void preparePreviewLayout(View v) {

	}

	public void currentDate(Date currentDate) {
		this.referenceDate = currentDate;
		loadEvents();
	}

	private void loadEvents() {
		Bundle args = new Bundle();
		getLoaderManager().restartLoader(LOADER_EVENTS, args, new EventLoaderHelper());
	}

	public void currentDate(Date currentDate, int offsetInDays) {
		long time = currentDate.getTime();
		currentDate(new Date(time + offsetInDays*MILLIS_IN_DAY));
	}

	public interface WeeklyPreviewListener {
		public void onPreviewTapped(Date referenceDate);
	}

	public interface WeeklyEventsListener {
		public void onNewEventRequested(Time time);

		public void onExistingEventSelected(Event event);

		public void onNewDaySelected(Date newDate);
	}

	private class EventLoaderHelper implements LoaderManager.LoaderCallbacks<Object> {
		@Override public Loader<Object> onCreateLoader(int id, Bundle args) {
			return null;
		}

		@Override public void onLoadFinished(Loader<Object> loader, Object data) {

		}

		@Override public void onLoaderReset(Loader<Object> loader) {

		}
	}
}

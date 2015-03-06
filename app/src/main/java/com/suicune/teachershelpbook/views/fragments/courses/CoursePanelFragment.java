package com.suicune.teachershelpbook.views.fragments.courses;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.EventList;
import com.suicune.teachershelpbook.model.events.EventListLoader;

import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 * Created by lapuente on 05.03.15.
 */
public class CoursePanelFragment extends Fragment {
	private static final int LOADER_COURSE = 1;
	private DateTime currentDate;
	private DateTime referenceDate;
	CoursePanelListener listener;
	TextView currentWeek;
	TextView referenceWeek;
	TextView eventCounter;

	public CoursePanelFragment() {
	}

	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (CoursePanelListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activities holding a CoursePanelFragment must implement CoursePanelListener");
		}
		loadCourseData();
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
									   @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.course_panel_fragment, container, false);
		prepareViews(v);
		return v;
	}

	private void prepareViews(View v) {
		currentWeek = (TextView) v.findViewById(R.id.current_week);
		referenceWeek = (TextView) v.findViewById(R.id.reference_week);
		eventCounter = (TextView) v.findViewById(R.id.event_counter);

		currentWeek.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onCurrentWeekTapped();
			}
		});
		eventCounter.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				listener.onEventCounterTapped();
			}
		});
	}

	public void updateDate(DateTime date) {
		this.referenceDate = date;
		Interval week = referenceDate.weekOfWeekyear().toInterval();
		DateTime startOfWeek = week.getStart();
		DateTime endOfWeek = week.getEnd();
		if (referenceWeek != null) {
			referenceWeek.setText(String.format("%d/%d/%d - %d/%d/%d", startOfWeek.getDayOfMonth(),
					startOfWeek.getMonthOfYear(), startOfWeek.getYear(), endOfWeek.getDayOfMonth(),
					endOfWeek.getMonthOfYear(), endOfWeek.getYear()));
		}
	}

	private void loadCourseData() {
		Bundle args = new Bundle();
		getLoaderManager().restartLoader(LOADER_COURSE, args, new CourseDataLoaderHelper());
	}

	public interface CoursePanelListener {
		public void onCurrentWeekTapped();

		public void onEventCounterTapped();
	}

	private class CourseDataLoaderHelper implements LoaderManager.LoaderCallbacks<EventList> {
		@Override public Loader<EventList> onCreateLoader(int id, Bundle args) {
			return new EventListLoader(getActivity(), args);
		}

		@Override public void onLoadFinished(Loader<EventList> loader, EventList data) {
			eventCounter.setText(getString(R.string.event_count, data.eventCount()));
		}

		@Override public void onLoaderReset(Loader<EventList> loader) {

		}
	}
}

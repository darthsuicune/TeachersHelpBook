package com.suicune.teachershelpbook.views.courses.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.EventList;
import com.suicune.teachershelpbook.model.events.EventListLoader;
import com.suicune.teachershelpbook.utils.Dates;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class CoursePanelFragment extends Fragment {
	private static final int LOADER_COURSE = 1;
	DateTime currentDate;
	DateTime referenceDate;
	CoursePanelListener listener;
	TextView currentWeek;
	TextView referenceWeek;
	TextView eventCounter;
	EventList eventList;

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
		currentWeek = (TextView) v.findViewById(R.id.current_date);
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
        showCurrentDate(date);
		this.referenceDate = date;
		Interval week = referenceDate.weekOfWeekyear().toInterval();
		DateTime startOfWeek = week.getStart();
		//week.getEnd() returns the first day of the next week.
		DateTime endOfWeek = week.getEnd().minusDays(1);
		if (referenceWeek != null) {
			referenceWeek.setText(Dates.formatRange(startOfWeek, endOfWeek));
		}
	}

    private void showCurrentDate(DateTime date) {
        if (currentDate == null) {
            currentDate = date;
            currentWeek.setText(Dates.formatDate(currentDate));
        }
    }

	private void loadCourseData() {
		Bundle args = new Bundle();
		getLoaderManager().restartLoader(LOADER_COURSE, args, new CourseDataLoaderHelper());
	}

	public void eventList(EventList eventList) {
		this.eventList = eventList;
		updateEventCounter();
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
            eventList(data);
		}

		@Override public void onLoaderReset(Loader<EventList> loader) {

		}
	}

    private void updateEventCounter() {
        eventCounter.setText(getString(R.string.event_count, eventList.eventCount()));
    }
}

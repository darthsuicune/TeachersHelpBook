package com.suicune.teachershelpbook.views.courses.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.Settings;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.EventList;
import com.suicune.teachershelpbook.model.events.EventListLoader;
import com.suicune.teachershelpbook.utils.Dates;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import static org.joda.time.DateTimeConstants.MONDAY;
import static org.joda.time.DateTimeConstants.SATURDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;

public class WeeklyEventsFragment extends Fragment {
    private static final int LOADER_EVENTS = 1;

    WeeklyEventsListener eventsListener;
    WeeklyPreviewListener previewListener;
    DateTime referenceDate;
    DateTime startOfWeek;
    DateTime endOfWeek;
    int startingDayOfWeek;
    int endingDayOfWeek;
    TextView dateView;
    EventList eventList;
    private SharedPreferences prefs;

    public WeeklyEventsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            eventsListener = (WeeklyEventsListener) activity;
            previewListener = (WeeklyPreviewListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.getLocalClassName() + " should implement the callback interfaces");
        }
        prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        startingDayOfWeek = prefs.getInt(Settings.FIRST_DAY_OF_WEEK, MONDAY);
        endingDayOfWeek = (startingDayOfWeek == MONDAY) ? SUNDAY : SATURDAY;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v;
        if (this.getId() == R.id.course_weekly_main_fragment) {
            v = inflater.inflate(R.layout.weekly_events_fragment, container, false);
            prepareMainLayout(v);
        } else {
            v = inflater.inflate(R.layout.weekly_events_preview_fragment, container, false);
            preparePreviewLayout(v);
        }
        return v;
    }

    private void prepareMainLayout(View v) {
    }

    private void preparePreviewLayout(View v) {
        dateView = (TextView) v.findViewById(R.id.weekly_events_preview_text);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previewListener.onPreviewTapped(referenceDate);
            }
        });
    }

    public void updateDate(DateTime currentDate) {
        this.referenceDate = currentDate;
        Interval week = referenceDate.weekOfWeekyear().toInterval();
        startOfWeek = week.getStart();
        endOfWeek = week.getEnd();
        if (dateView != null) {
            dateView.setText(Dates.formatRange(startOfWeek, endOfWeek));
        }
        loadEvents();
    }

    //TODO: Implement
    private void loadEvents() {
        Bundle args = new Bundle();
        args.putLong(EventListLoader.KEY_START, startOfWeek.getMillis());
        args.putLong(EventListLoader.KEY_END, endOfWeek.getMillis());
        getLoaderManager().restartLoader(LOADER_EVENTS, args, new EventLoaderHelper());
    }

    private void updateEventList(EventList data) {
        this.eventList = data;
    }

    public interface WeeklyPreviewListener {
        public void onPreviewTapped(DateTime referenceDate);
    }

    public interface WeeklyEventsListener {
        public void onNewEventRequested(Period period);

        public void onExistingEventSelected(Event event);

        public void onNewDaySelected(DateTime newDate);
    }

    private class EventLoaderHelper implements LoaderManager.LoaderCallbacks<EventList> {
        @Override
        public Loader<EventList> onCreateLoader(int id, Bundle args) {
            return new EventListLoader(getActivity(), args);
        }

        @Override
        public void onLoadFinished(Loader<EventList> loader, EventList data) {
            updateEventList(data);
        }

        @Override
        public void onLoaderReset(Loader<EventList> loader) {

        }
    }
}

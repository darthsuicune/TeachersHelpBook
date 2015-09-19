package com.dlgdev.teachers.helpbook.views.events;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.models.Event;
import com.dlgdev.teachers.helpbook.models.EventList;
import com.dlgdev.teachers.helpbook.utils.Dates;
import com.dlgdev.views.CardWithBackground;
import com.dlgdev.views.DividerWrappedRecyclerView;

import org.joda.time.DateTime;

import java.util.List;


public class DailyEventsCardView extends CardWithBackground {
    EventActionsListener listener;
    EventList events;
    DateTime date;

    TextView dateView;
    TextView emptyEventListView;
    TextView addNewView;
    DividerWrappedRecyclerView eventListView;

    EventListAdapter adapter;

    public DailyEventsCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setup(EventActionsListener listener, DateTime newDate) {
        this.listener = listener;
        this.date = newDate;
        loadViews();
        setupViewParameters();
        setBackground();
    }

    private void loadViews() {
        this.dateView = (TextView) findViewById(R.id.daily_event_card_date);
        this.addNewView = (TextView) findViewById(R.id.daily_event_card_add_new);
        this.eventListView = (DividerWrappedRecyclerView) findViewById(R.id.event_list);
        this.emptyEventListView = (TextView) findViewById(R.id.event_list_empty);
    }

    private void setupViewParameters() {
        dateView.setText(Dates.formatDate(date));
        addNewView.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                newEventRequested();
            }
        });
    }

    private void newEventRequested() {
        listener.onNewEventRequested(date);
    }

    private void setBackground() {
        int color;
        if (date.dayOfYear().equals(new DateTime().dayOfYear())) {
            color = Color.parseColor("#80cbc4");
        } else {
            color = Color.parseColor("#fafafa");
        }
        setCardBackgroundColor(color);
    }

    public void updateEvents(EventList eventList) {
        events = eventList;
        if (events.eventCount() == 0) {
            eventListView.setVisibility(View.GONE);
            emptyEventListView.setVisibility(View.VISIBLE);
        } else {
            eventListView.setVisibility(View.VISIBLE);
            emptyEventListView.setVisibility(View.GONE);
            updateEventList();
        }
    }

    private void updateEventList() {
        if (adapter == null) {
            adapter = new EventListAdapter();
            eventListView.setAdapter(adapter);
        }
        adapter.swapItems(events);
    }

    private class EventListAdapter extends RecyclerView.Adapter<EventViewHolder> {
        List<Event> events;

        public EventListAdapter() {
            events = DailyEventsCardView.this.events.events();
        }

        @Override public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getContext())
                    .inflate(R.layout.event_view_for_list, parent, false);
            EventViewHolder holder = new EventViewHolder(v, listener);
            v.setOnClickListener(holder);
            return holder;
        }

        @Override public void onBindViewHolder(EventViewHolder holder, int position) {
            final Event event = events.get(position);
            holder.event(event);
        }

        @Override public int getItemCount() {
            return events.size();
        }

        public void swapItems(EventList list) {
            this.events = list.events();
            notifyDataSetChanged();
        }
    }

}

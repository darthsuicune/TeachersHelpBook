package com.dlgdev.teachers.helpbook.views.events;

import android.content.Context;
import android.support.v7.widget.CardView;
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
import com.dlgdev.teachers.helpbook.views.WrappedLayoutManager;
import com.example.android.supportv7.widget.decorator.DividerItemDecoration;

import org.joda.time.DateTime;

import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;


public class DailyEventsCardView extends CardView {
    DailyEventsCardListener listener;
    EventList events;
    DateTime date;

    TextView dateView;
    TextView emptyEventListView;
    TextView addNewView;
    RecyclerView eventListView;

    EventListAdapter adapter;

    public DailyEventsCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setup(DailyEventsCardListener fragment, DateTime newDate) {
        this.listener = fragment;
        this.date = newDate;
        loadViews();
        setupViewParameters();
        setBackground();
    }

    private void loadViews() {
        this.dateView = (TextView) findViewById(R.id.daily_event_card_date);
        this.addNewView = (TextView) findViewById(R.id.daily_event_card_add_new);
        this.eventListView = (RecyclerView) findViewById(R.id.event_list);
        this.emptyEventListView = (TextView) findViewById(R.id.event_list_empty);
    }

    private void setupViewParameters() {
        eventListView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
        eventListView.setLayoutManager(new WrappedLayoutManager(getContext()));
        dateView.setText(Dates.formatDate(date));
        addNewView.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                newEventRequested();
            }
        });
    }

    private void setBackground() {
        int color;
        if (date.dayOfYear().equals(new DateTime().dayOfYear())) {
            color = getResources().getColor(R.color.material_deep_teal_200);
        } else {
            color = getResources().getColor(R.color.background_material_light);
        }
        setBackgroundColor(color);
    }

    private void newEventRequested() {
        listener.onNewEventRequested(date);
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

    public interface DailyEventsCardListener {
        void onNewEventRequested(DateTime date);

        void onEventSelected(Event event);
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

package com.suicune.teachershelpbook.views.events;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.supportv7.widget.decorator.DividerItemDecoration;
import com.suicune.teachershelpbook.R;
import com.suicune.teachershelpbook.model.events.Event;
import com.suicune.teachershelpbook.model.events.EventList;
import com.suicune.teachershelpbook.utils.Dates;
import com.suicune.teachershelpbook.views.WrappedLayoutManager;

import org.joda.time.DateTime;

import java.util.List;


public class DailyEventsCardView extends CardView {
	NewEventsRequestedListener listener;
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

	public void setup(NewEventsRequestedListener fragment, DateTime newDate) {
		this.listener = fragment;
		this.date = newDate;
		loadViews();
		setupViewParameters();
	}

	private void loadViews() {
		this.dateView = (TextView) findViewById(R.id.date);
		this.addNewView = (TextView) findViewById(R.id.add_new);
		this.eventListView = (RecyclerView) findViewById(R.id.event_list);
		this.emptyEventListView = (TextView) findViewById(R.id.event_list_empty);
	}

	private void setupViewParameters() {
		eventListView.addItemDecoration(
				new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
		eventListView.setLayoutManager(new WrappedLayoutManager(getContext()));
		dateView.setText(Dates.formatDate(date));
		addNewView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
				listener.onNewEventRequested(date);
			}
		});
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

	public interface NewEventsRequestedListener {
		void onNewEventRequested(DateTime date);
	}

	private class EventListAdapter extends RecyclerView.Adapter<EventViewHolder> {
		List<Event> events;

		public EventListAdapter() {
			events = DailyEventsCardView.this.events.events();
		}

		@Override public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(getContext())
					.inflate(R.layout.event_view_for_list, parent, false);
			return new EventViewHolder(v);
		}

		@Override public void onBindViewHolder(EventViewHolder holder, int position) {
			Event event = events.get(position);
			holder.name(event.title());
			holder.time(Dates.formatTimeRange(event.start(), event.end()));
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

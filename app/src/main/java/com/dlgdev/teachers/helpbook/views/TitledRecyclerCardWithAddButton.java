package com.dlgdev.teachers.helpbook.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.example.android.supportv7.widget.decorator.DividerItemDecoration;

import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;


public class TitledRecyclerCardWithAddButton<T> extends CardView {
	RecyclerCardListener listener;
	List<T> items;
	String title;

	TextView titleView;
	TextView emptyListView;
	TextView addNewView;
	RecyclerView listView;
	int recyclerItemLayoutResId;

	ItemListAdapter<T> adapter;

	public TitledRecyclerCardWithAddButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setup(RecyclerCardListener listener, String title, int recyclerItemLayoutId) {
		this.listener = listener;
		this.title = title;
		this.recyclerItemLayoutResId = recyclerItemLayoutId;
		loadViews();
		setupViewParameters();
	}

	private void loadViews() {
		this.titleView = (TextView) findViewById(R.id.card_title);
		this.addNewView = (TextView) findViewById(R.id.card_add_new);
		this.listView = (RecyclerView) findViewById(R.id.card_list);
		this.emptyListView = (TextView) findViewById(R.id.card_list_empty);
	}

	private void setupViewParameters() {
		listView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
		listView.setLayoutManager(new WrappedLayoutManager(getContext()));
		titleView.setText(title);
		addNewView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {

				listener.onNewItemRequested();
			}
		});
	}

	public void updateItems(List<T> itemList) {
		items = itemList;
		if (itemList.size() == 0) {
			listView.setVisibility(View.GONE);
			emptyListView.setVisibility(View.VISIBLE);
		} else {
			listView.setVisibility(View.VISIBLE);
			emptyListView.setVisibility(View.GONE);
			updateItemList();
		}
	}

	private void updateItemList() {
		if (adapter == null) {
			adapter = new ItemListAdapter<>(items, recyclerItemLayoutResId);
			listView.setAdapter(adapter);
		}
		adapter.swapItems(items);
	}

	public interface RecyclerCardListener<T> {
		void onNewItemRequested();

		void onItemSelected(T t);
	}

	private class ItemListAdapter<U> extends RecyclerView.Adapter<ClickableViewHolder<U>> {
		List<U> items;
		int itemLayoutResId;

		public ItemListAdapter(List<U> list, int itemLayoutResId) {
			items = list;
			this.itemLayoutResId = itemLayoutResId;
		}

		@Override public ClickableViewHolder<U> onCreateViewHolder(ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(getContext()).inflate(itemLayoutResId, parent, false);
			ClickableViewHolder<U> holder = new ClickableViewHolder<>(v);
			v.setOnClickListener(holder);
			return holder;
		}

		@Override public void onBindViewHolder(ClickableViewHolder<U> holder, int position) {
			final U item = items.get(position);
			holder.item(item);
		}

		@Override public int getItemCount() {
			return items.size();
		}

		public void swapItems(List<U> list) {
			this.items = list;
			notifyDataSetChanged();
		}
	}

}

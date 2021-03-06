package com.dlgdev.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.domain.models.Listable;
import com.example.android.supportv7.widget.decorator.DividerItemDecoration;

import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;


public class TitledRecyclerCardWithAddButton extends CardWithBackground {
	RecyclerCardListener listener;
	List<? extends Listable> items;
	String title;

	TextView titleView;
	TextView emptyListView;
	TextView addNewView;
	RecyclerView listView;

	ItemListAdapter adapter;

	public TitledRecyclerCardWithAddButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setup(String title, String emptyListText, RecyclerCardListener listener) {
		this.listener = listener;
		this.title = title;
		loadViews();
		setupViewParameters();
		this.emptyListView.setText(emptyListText);
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

	public void updateItems(List<? extends Listable> itemList) {
		items = itemList;
		if (itemList.size() == 0) {
			listView.setVisibility(GONE);
			emptyListView.setVisibility(VISIBLE);
		} else {
			listView.setVisibility(VISIBLE);
			emptyListView.setVisibility(GONE);
			updateItemList();
		}
	}

	private void updateItemList() {
		if (adapter == null) {
			adapter = new ItemListAdapter(items);
			listView.setAdapter(adapter);
		}
		adapter.swapItems(items);
	}

	public interface RecyclerCardListener {
		void onNewItemRequested();

		<T extends Listable> void onItemSelected(T t);
	}

	private class ItemListAdapter extends RecyclerView.Adapter<ListElementViewHolder>
			implements ListElementViewHolder.RecyclerItemListener {
		List<? extends Listable> items;

		public ItemListAdapter(List<? extends Listable> list) {
			items = list;
		}

		@Override public ListElementViewHolder onCreateViewHolder(
				ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
			ListElementViewHolder holder = new ListElementViewHolder(v, this);
			v.setOnClickListener(holder);
			return holder;
		}

		@Override
		public void onBindViewHolder(ListElementViewHolder holder, int position) {
			holder.position(position);
			holder.title(items.get(position).title());
			holder.description(items.get(position).description());
		}

		@Override public int getItemCount() {
			return items.size();
		}

		public void swapItems(List<? extends Listable> list) {
			this.items = list;
			notifyDataSetChanged();
		}

		@Override public void onItemSelected(int position) {
			listener.onItemSelected(items.get(position));
		}
	}

}

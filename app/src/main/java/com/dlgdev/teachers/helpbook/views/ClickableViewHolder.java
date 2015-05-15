package com.dlgdev.teachers.helpbook.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dlgdev.teachers.helpbook.views.TitledRecyclerCardWithAddButton.RecyclerCardListener;

public class ClickableViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
	RecyclerCardListener<T> listener;
	T item;

	public ClickableViewHolder(View itemView, RecyclerCardListener<T> listener) {
		super(itemView);
		this.listener = listener;
	}

	@Override public void onClick(View view) {
		listener.onItemSelected(item);
	}

	public void item(T item) {
		this.item = item;
	}
}

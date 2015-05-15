package com.dlgdev.teachers.helpbook.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ClickableViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
	T item;

	public ClickableViewHolder(View itemView) {
		super(itemView);
	}

	@Override public void onClick(View view) {
		//TODO
	}

	public void item(T item) {
		this.item = item;
	}
}

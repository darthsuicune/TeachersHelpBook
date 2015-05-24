package com.dlgdev.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ClickableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
	int position;
	public RecyclerItemListener listener;

	public ClickableViewHolder(View itemView, RecyclerItemListener listener) {
		super(itemView);
		this.listener = listener;
	}

	@Override public void onClick(View view) {
		listener.onItemSelected(position);
	}

	public void position(int position) {
		this.position = position;
	}

	public interface RecyclerItemListener {
		void onItemSelected(int position);
	}
}

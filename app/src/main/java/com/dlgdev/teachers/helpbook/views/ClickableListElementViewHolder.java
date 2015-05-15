package com.dlgdev.teachers.helpbook.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;

public class ClickableListElementViewHolder extends RecyclerView.ViewHolder
		implements View.OnClickListener {
	RecyclerItemListener listener;
	int position;
	TextView titleView;
	TextView descriptionView;

	public ClickableListElementViewHolder(View itemView, RecyclerItemListener listener) {
		super(itemView);
		this.listener = listener;
		titleView = (TextView) itemView.findViewById(R.id.item_list_title);
		descriptionView = (TextView) itemView.findViewById(R.id.item_list_description);
	}

	@Override public void onClick(View view) {
		listener.onItemSelected(position);
	}

	public void title(String title) {
		titleView.setText(title);
	}

	public void description(String description) {
		descriptionView.setText(description);
	}

	public void position(int position) {
		this.position = position;
	}

	public interface RecyclerItemListener {
		void onItemSelected(int position);
	}
}

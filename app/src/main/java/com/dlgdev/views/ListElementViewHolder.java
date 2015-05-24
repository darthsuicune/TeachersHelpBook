package com.dlgdev.views;

import android.view.View;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;

public class ListElementViewHolder extends ClickableViewHolder {
	TextView titleView;
	TextView descriptionView;

	public ListElementViewHolder(View itemView, RecyclerItemListener listener) {
		super(itemView, listener);
		titleView = (TextView) itemView.findViewById(R.id.item_list_title);
		descriptionView = (TextView) itemView.findViewById(R.id.item_list_description);
	}

	public void title(String title) {
		titleView.setText(title);
	}

	public void description(String description) {
		descriptionView.setText(description);
	}
}

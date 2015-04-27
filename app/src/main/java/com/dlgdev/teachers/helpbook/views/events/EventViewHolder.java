package com.dlgdev.teachers.helpbook.views.events;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dlgdev.teachers.helpbook.R;

/**
 * Created by lapuente on 11.03.15.
 */
public class EventViewHolder extends RecyclerView.ViewHolder {
		TextView name;
		TextView time;

		public EventViewHolder(View itemView) {
			super(itemView);
			name = (TextView) itemView.findViewById(R.id.event_entry_name);
			time = (TextView) itemView.findViewById(R.id.event_entry_time);
		}

		public void name(String newName) {
			name.setText(newName);
		}

		public void time(String newTime) {
			time.setText(newTime);
		}

}

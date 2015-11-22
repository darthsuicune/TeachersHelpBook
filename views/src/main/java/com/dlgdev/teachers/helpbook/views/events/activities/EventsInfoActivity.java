package com.dlgdev.teachers.helpbook.views.events.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dlgdev.teachers.helpbook.R;
import com.dlgdev.teachers.helpbook.views.ModelInfoActivity;

public class EventsInfoActivity extends ModelInfoActivity {

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_info);
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_event_info, menu);
		return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}
}

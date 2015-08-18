package com.dlgdev.teachers.helpbook.views.events.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlgdev.teachers.helpbook.R;

public class EventInfoFragment extends Fragment {

	@Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
												 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_event_info, container, false);
		setupViews(v);
		return v;
	}

	public void setupViews(View rootView) {

	}
}

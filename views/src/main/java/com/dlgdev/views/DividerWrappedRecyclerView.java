package com.dlgdev.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.android.supportv7.widget.decorator.DividerItemDecoration;

public class DividerWrappedRecyclerView extends RecyclerView {
	public DividerWrappedRecyclerView(Context context) {
		super(context);
		setup();
	}

	public DividerWrappedRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup();
	}

	public DividerWrappedRecyclerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup();
	}

	private void setup() {
		addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
		setLayoutManager(new WrappedLayoutManager(getContext()));
	}
}

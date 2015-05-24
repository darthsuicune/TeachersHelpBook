package com.dlgdev.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

public class CardWithBackground extends CardView {
	int backgroundColor;
	public CardWithBackground(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	@Override public void setCardBackgroundColor(int color) {
		super.setCardBackgroundColor(color);
		this.backgroundColor = color;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}
}

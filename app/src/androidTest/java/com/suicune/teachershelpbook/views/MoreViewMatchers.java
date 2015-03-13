package com.suicune.teachershelpbook.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class MoreViewMatchers {
	public static Matcher<View> instance(final View v) {
		return new TypeSafeMatcher<View>() {
			@Override public boolean matchesSafely(View view) {
				return v.equals(view);
			}

			@Override public void describeTo(Description description) {
				description.appendText("The view should match instance: ").appendValue(v);
			}
		};
	}

	public static Matcher<View> hasChildren(final int count) {
		return new TypeSafeMatcher<View>() {
			@Override public boolean matchesSafely(View view) {
				int adapterCount;
				if (view instanceof AbsListView) {
					adapterCount = ((AbsListView) view).getAdapter().getCount();
				} else if (view instanceof RecyclerView) {
					adapterCount = ((RecyclerView) view).getAdapter().getItemCount();
				} else {
					adapterCount = 0;
				}
				return adapterCount == count;
			}

			@Override public void describeTo(Description description) {
				description.appendText("The view should have children count: ").appendValue(count);
			}
		};
	}

	public static Matcher<View> hasText(final String s) {
		return new TypeSafeMatcher<View>() {
			@Override public boolean matchesSafely(View view) {
				TextView tv = (TextView) view;
				return tv.getText().toString().contains(s);
			}

			@Override public void describeTo(Description description) {
				description.appendText("It should include the text: ").appendValue(s);
			}
		};
	}

	public static Matcher<View> backgroundIs(final int color) {
		return new TypeSafeMatcher<View>() {
			int detectedColor = 0;
			@Override public boolean matchesSafely(View view) {
				ColorDrawable drawable = (ColorDrawable) view.getBackground();
				detectedColor = drawable.getColor();
				return detectedColor == color;
			}

			@Override public void describeTo(Description description) {
				description.appendText("The background should match color ").appendValue(color)
				.appendText("but was ").appendValue(detectedColor);
			}
		};
	}

	private static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}
		int width = (drawable.getIntrinsicWidth() > 0) ? drawable.getIntrinsicWidth() : 1;
		int height = (drawable.getIntrinsicHeight() > 0) ? drawable.getIntrinsicHeight() : 1;

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}
}

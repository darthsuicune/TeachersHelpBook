package com.suicune.teachershelpbook.views.fragments.courses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suicune.teachershelpbook.R;

import java.util.Date;

/**
 * Created by lapuente on 05.03.15.
 */
public class CoursePanelFragment extends Fragment{
	private Date referenceDate;
	public CoursePanelFragment() {}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
									   @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.course_panel_fragment, container, false);
	}

	public void updateDate(Date date) {
		this.referenceDate = date;
	}

}

package com.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.grasshopper.newsapp.R;

public class Competition extends Fragment {

	Spinner competition_anwers_spinner;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.competition, container, false);

		init(view);
		return view;
	}

	private void init(View v) {

		competition_anwers_spinner = (Spinner) v
				.findViewById(R.id.competition_anwers_spinner);

		List<String> list = new ArrayList<String>();
		list.add("list 1");
		list.add("list 2");
		list.add("list 3");
		list.add("baby over 1");
		list.add("baby over 2");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		competition_anwers_spinner.setAdapter(dataAdapter);
	}
}

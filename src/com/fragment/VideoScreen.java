package com.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.adapter.VideoScreenlistAdapter;
import com.grasshopper.newsapp.R;
import com.model.VideoScreenModel;

public class VideoScreen extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.video_screen, container, false);

		ListView video_screen_list = (ListView) view
				.findViewById(R.id.video_screen_list);

		ArrayList<VideoScreenModel> mainmodel = new ArrayList<VideoScreenModel>();

		for (int i = 0; i < 10; i++) {
			VideoScreenModel model = new VideoScreenModel();
			mainmodel.add(model);
		}

		VideoScreenlistAdapter adapter = new VideoScreenlistAdapter(
				getActivity(), 0, mainmodel);

		video_screen_list.setAdapter(adapter);

		return view;
	}

}

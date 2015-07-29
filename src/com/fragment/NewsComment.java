package com.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapter.NewsCommentAdapter;
import com.grasshopper.newsapp.R;
import com.model.NewsCommentModel;

import eu.erikw.PullToRefreshListView;

public class NewsComment extends Fragment {

	Context context;

	View view;

	ArrayList<NewsCommentModel> mainmodel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.news_comment, container, false);

		mainmodel = new ArrayList<NewsCommentModel>();

		for (int i = 0; i < 10; i++) {

			NewsCommentModel model = new NewsCommentModel();

			mainmodel.add(model);

		}

		PullToRefreshListView news_comment_list = (PullToRefreshListView) view
				.findViewById(R.id.news_comment_list);

		NewsCommentAdapter adapter = new NewsCommentAdapter(context, 0,
				mainmodel);

		news_comment_list.setAdapter(adapter);

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		context = activity;
	}

}

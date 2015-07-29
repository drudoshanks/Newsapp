package com.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.adapter.Top_news_adapter;
import com.grasshopper.newsapp.HomeScreen;
import com.grasshopper.newsapp.R;
import com.model.Top_news_Model;
import com.utils.Session;
import com.utils.Utils;
import com.webservice.CallService;
import com.webservice.CallService.OnServicecall;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TopNews extends Fragment {

	ArrayList<Top_news_Model> main_model = new ArrayList<Top_news_Model>();
	Session session;
	Top_news_Model model;
	ProgressBar list_refresh_progressbar;
	View view;
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.top_news, container, false);

		// TODO test comment

		init();

		// ends here
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = activity;
	}

	protected void init() {
		// initiate the session
		session = ((HomeScreen) getActivity()).session;

		list_refresh_progressbar = (ProgressBar) view
				.findViewById(R.id.list_refresh_progressbar);

		// if (session.getTopNews().equalsIgnoreCase(""))
		getData();
		// else
		// JSONPARSOR(session.getTopNews());
	}

	private void getData() {

		if (session != null && context != null) {
			String urlStr = Utils._url + "getTopStory.php?client_id="
					+ session.getClient_id() + "&lang_id="
					+ session.getLang_id() + "&cat_id=0";

			CallService getListdata = new CallService(context, urlStr,
					Utils.GET, new OnServicecall() {

						@Override
						public void onServicecall(String response) {
							// TODO Auto-generated method stub
							// session.setTopNews(response);

							JSONPARSOR(response);
						}
					});
			getListdata.execute();
		}
	}

	private void JSONPARSOR(String response) {

		if (list_refresh_progressbar.getVisibility() == View.VISIBLE)
			list_refresh_progressbar.setVisibility(View.GONE);
		if (main_model.size() > 0)
			main_model.clear();

		boolean is_left = true;
		try {

			JSONArray jarr = new JSONArray(response);
			Utils.TOP_ADDED_NEWS = jarr.length();
			ArrayList<String> passingUrls = new ArrayList<String>();
			for (int i = 0; i < jarr.length(); i++) {

				JSONObject jobj = jarr.getJSONObject(i);
				int story_id = Integer.parseInt(jobj.getString("story_id"));
				String url = jobj.getString("story_url");

				passingUrls.add(url);

				// these are in the inner array
				String propertyValue = "";
				String subpropertyValue = "";
				String pub_date = "";
				String assetUrl = "";
				JSONArray innerJar = jobj.getJSONArray("propert_array");
				for (int j = 0; j < innerJar.length(); j++) {

					JSONObject inneObj = innerJar.getJSONObject(j);

					assetUrl = inneObj.getString("assetid");

					String TAG = inneObj.getString("tag");

					if (TAG.equalsIgnoreCase("TP")) {

					} else if (TAG.equalsIgnoreCase("HD")) {
						propertyValue = inneObj.getString("value");
					} else if (TAG.equalsIgnoreCase("CS")) {

						// content
						subpropertyValue = inneObj.getString("value");
					} else if (TAG.equalsIgnoreCase("PB")) {

					} else if (TAG.equalsIgnoreCase("CD")) {
						// created date
						pub_date = inneObj.getString("value");
					}

				}

				if (i == 0) {

					Top_news_Model model = new Top_news_Model();
					model.setTitle(propertyValue);
					model.setTitle_sub(subpropertyValue);
					model.setImage_url(assetUrl);
					model.setItem_type(0);
					model.setPassing_url(url);

					model.setCount(i);
					main_model.add(model);
				} else {

					if (is_left) {

						model = new Top_news_Model();
						model.setItem_type(1);
						model.setTitle_left(propertyValue);
						model.setTitle_sub_left(subpropertyValue);
						model.setImage_url_left(assetUrl);
						model.setPassing_url_left(url);
						model.setLeftCount(i);
						is_left = false;
						if (i == (jarr.length() - 1)) {
							main_model.add(model);
						}

					} else {
						model.setTitle_right(propertyValue);
						model.setTitle_sub_right(subpropertyValue);
						model.setImage_url_right(assetUrl);
						model.setPassing_url_right(url);
						model.setRightCount(i);
						is_left = true;
						main_model.add(model);
					}

				}
			}
			if (main_model != null && main_model.size() > 0) {

				final Top_news_adapter adapter = new Top_news_adapter(
						getActivity(), 0, main_model, TopNews.this, passingUrls);

				PullToRefreshListView list = (PullToRefreshListView) view
						.findViewById(R.id.to_news_list);
				list.setAdapter(adapter);

				// list.setOnItemClickListener(new OnItemClickListener() {
				//
				// @Override
				// public void onItemClick(AdapterView<?> parent,
				// View view, int position, long id) {
				// // TODO Auto-generated method stub
				// main_model.get(position).setReadHead(true);
				// main_model.get(position).setReadLeft(true);
				// main_model.get(position).setReadRight(true);
				// adapter.notifyDataSetChanged();
				// }
				// });

				list.setOnRefreshListener(new OnRefreshListener() {

					@Override
					public void onRefresh() {
						// TODO Auto-generated method stub
						getData();
					}
				});

				if (list.isRefreshing())
					list.onRefreshComplete();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}

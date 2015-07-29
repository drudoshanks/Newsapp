package com.fragment.video;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.adapter.VideoScreenlistAdapter;
import com.grasshopper.newsapp.HomeScreen;
import com.grasshopper.newsapp.R;
import com.model.VideoScreenModel;
import com.utils.Session;
import com.utils.Utils;
import com.webservice.CallService;
import com.webservice.CallService.OnServicecall;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class VideoList extends Fragment {

	private static final String ARG_POSITION = "position";

	Session session;
	int cat_id;
	VideoList f;
	ArrayList<VideoScreenModel> mainmodel;
	View view;

	Context context;

	public static VideoList newInstance(int cat_id) {
		VideoList f = new VideoList();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, cat_id);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		context = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.video_screen, container, false);

		Bundle bundle = this.getArguments();
		cat_id = bundle.getInt(ARG_POSITION, 0);

		// initiate the session
		session = ((HomeScreen) getActivity()).session;

		// getting all the data
		getData();

		return view;
	}

	private void getData() {

		// mainmodel = db.getAllStories(cat_id);
		// if (mainmodel != null) {
		//
		// setListView();
		//
		// } else {

		mainmodel = new ArrayList<VideoScreenModel>();

		String urlStr = Utils._url + "getStory.php?client_id="
				+ session.getClient_id() + "&lang_id=" + session.getLang_id()
				+ "&cat_id=" + cat_id;

		CallService getNewsList = new CallService(context, urlStr, Utils.GET,
				new OnServicecall() {

					@Override
					public void onServicecall(String response) {
						// TODO Auto-generated method stub

						JSONPARSOR(response);

					}
				});
		getNewsList.execute();
		// }

	}

	private void JSONPARSOR(String response) {
		if (mainmodel.size() > 0)
			mainmodel.clear();

		try {

			JSONArray jarr = new JSONArray(response);
			for (int i = 0; i < jarr.length(); i++) {
				JSONObject jobj = jarr.getJSONObject(i);
				String story_id = jobj.getString("story_id");
				String url = jobj.getString("story_url");

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

				// endsr here

				VideoScreenModel model = new VideoScreenModel();
				model.setVideo_screen_model_id(story_id);
				model.setVideo_screen_passing_url(url);
				model.setVideo_screen_image_url(assetUrl);
				model.setVideo_screen_title(propertyValue);
				// model.setNews_list_title_sub(subpropertyValue);
				model.setVideo_screen_time(pub_date);

				mainmodel.add(model);

			}

			// add main model to database
			// db.addStories(mainmodel, cat_id);

			setListView();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	private void setListView() {

		if (mainmodel != null && mainmodel.size() > 0 && context != null) {
			VideoScreenlistAdapter adapter = new VideoScreenlistAdapter(
					context, 0, mainmodel);

			PullToRefreshListView sub_category_list = (PullToRefreshListView) view
					.findViewById(R.id.video_screen_list);
			sub_category_list.setAdapter(adapter);

			sub_category_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method

					try {

						NewsDetailVideo f = new NewsDetailVideo();
						Bundle b = new Bundle();
						b.putString(Utils.url_send_intent,
								mainmodel.get(position)
										.getVideo_screen_passing_url());

						b.putString(Utils.title_send_intent,
								mainmodel.get(position).getVideo_screen_title());
						f.setArguments(b);

						((HomeScreen) getActivity()).addfragment(f,
								"NewsDetail");

					} catch (Exception e) {
						// TODO: handle exception
						// rare exception
						e.printStackTrace();
					}

				}
			});

			sub_category_list.setOnRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {
					// TODO Auto-generated method
					// stub

					getData();

				}
			});

			if (sub_category_list.isRefreshing())
				sub_category_list.onRefreshComplete();
		}
	}
}

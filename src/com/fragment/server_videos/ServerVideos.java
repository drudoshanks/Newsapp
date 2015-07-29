package com.fragment.server_videos;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.adapter.ServerVideoAdapter;
import com.grasshopper.newsapp.HomeScreen;
import com.grasshopper.newsapp.R;
import com.model.ServerVideoModel;
import com.utils.Session;
import com.utils.Utils;
import com.webservice.CallService;
import com.webservice.CallService.OnServicecall;

public class ServerVideos extends Fragment {

	private static final String ARG_POSITION = "position";

	private View view;
	private ListView video_list;

	Session session;
	int cat_id;

	Context context;

	ArrayList<ServerVideoModel> main_model;

	ServerVideoAdapter adapter;

	public static ServerVideos newInstance(int cat_id) {
		ServerVideos f = new ServerVideos();
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
		view = inflater.inflate(R.layout.server_videos, container, false);

		Bundle bundle = this.getArguments();
		cat_id = bundle.getInt(ARG_POSITION, 0);

		// initiate the session
		session = ((HomeScreen) getActivity()).session;

		video_list = (ListView) view.findViewById(R.id.video_list);

		main_model = new ArrayList<ServerVideoModel>();

		adapter = new ServerVideoAdapter(getActivity(), 0, main_model,
				ServerVideos.this);
		video_list.setAdapter(adapter);

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

		String urlStr = Utils._url + "getStory.php?client_id="
				+ session.getClient_id() + "&lang_id=" + session.getLang_id()
				+ "&cat_id=" + cat_id;

		CallService getNewsList = new CallService(context, urlStr, Utils.GET,
				new OnServicecall() {

					@Override
					public void onServicecall(String response) {
						// TODO Auto-generated method stub

						Log.d("ankit", "response ::::" + response);

						JSONPARSOR(response);

					}
				});
		getNewsList.execute();
		// }

	}

	private void JSONPARSOR(String response) {
		if (main_model.size() > 0)
			main_model.clear();

		try {

			JSONArray jarr = new JSONArray(response);
			for (int i = 0; i < jarr.length(); i++) {
				JSONObject jobj = jarr.getJSONObject(i);
				int story_id = Integer.parseInt(jobj.getString("story_id"));
				String url = jobj.getString("story_url");

				// these are in the inner array
				String propertyValue = "";
				String subpropertyValue = "";
				String pub_date = "";
				String assetUrl = "";
				String u_code = "";
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
					else if (TAG.equalsIgnoreCase("YL")) {
						// created date
						u_code = inneObj.getString("value");
					}

				}

				// ends here

				ServerVideoModel model = new ServerVideoModel();
				model.setYt_list_id(story_id);
				model.setYt_list_passingurl(url);
				model.setYt_list_image_url(assetUrl);
				model.setYt_list_title(propertyValue);
				model.setYt_list_title_sub(subpropertyValue);
				model.setYt_list_publish_date(pub_date);
				model.setYt_code(u_code);

				main_model.add(model);

			}

			// add main model to database
			// db.addStories(mainmodel, cat_id);

			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}

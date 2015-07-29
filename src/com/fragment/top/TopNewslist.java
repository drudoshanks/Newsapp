package com.fragment.top;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import com.adapter.TopNewsAdapterNew;
import com.grasshopper.newsapp.HomeScreen;
import com.grasshopper.newsapp.R;
import com.model.ToplistModel;
import com.utils.Session;
import com.utils.Utils;
import com.webservice.CallService;
import com.webservice.CallService.OnServicecall;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TopNewslist extends Fragment {

	ArrayList<ToplistModel> main_model = new ArrayList<ToplistModel>();
	Session session;
	ToplistModel model;
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

				ToplistModel model = new ToplistModel();
				model.setTitle(propertyValue);
				model.setTitle_sub(subpropertyValue);
				model.setImage_url(assetUrl);
				model.setPassing_url(url);

				main_model.add(model);

			}
			if (main_model != null && main_model.size() > 0) {

				final TopNewsAdapterNew adapter = new TopNewsAdapterNew(
						context, 0, main_model);

				PullToRefreshListView list = (PullToRefreshListView) view
						.findViewById(R.id.to_news_list);
				list.setAdapter(adapter);

				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub

						 addNewslayout(position);
//						main_model.get(position).setRead(true);
//						adapter.notifyDataSetChanged();

						// ImageView top_list_item_image = (ImageView) view
						// .findViewById(R.id.top_list_item_image);
						//
						// Drawable myIcon = top_list_item_image.getDrawable();
						//
						// if (myIcon != null) {
						// myIcon.setColorFilter(Color.GRAY,
						// PorterDuff.Mode.SRC_ATOP);
						// top_list_item_image.setImageDrawable(myIcon);
						//
						// Utils.setBlackAndWhite(top_list_item_image);
						// }
					}
				});

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

	private void addNewslayout(int value) {

		TopNewsDetailScrore f = new TopNewsDetailScrore();
		Bundle b = new Bundle();
		b.putParcelableArrayList(Utils.model_send_intent, main_model);
		b.putInt(Utils.position_send_intent, value);

		// b.putString(Utils.title_send_intent, title);
		f.setArguments(b);

		((HomeScreen) getActivity()).addfragment(f, "TopNewsDetailScrore");

	}
}

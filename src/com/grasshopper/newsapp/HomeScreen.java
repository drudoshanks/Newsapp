package com.grasshopper.newsapp;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adapter.SliderAdapter;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.Sharer.Result;
import com.facebook.share.widget.ShareDialog;
import com.fragment.NewsCategory;
import com.fragment.gallary.GallaryStories;
import com.fragment.live_video.LiveVideo;
import com.fragment.op.OpList;
import com.fragment.server_videos.ServerVideos;
import com.fragment.top.TopNewslist;
import com.fragment.video.VideoList;
import com.model.SliderModel;
import com.utils.Session;
import com.utils.Utils;
import com.webservice.CallService;
import com.webservice.CallService.OnServicecall;

public class HomeScreen extends FragmentActivity {

	String[] menu;
	DrawerLayout dLayout;
	ListView slider_list;
	RelativeLayout left_layout;
	boolean open_flag = false;
	public Session session;
	public ArrayList<SliderModel> main_model;
	public int list_position = 0;
	private TextView top_bar_title;

	CallbackManager callbackManager;
	public ShareDialog shareDialog;

	ArrayList<SliderModel> setter_model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);

		dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		slider_list = (ListView) findViewById(R.id.left_list);

		left_layout = (RelativeLayout) findViewById(R.id.left_layout);

		session = new Session(HomeScreen.this);

		top_bar_title = (TextView) findViewById(R.id.top_bar_title);

		add_list();

		slider_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {

				dLayout.closeDrawers();

				list_position = position;

				String tag = setter_model.get(list_position).getText();
				if (list_position == 0) {
					for (int i = 1; i < getSupportFragmentManager()
							.getBackStackEntryCount(); i++) {
						getSupportFragmentManager().popBackStack();
					}
					top_bar_title.setText(Html.fromHtml(tag));
				}

				else {
					FragmentManager fragmentManager = getSupportFragmentManager();
					Fragment frag = fragmentManager.findFragmentByTag(tag);

					if (frag == null) {

						switchScreens(setter_model.get(position).getLayout_no());
					}
				}
			}

		});

		ImageView open_drawable = (ImageView) findViewById(R.id.top_bar_menu);

		open_drawable.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (!open_flag) {
					dLayout.openDrawer(left_layout);
					open_flag = true;
				} else {
					dLayout.closeDrawer(left_layout);
					open_flag = false;
				}

			}
		});

		// facebook work
		FacebookSdk.sdkInitialize(HomeScreen.this);
		callbackManager = CallbackManager.Factory.create();
		shareDialog = new ShareDialog(this);
		// this part is optional
		shareDialog.registerCallback(callbackManager,
				new FacebookCallback<Sharer.Result>() {

					@Override
					public void onCancel() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onError(FacebookException error) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Result result) {
						// TODO Auto-generated method stub

					}
				});
		// ends here

	}

	@Override
	public void onActivityResult(final int requestCode, final int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// callbackManager.onActivityResult(requestCode, resultCode, data);
	}

	private void add_list() {

		setter_model = new ArrayList<SliderModel>();

		main_model = new ArrayList<SliderModel>();

		// if (session.getCategory().equalsIgnoreCase("")) {
		String url = Utils._url + "getCategory.php?client_id="
				+ session.getClient_id();

		// String url =
		// "https://www.googleapis.com/youtube/v3/videos?id=7F-OjwGLSe8&key=AIzaSyBXspoM4g3uJIWzl6GcXEux-jPwYObslJ0&part=snippet";

		CallService getCategories = new CallService(HomeScreen.this, url,
				Utils.GET, new OnServicecall() {

					@Override
					public void onServicecall(String response) {
						// TODO Auto-generated method stub

						JSONPARSOR(response);

						// session.setCategory(response);

					}
				});

		getCategories.execute();
		// } else {
		// JSONPARSOR(session.getCategory());
		// }

	}

	// replace fragments
	public void addfragment(Fragment fragment, String tag) {

		try {
			FragmentManager fragmentManager = getSupportFragmentManager();
			Fragment frag = fragmentManager.findFragmentByTag(tag);

			if (frag == null) {

				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.add(R.id.content_frame, fragment, tag)
						.addToBackStack(null).commit();

				fragmentManager.executePendingTransactions();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Override
	public void onBackPressed() {

		dLayout.closeDrawers();

		if (getSupportFragmentManager().getBackStackEntryCount() == 1) {

			finish();
		} else if (getSupportFragmentManager().getBackStackEntryCount() == 2) {

			top_bar_title.setText(Html
					.fromHtml(getSupportFragmentManager()
							.getFragments()
							.get(getSupportFragmentManager()
									.getBackStackEntryCount() - 2).getTag()));
			super.onBackPressed();
		} else {
			super.onBackPressed();
		}
	}

	// switch fragments
	void switchScreens(int layout_number) {

		// this code is to remove all the fragments existed before this
		// transaction

		for (int i = 1; i < getSupportFragmentManager()
				.getBackStackEntryCount(); i++) {
			getSupportFragmentManager().popBackStack();
		}

		if (setter_model.get(list_position).getLayout_no() == 1024) {

			startActivity(new Intent(HomeScreen.this, Setting.class));
			finish();
		}

		else if (setter_model.get(list_position).getLayout_no() == 2000) {
			addfragment(new LiveVideo(), "LiveVideo");

			top_bar_title.setText("LiveVideo");
		}

		// else if (setter_model.get(list_position).getLayout_no() == 1000) {
		// addfragment(new ServerVideos(), "ServerVideos");
		//
		// top_bar_title.setText("ServerVideos");
		// }

		else {

			String tag = "";

			tag = setter_model.get(list_position).getText();

			top_bar_title.setText(Html.fromHtml(tag));

			Log.d("ankit", "cat tag:::"
					+ setter_model.get(list_position).getCat_type_tag());

			if (setter_model.get(list_position).getCat_type_tag()
					.equalsIgnoreCase("TP")) {
				addfragment(new TopNewslist(), tag);
			} else if (setter_model.get(list_position).getCat_type_tag()
					.equalsIgnoreCase("NS")) {
				addfragment(new NewsCategory(), tag);
			} else if (setter_model.get(list_position).getCat_type_tag()
					.equalsIgnoreCase("GL")) {
				addfragment(
						GallaryStories.newInstance(setter_model.get(
								list_position).getCat_id()), tag);
			} else if (setter_model.get(list_position).getCat_type_tag()
					.equalsIgnoreCase("VD")) {
				addfragment(VideoList.newInstance(setter_model.get(
						list_position).getCat_id()), tag);
			} else if (setter_model.get(list_position).getCat_type_tag()
					.equalsIgnoreCase("PL")) {
				addfragment(OpList.newInstance(setter_model.get(list_position)
						.getCat_id()), tag);
			} else if (setter_model.get(list_position).getCat_type_tag()
					.equalsIgnoreCase("YTV")) {
				// ServerVideos
				addfragment(ServerVideos.newInstance(setter_model.get(
						list_position).getCat_id()), tag);
			}
		}

		// switch (layout_number) {
		// case 0:
		//
		// addfragment(new NewsCategory(), "NewsCategory");
		// break;
		//
		// case 1:
		//
		// addfragment(new VideoScreen(), "VideoScreen");
		// break;
		//
		// case 2:
		//
		// addfragment(new Opinion_poll(), "Opinion_poll");
		// break;
		//
		// case 3:
		//
		// addfragment(new Competition(), "Competition");
		// break;

		// default:
		// break;
		// }

	}

	private void JSONPARSOR(String response) {
		try {

			JSONArray jarr = new JSONArray(response);

			// [{"cat_id":"1","client_id":"1","parent_cat_id":"0","name_eng":"Home","image_url":"","lang_name":"1",
			// "cate_type_id":"10","cate_type_name":"Top News"},{"cat_id":"2","client_id":"1","parent_cat_id":"1","name_eng":"Game","image_url":"","lang_name":"1","cate_type_id":"11","cate_type_name":"News"}]

			for (int i = 0; i < jarr.length(); i++) {

				JSONObject jobj = jarr.getJSONObject(i);
				int cat_id = Integer.parseInt(jobj.getString("cat_id"));
				int parent_cat_id = Integer.parseInt(jobj
						.getString("parent_cat_id"));
				String name_lang = jobj.getString("name_eng");
				int layout_no = Integer
						.parseInt(jobj.getString("cate_type_id"));
				String image_url = jobj.getString("image_url");

				String cat_type_tag = jobj.getString("cat_type_tag");

				// "TP"

				SliderModel model = SliderModel.newInstance();
				model.setCat_id(cat_id);
				model.setParent_cat_id(parent_cat_id);
				model.setText(name_lang);
				model.setLayout_no(layout_no);
				model.setImage_url(image_url);
				model.setCat_type_tag(cat_type_tag);

				main_model.add(model);

				if (model.getParent_cat_id() == 0) {
					setter_model.add(model);
					// if (cat_type_tag.equalsIgnoreCase("TP") && i != 0) {
					//
					// // this is for swapping models
					//
					// SliderModel temp_model1 = setter_model.get(i - 1);
					// SliderModel temp_model2 = setter_model.get(0);
					//
					// setter_model.set(0, temp_model1);
					// setter_model.set(i - 1, temp_model2);
					//
					// }
				}

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// {
		// SliderModel model = SliderModel.newInstance();
		// model.setCat_id(1000);
		// model.setParent_cat_id(0);
		// model.setText("Live Videos");
		// model.setLayout_no(1000);
		// model.setImage_url("data/settings.png");
		//
		// setter_model.add(model);
		// main_model.add(model);
		// }

		// rtsp block

		{
			SliderModel model = SliderModel.newInstance();
			model.setCat_id(2000);
			model.setParent_cat_id(0);
			model.setText("Live TV");
			model.setLayout_no(2000);
			model.setImage_url("data/image/icons-03.png");

			setter_model.add(model);
			main_model.add(model);
		}

		// rtsp ends here

		// settings block to be removed

		{
			SliderModel model = SliderModel.newInstance();
			model.setCat_id(1024);
			model.setParent_cat_id(0);
			model.setText("Settings");
			model.setLayout_no(1024);
			model.setImage_url("data/settings.png");

			setter_model.add(model);
			main_model.add(model);
		}

		// settings block ends here

		SliderAdapter adapetr = new SliderAdapter(HomeScreen.this, setter_model);
		slider_list.setAdapter(adapetr);

		// add the top news
		if (setter_model != null && setter_model.size() > 0)
			switchScreens(0);
	}
}

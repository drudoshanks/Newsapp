package com.grasshopper.newsapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.model.UserPropertyModel;
import com.utils.Session;
import com.utils.Utils;
import com.webservice.CallService;
import com.webservice.CallService.OnServicecall;

public class SignUp extends Activity {

	Button submit_button;
	LinearLayout user_property_layout;
	Session session;

	LayoutInflater mInflater;

	ArrayList<UserPropertyModel> mainmodel;
	ArrayList<EditText> all_edits;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sign_up);

		init();

		// get user property type
		getUserPropertyType();

	}

	private void init() {
		submit_button = (Button) findViewById(R.id.submit_button);

		submit_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				registerUser();

			}
		});

		user_property_layout = (LinearLayout) findViewById(R.id.user_property_layout);

		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		all_edits = new ArrayList<EditText>();

		session = new Session(SignUp.this);
	}

	private void getUserPropertyType() {

//		String url = Utils._url + "getUserPropertyType.php?client_id="
//				+ session.getClient_id();
		
		String url = Utils._url + "getUserPropertyType.php?client_id=2";

		CallService callService = new CallService(SignUp.this, url, Utils.GET,
				new OnServicecall() {

					@Override
					public void onServicecall(String response) {
						// TODO Auto-generated method stub

						mainmodel = jsonDecodeUserPropertyType(response);

						if (mainmodel != null && mainmodel.size() > 0)
							makeUI(mainmodel);

					}

				});
		callService.execute();
	}

	private ArrayList<UserPropertyModel> jsonDecodeUserPropertyType(String value) {

		Log.d("ankit", "response :::" + value);
		ArrayList<UserPropertyModel> mainmodel = new ArrayList<UserPropertyModel>();

		try {
			JSONArray jarr = new JSONArray(value);

			for (int i = 0; i < jarr.length(); i++) {

				JSONObject jobj = jarr.getJSONObject(i);

				UserPropertyModel model = new UserPropertyModel();

				model.setClient_id(jobj
						.getString(Utils.user_property_client_id));
				// model.setLang_id(jobj.getString(Utils.user_property_lang_id));
				model.setName_eng(jobj.getString(Utils.user_property_name_eng));
				// model.setName_lang(jobj
				// .getString(Utils.user_property_name_lang));
				model.setUser_property_id(jobj
						.getString(Utils.user_property_id));

				mainmodel.add(model);
			}

			return mainmodel;
		} catch (Exception e) {
			// TODO: handle exception

			return null;
		}

	}

	@SuppressLint("InflateParams")
	private void makeUI(ArrayList<UserPropertyModel> mainmodel) {

		if (mainmodel != null && mainmodel.size() > 0)
			for (int i = 0; i < mainmodel.size(); i++) {

				UserPropertyModel model = mainmodel.get(i);

				EditText edit_text = (EditText) mInflater.inflate(
						R.layout.register_edit, null, false);
				edit_text.setHint(Html.fromHtml(model.getName_eng()));

				all_edits.add(edit_text);
				user_property_layout.addView(edit_text);
			}

	}

	private void registerUser() {
		try {

			boolean isEmpty = false;

			JSONObject send_jobj = new JSONObject();

			if (all_edits != null && all_edits.size() > 0)
				outer_loop: for (int i = 0; i < all_edits.size(); i++) {

					EditText edit = all_edits.get(i);

					String edit_str = edit.getText().toString().trim();

					if (edit_str.equalsIgnoreCase("")) {
						Toast.makeText(SignUp.this,
								"Please fill all the feilds", Toast.LENGTH_LONG)
								.show();
						isEmpty = true;
						break outer_loop;
					}

					send_jobj.put(mainmodel.get(i).getUser_property_id(),
							edit_str);

				}

			if (!isEmpty) {

				String url = Utils._url + "setUser.php?client_id="
						+ session.getClient_id() + "&lang_id="
						+ session.getLang_id() + "&varIDs=" + send_jobj;

				Log.d("ankit", "::::" + url);

				CallService callService = new CallService(SignUp.this, url,
						Utils.GET, new OnServicecall() {

							@Override
							public void onServicecall(String response) {
								// TODO Auto-generated method stub

								Log.d("ankit", "response::" + response);

								try {
									JSONObject jobj = new JSONObject(response);

									String userID = jobj.getString("user_id");

									Log.d("ankit", "userID::" + userID);

									session.setUser_id(userID);

									startActivity(new Intent(SignUp.this,
											HomeScreen.class));
									finish();

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									Toast.makeText(SignUp.this,
											"Please try again later",
											Toast.LENGTH_LONG).show();
								}

							}

						});
				callService.execute();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(SignUp.this, "something went wrong",
					Toast.LENGTH_LONG).show();
		}
	}
}

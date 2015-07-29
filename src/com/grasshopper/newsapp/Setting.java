package com.grasshopper.newsapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.utils.Session;
import com.utils.Utils;
import com.webservice.CallService;
import com.webservice.CallService.OnServicecall;

public class Setting extends Activity {

	Spinner all_category, all_language;

	Session session;

	String lang_id = "", client_id = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		session = new Session(Setting.this);
		Button submit = (Button) findViewById(R.id.submit);

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (lang_id.equalsIgnoreCase("")
						|| client_id.equalsIgnoreCase("")) {

					Toast.makeText(Setting.this, "please fill all settings",
							Toast.LENGTH_LONG).show();

				} else {

					session.setClient_id(client_id);
					session.setLang_id(lang_id);

					startActivity(new Intent(Setting.this, HomeScreen.class));
					finish();
				}

				client_id = "";
				lang_id = "";

			}
		});

		all_category = (Spinner) findViewById(R.id.all_category);

		all_language = (Spinner) findViewById(R.id.all_language);

		// get all
		getClientID();
	}

	// get client id and languGE ID
	private void getClientID() {

		client_id = "";
		String urlStr = Utils._url + "getClient.php";

		CallService getListdata = new CallService(Setting.this, urlStr,
				Utils.GET, new OnServicecall() {

					@Override
					public void onServicecall(String response) {
						// TODO Auto-generated method stub

						try {

							final ArrayList<ClientModel> mainmodel = new ArrayList<Setting.ClientModel>();

							ArrayList<String> mainSting = new ArrayList<String>();

							JSONArray jarr = new JSONArray(response);
							for (int i = 0; i < jarr.length(); i++) {

								JSONObject jobj = jarr.getJSONObject(i);
								ClientModel model = new ClientModel();
								model.setClient_id(jobj.getInt("client_id"));
								model.setName_eng(jobj.getString("name_eng"));

								mainmodel.add(model);
								mainSting.add(jobj.getString("name_eng"));

							}

							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									Setting.this, R.layout.spinner_item,
									mainSting);
							all_category.setAdapter(adapter);

							all_category
									.setOnItemSelectedListener(new OnItemSelectedListener() {

										@Override
										public void onItemSelected(
												AdapterView<?> parent,
												View view, int position, long id) {
											// TODO Auto-generated method stub

											int client_id = mainmodel.get(
													position).getClient_id();

											Setting.this.client_id = ""
													+ client_id;
											get_lang(client_id);

										}

										@Override
										public void onNothingSelected(
												AdapterView<?> parent) {
											// TODO Auto-generated method stub

										}
									});

						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				});
		getListdata.execute();
	}

	private class ClientModel {
		private int client_id = 0;
		private String name_eng = "";

		public int getClient_id() {
			return client_id;
		}

		public void setClient_id(int client_id) {
			this.client_id = client_id;
		}

		public String getName_eng() {
			return name_eng;
		}

		public void setName_eng(String name_eng) {
			this.name_eng = name_eng;
		}

	}

	private void get_lang(int client_id) {

		lang_id = "";
		// http://192.168.5.171/klapp/getLanguage.php?client_id=1

		String urlStr = Utils._url + "getLanguage.php?client_id=" + client_id;

		CallService getListdata = new CallService(Setting.this, urlStr,
				Utils.GET, new OnServicecall() {

					@Override
					public void onServicecall(String response) {
						// TODO Auto-generated method stub

						try {

							final ArrayList<Langmodel> mainmodel = new ArrayList<Setting.Langmodel>();

							ArrayList<String> mainStrings = new ArrayList<String>();

							JSONArray JARR = new JSONArray(response);
							for (int i = 0; i < JARR.length(); i++) {

								JSONObject jobj = JARR.getJSONObject(i);

								Langmodel model = new Langmodel();
								model.setLang_id(jobj.getInt("lang_id"));
								model.setName_lang(jobj.getString("name_lang"));

								mainmodel.add(model);
								mainStrings.add(jobj.getString("name_lang"));

							}

							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									Setting.this, R.layout.spinner_item,
									mainStrings);
							all_language.setAdapter(adapter);

							all_language
									.setOnItemSelectedListener(new OnItemSelectedListener() {

										@Override
										public void onItemSelected(
												AdapterView<?> parent,
												View view, int position, long id) {
											// TODO Auto-generated method stub

											lang_id = ""
													+ mainmodel.get(position)
															.getLang_id();

										}

										@Override
										public void onNothingSelected(
												AdapterView<?> parent) {
											// TODO Auto-generated method stub

										}
									});

						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				});
		getListdata.execute();
	}

	private class Langmodel {
		private int lang_id = 0;
		private String name_lang = "";

		public int getLang_id() {
			return lang_id;
		}

		public void setLang_id(int lang_id) {
			this.lang_id = lang_id;
		}

		public String getName_lang() {
			return name_lang;
		}

		public void setName_lang(String name_lang) {
			this.name_lang = name_lang;
		}

	}
}

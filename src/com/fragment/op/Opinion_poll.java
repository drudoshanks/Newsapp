package com.fragment.op;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.fragment.NewsComment;
import com.grasshopper.newsapp.HomeScreen;
import com.grasshopper.newsapp.R;
import com.model.OpDetailModel;
import com.model.OpDetailSubModel;
import com.model.OpListModel;
import com.squareup.picasso.Picasso;
import com.utils.Session;
import com.utils.Utils;
import com.webservice.CallService;
import com.webservice.CallService.OnServicecall;
import com.webservice.OpXmlParser;

public class Opinion_poll extends Fragment {

	private String passing_url = "";

	View view;

	Context context;

	private String emei_id = "";

	private Session session;
	private OpListModel model;
	int cat_id = 0;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		context = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.opinion_poll, container, false);

		Bundle bundle = this.getArguments();

		model = bundle.getParcelable(Utils.model_send_intent);
		cat_id = bundle.getInt("cat_id");

		if (model != null) {
			passing_url = Utils._url + model.getOp_list_passingurl();

			init();
			CallUrlForXml(passing_url);
		}

		return view;
	}

	private void init() {

		session = new Session(context);
		emei_id = Utils.getEMIE_ID(context);

		ImageView news_details_share = (ImageView) view
				.findViewById(R.id.news_details_share);
		news_details_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Utils.shareThing(passing_url, getActivity());
			}
		});

		ImageView news_detail_comment = (ImageView) view
				.findViewById(R.id.news_detail_comment);
		news_detail_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {

					NewsComment f = new NewsComment();

					((HomeScreen) getActivity()).addfragment(f, "NewsComment");

				} catch (Exception e) {
					// TODO: handle exception
					// rare exception
					e.printStackTrace();
				}
			}
		});
	}

	// call url for getting the xml
	private void CallUrlForXml(String url) {
		CallService callservice = new CallService(getActivity(), url,
				Utils.GET, new OnServicecall() {

					@Override
					public void onServicecall(String response) {
						// TODO parse xml and make ui
						try {

							if (response != null
									&& !response.equalsIgnoreCase("")) {
								makeUI(ParseXml(response));

							}

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}

					}
				});

		callservice.execute();
	}

	private OpDetailModel ParseXml(String xml) throws Exception {

		/************** Read XML *************/

		BufferedReader br = new BufferedReader(new StringReader(xml));
		InputSource is = new InputSource(br);

		/************ Parse XML **************/

		OpXmlParser parser = new OpXmlParser();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser sp = factory.newSAXParser();
		XMLReader reader = sp.getXMLReader();
		reader.setContentHandler(parser);
		reader.parse(is);

		return Utils.op_model;
	}

	@SuppressLint("InflateParams")
	private void makeUI(final OpDetailModel model) {

		ImageView opinion_poll_image = (ImageView) view
				.findViewById(R.id.opinion_poll_image);

		try {

			String url = Utils._url + model.getImage();

			url = url.replace(" ", "%20");

			Picasso.with(context).load(url).fit().into(opinion_poll_image);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		TextView news_detail_news_title = (TextView) view
				.findViewById(R.id.news_detail_news_title);
		news_detail_news_title.setText(model.getQuestion());

		TextView op_date = (TextView) view.findViewById(R.id.op_date);
		op_date.setText(model.getPub_date());

		LinearLayout insert_option_layout = (LinearLayout) view
				.findViewById(R.id.insert_option_layout);

		ArrayList<OpDetailSubModel> optionList = new ArrayList<OpDetailSubModel>();
		optionList = model.getOptions();

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		final ArrayList<TextView> textArr = new ArrayList<TextView>();

		for (int i = 0; i < optionList.size(); i++) {

			View v = inflater.inflate(R.layout.op_button, null);

			v.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.MATCH_PARENT, 1f));

			final OpDetailSubModel submodel = optionList.get(i);

			insert_option_layout.addView(v);

			final TextView medium_text = (TextView) v
					.findViewById(R.id.medium_text);

			textArr.add(medium_text);

			Button op_button_id = (Button) v.findViewById(R.id.op_button_id);
			op_button_id.setText("" + submodel.getOption());

			op_button_id.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					setUserPolls(submodel.getOptionid(), textArr);
				}
			});

		}

	}

	private void setUserPolls(int id, final ArrayList<TextView> textView) {

		// user_poll.php?option_id=1&usermid=1&client_id=1&Langid=1&storyid=1

		String urlStr = Utils._url + "user_poll.php?option_id=" + id
				+ "&usermid=" + emei_id + "&client_id="
				+ session.getClient_id() + "&Langid=" + session.getLang_id()
				+ "&storyid=" + model.getOp_list_id();

		new CallService(context, urlStr, Utils.GET, new OnServicecall() {

			@Override
			public void onServicecall(String response) {
				// TODO Auto-generated method stub
				Toast.makeText(context, response, Toast.LENGTH_LONG).show();

				String urlStr = Utils._url + "user_poll_service.php?storyid="
						+ model.getOp_list_id() + "&client_id="
						+ session.getClient_id() + "&languadid="
						+ session.getLang_id() + "&catid=" + cat_id;

				// user_poll_service.php?storyid=1&client_id=1&languadid=1&catid=1

				new CallService(context, urlStr, Utils.GET,
						new OnServicecall() {

							@Override
							public void onServicecall(String response) {
								// TODO Auto-generated method stub

								Log.d("ankit", response);

								try {
									JSONArray jarr = new JSONArray(response);

									int _count = 0;

									for (int i = 0; i < jarr.length(); i++) {

										try {

											JSONObject jobj = jarr
													.getJSONObject(i);
											JSONArray jarrar = jobj
													.getJSONArray("propert_array");
											JSONObject inner_jobj = jarrar
													.getJSONObject(0);
											String count = inner_jobj
													.getString("totalpositvie");

											Log.d("ankit", "count = " + count);

											textView.get(_count).setText(
													"Count = " + count);
											_count++;

										} catch (Exception e) {
											// TODO: handle exception

											e.printStackTrace();
										}

									}

								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}

								// Toast.makeText(context, response,
								// Toast.LENGTH_LONG).show();

							}
						}).execute();

			}
		}).execute();

	}

}

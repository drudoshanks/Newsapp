package com.fragment;

import java.io.BufferedReader;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.grasshopper.newsapp.HomeScreen;
import com.grasshopper.newsapp.R;
import com.model.NewsDetailModel;
import com.squareup.picasso.Picasso;
import com.utils.Utils;
import com.webservice.CallService;
import com.webservice.CallService.OnServicecall;
import com.webservice.XMLParser;

public class NewsDetail extends Fragment {

	private String passing_url = "";
	private String title = "";

	static String TWITTER_CONSUMER_KEY = "JAJRPdxAsNtoKNLlq2urRgkez";
	static String TWITTER_CONSUMER_SECRET = "W60QrPV33HzERq5RVMZG87cQ0rTtK7SRT4zVKdLto0IJLnPV9g";

	// Preference Constants
	static String PREFERENCE_NAME = "twitter_oauth";
	static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

	static final String TWITTER_CALLBACK_URL = "http://thegrasshoppers.biz";

	// Twitter oauth urls
	static final String URL_TWITTER_AUTH = "auth_url";
	static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";

	// Twitter
	private static Twitter twitter;
	private static RequestToken requestToken;

	// Shared Preferences
	private static SharedPreferences mSharedPreferences;

	View view;

	ImageView news_details_image;

	Dialog commentDialog;

	public static NewsDetail newInstance(String passing_url) {
		NewsDetail f = new NewsDetail();
		Bundle b = new Bundle();
		b.putString(Utils.url_send_intent, passing_url);
		f.setArguments(b);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.news_detail, container, false);

		Bundle bundle = this.getArguments();
		passing_url = Utils._url + bundle.getString(Utils.url_send_intent);
		//
		//

		// test code
		// passing_url = Utils._url + "Story/172.xml";
		//
		// Log.d("ankit", "passing_url:::" + passing_url);

		// ends here

		title = bundle.getString(Utils.title_send_intent);

		// Shared Preferences
		mSharedPreferences = getActivity().getApplicationContext()
				.getSharedPreferences("MyPref", 0);

		init(view);

		// do work after initializing the UI
		CallUrlForXml(passing_url);

		return view;
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void init(View v) {
		ImageView news_detail_like = (ImageView) v
				.findViewById(R.id.news_detail_like);
		news_detail_like.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Toast.makeText(getActivity(), "Like button pressed",
						Toast.LENGTH_LONG).show();
			}
		});

		ImageView news_detail_comment = (ImageView) v
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

		ImageView news_details_share = (ImageView) v
				.findViewById(R.id.news_details_share);
		news_details_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Utils.shareThing(passing_url, getActivity());

				// final Dialog shareDialog = new Dialog(getActivity(),
				// R.style.myDialogTheme);
				// shareDialog.setContentView(R.layout.share_dialog);
				//
				// Button facebook_share = (Button) shareDialog
				// .findViewById(R.id.facebook_share);
				// facebook_share.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				//
				// shareDialog.dismiss();
				// shareFacebook();
				//
				// }
				// });
				//
				// Button twitter_share = (Button) shareDialog
				// .findViewById(R.id.twitter_share);
				// twitter_share.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				//
				// shareDialog.dismiss();
				// loginToTwitter();
				//
				// }
				// });
				//
				// shareDialog.show();
			}
		});

		// comment dialog initialization

		commentDialog = new Dialog(getActivity(), R.style.myDialogTheme);
		commentDialog.setContentView(R.layout.do_comment);

		ImageView news_detail_do_comment = (ImageView) view
				.findViewById(R.id.news_detail_do_comment);

		news_detail_do_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Open a dialog box to comment on this news

				commentDialog.show();

			}
		});

		// initialize all comment dialog items

		final EditText comment_box = (EditText) commentDialog
				.findViewById(R.id.comment_box);

		Button submit_comments = (Button) commentDialog
				.findViewById(R.id.submit_comments);
		submit_comments.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String comment_boxString = comment_box.getText().toString()
						.trim();

				Toast.makeText(getActivity(), comment_boxString,
						Toast.LENGTH_LONG).show();

				comment_box.setText("");
				commentDialog.dismiss();
			}
		});

		news_details_image = (ImageView) v
				.findViewById(R.id.news_details_image);

		news_details_image.requestLayout();

		news_details_image.getLayoutParams().height = Utils
				.getImageHeight(getActivity());

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (!isTwitterLoggedInAlready()) {
			Uri uri = getActivity().getIntent().getData();
			if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
				// oAuth verifier
				String verifier = uri
						.getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);

				try {
					// Get the access token
					AccessToken accessToken = twitter.getOAuthAccessToken(
							requestToken, verifier);

					// Shared Preferences
					Editor e = mSharedPreferences.edit();

					// After getting access token, access token secret
					// store them in application preferences
					e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
					e.putString(PREF_KEY_OAUTH_SECRET,
							accessToken.getTokenSecret());
					// Store login status - true
					e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
					e.commit(); // save changes

					new updateTwitterStatus().execute("hi I am updating this "
							+ System.currentTimeMillis());

					Log.e("Twitter OAuth Token", "> " + accessToken.getToken());

				} catch (Exception e) {
					// Check log for login errors
					Log.e("Twitter Login Error", "> " + e.getMessage());
				}
			}
		}

	}

	private void shareFacebook() {
		if (ShareDialog.canShow(ShareLinkContent.class)) {
			ShareLinkContent linkContent = new ShareLinkContent.Builder()
					.setContentTitle("NewsApp").setContentDescription(title)
					.setContentUrl(Uri.parse(passing_url)).build();

			((HomeScreen) getActivity()).shareDialog.show(linkContent);
		}
	}

	/**
	 * Function to login twitter
	 * */
	private void loginToTwitter() {
		// Check if already logged in
		if (!isTwitterLoggedInAlready()) {
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
			builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
			Configuration configuration = builder.build();

			TwitterFactory factory = new TwitterFactory(configuration);
			twitter = factory.getInstance();

			new AsyncTask<Void, Void, Void>() {

				ProgressDialog dialog;

				@Override
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					try {
						requestToken = twitter
								.getOAuthRequestToken(TWITTER_CALLBACK_URL);
					} catch (TwitterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}

				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();

					dialog = new ProgressDialog(getActivity(),
							ProgressDialog.THEME_HOLO_DARK);
					// set indeterminate style
					dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					dialog.setCancelable(false);
					// set title and message
					dialog.setMessage("Please wait");
					// and show it
					dialog.show();
				}

				@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					dialog.dismiss();
					getActivity()
							.startActivity(
									new Intent(Intent.ACTION_VIEW, Uri
											.parse(requestToken
													.getAuthenticationURL())));
				}

			}.execute();

		} else {
			// user already logged into twitter

			new updateTwitterStatus().execute("hi I am updating this "
					+ System.currentTimeMillis());

		}
	}

	/**
	 * Function to update status
	 * */
	class updateTwitterStatus extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		/**
		 * getting Places JSON
		 * */
		@Override
		protected String doInBackground(String... args) {
			Log.d("Tweet Text", "> " + args[0]);
			String status = args[0];
			try {
				ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
				builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);

				// Access Token
				String access_token = mSharedPreferences.getString(
						PREF_KEY_OAUTH_TOKEN, "");
				// Access Token Secret
				String access_token_secret = mSharedPreferences.getString(
						PREF_KEY_OAUTH_SECRET, "");

				AccessToken accessToken = new AccessToken(access_token,
						access_token_secret);
				Twitter twitter = new TwitterFactory(builder.build())
						.getInstance(accessToken);

				// Update status
				twitter4j.Status response = twitter.updateStatus(status);

				Log.d("Status", "> " + response.getText());
			} catch (TwitterException e) {
				// Error in updating status
				Log.d("Twitter Update Error", e.getMessage());
			}
			return null;
		}

	}

	/**
	 * Check user already logged in your application using twitter Login flag is
	 * fetched from Shared Preferences
	 * */
	private boolean isTwitterLoggedInAlready() {
		// return twitter login status from Shared Preferences
		return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
	}

	// call url for getting the xml
	private void CallUrlForXml(String url) {

		Log.d("ankit", "xml url ::" + url);

		CallService callservice = new CallService(getActivity(), url,
				Utils.GET, new OnServicecall() {

					@Override
					public void onServicecall(String response) {
						// TODO parse xml and make ui
						try {

							if (response != null
									&& !response.equalsIgnoreCase(""))
								makeUI(ParseXml(response), view);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}

					}
				});

		callservice.execute();

	}

	private NewsDetailModel ParseXml(String xml) throws Exception {

		/************** Read XML *************/

		BufferedReader br = new BufferedReader(new StringReader(xml));
		InputSource is = new InputSource(br);

		/************ Parse XML **************/

		XMLParser parser = new XMLParser();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser sp = factory.newSAXParser();
		XMLReader reader = sp.getXMLReader();
		reader.setContentHandler(parser);
		reader.parse(is);

		return Utils.model;

	}

	private void makeUI(NewsDetailModel model, View v) {

		TextView news_detail_categoryname = (TextView) v
				.findViewById(R.id.news_detail_categoryname);
		news_detail_categoryname
				.setText(Html.fromHtml(model.getPropertyValue()));

		TextView news_detail_news_publish_date = (TextView) v
				.findViewById(R.id.news_detail_news_publish_date);
		news_detail_news_publish_date
				.setText("Publish date:"+Html.fromHtml(model.getPub_date()));

		TextView news_detail_news_title = (TextView) v
				.findViewById(R.id.news_detail_news_title);

		news_detail_news_title.setText(Html.fromHtml(model.getPropertyValue()));

		TextView news_detail_news_detail = (TextView) v
				.findViewById(R.id.news_detail_news_detail);
		news_detail_news_detail
				.setText(Html.fromHtml(model.getPropertySTORY()));

		news_detail_news_detail.setMovementMethod(new MovementCheck());

		try {

			String image_url = Utils._url + model.getAsset_id();

			image_url = image_url.replace(" ", "%20");

			if (!image_url.equalsIgnoreCase(""))
				Picasso.with(getActivity()).load(image_url).fit()
						.into(news_details_image);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private class MovementCheck extends LinkMovementMethod {

		@Override
		public boolean onTouchEvent(TextView widget, Spannable buffer,
				MotionEvent event) {
			try {
				return super.onTouchEvent(widget, buffer, event);
			} catch (Exception ex) {
				Toast.makeText(getActivity(), "Could not load link",
						Toast.LENGTH_LONG).show();
				return true;
			}
		}

	}

}

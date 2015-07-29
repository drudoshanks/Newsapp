package com.fragment.video;

import java.io.BufferedReader;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.fragment.NewsComment;
import com.grasshopper.newsapp.HomeScreen;
import com.grasshopper.newsapp.R;
import com.model.VideoDetailModel;
import com.utils.Utils;
import com.webservice.CallService;
import com.webservice.CallService.OnServicecall;
import com.webservice.VideoXmlParser;

public class NewsDetailVideo extends Fragment {

	View view;

	private String passing_url = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.news_detail_video, container, false);

		Bundle bundle = this.getArguments();
		passing_url = Utils._url + bundle.getString(Utils.url_send_intent);

		init();
		CallUrlForXml(passing_url);
		return view;
	}

	private void init() {

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

	private VideoDetailModel ParseXml(String xml) throws Exception {

		/************** Read XML *************/

		BufferedReader br = new BufferedReader(new StringReader(xml));
		InputSource is = new InputSource(br);

		/************ Parse XML **************/

		VideoXmlParser parser = new VideoXmlParser();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser sp = factory.newSAXParser();
		XMLReader reader = sp.getXMLReader();
		reader.setContentHandler(parser);
		reader.parse(is);

		return Utils.video_model;
	}

	private void makeUI(VideoDetailModel model) {

		TextView news_detail_categoryname = (TextView) view
				.findViewById(R.id.news_detail_categoryname);
		news_detail_categoryname.setText(model.getPropertyValue());

		VideoView news_detail_pager = (VideoView) view
				.findViewById(R.id.news_detail_pager);

		String uriPath = (Utils._url + model.getStoryUrl()).replace(" ", "%20");

		Log.d("ankit", "uriPath:::" + uriPath);

		Uri uri = Uri.parse(uriPath);
		news_detail_pager.setVideoURI(uri);
		news_detail_pager
				.setMediaController(new MediaController(getActivity()));
		news_detail_pager.requestFocus();
		news_detail_pager.start();

	}

}

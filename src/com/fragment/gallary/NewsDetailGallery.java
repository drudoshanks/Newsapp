package com.fragment.gallary;

import java.io.BufferedReader;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anks.libs.InfinitePagerAdapter;
import com.fragment.GallryImage;
import com.fragment.NewsComment;
import com.grasshopper.newsapp.HomeScreen;
import com.grasshopper.newsapp.R;
import com.model.Gallarymodel;
import com.utils.Utils;
import com.webservice.CallService;
import com.webservice.CallService.OnServicecall;
import com.webservice.GallaryXmlParser;

public class NewsDetailGallery extends Fragment {

	View view;

	private ViewPager news_detail_pager;

	private String passing_url = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.news_detail_gallary, container, false);

		news_detail_pager = (ViewPager) view
				.findViewById(R.id.news_detail_pager);

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

								Gallarymodel model = new Gallarymodel();

								model = ParseXml(response);

								MyPagerAdapter adapter = new MyPagerAdapter(
										getChildFragmentManager(), model);

								// wrap pager to provide infinite paging with wrap-around
//								PagerAdapter wrappedAdapter = new InfinitePagerAdapter(adapter);
								
								news_detail_pager.setAdapter(adapter);

								makeUI(model);
							}

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}

					}
				});

		callservice.execute();

	}

	private Gallarymodel ParseXml(String xml) throws Exception {

		/************** Read XML *************/

		BufferedReader br = new BufferedReader(new StringReader(xml));
		InputSource is = new InputSource(br);

		/************ Parse XML **************/

		GallaryXmlParser parser = new GallaryXmlParser();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser sp = factory.newSAXParser();
		XMLReader reader = sp.getXMLReader();
		reader.setContentHandler(parser);
		reader.parse(is);

		return Utils.gallary_model;
	}

	private void makeUI(Gallarymodel model) {
		TextView news_detail_categoryname = (TextView) view
				.findViewById(R.id.news_detail_categoryname);
		news_detail_categoryname.setText(model.getPropertyValue());
	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

		Gallarymodel main_model;

		public MyPagerAdapter(FragmentManager fm, Gallarymodel main_model) {
			super(fm);

			this.main_model = main_model;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// return main_model.get(position).getText();
			return "";
		}

		@Override
		public int getCount() {
			return main_model.getStory_images().size();
		}

		@Override
		public Fragment getItem(int position) {
			return GallryImage.newInstance(main_model.getStory_images().get(
					position));
		}

	}

}

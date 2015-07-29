package com.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anks.libs.InfinitePagerAdapter;
import com.grasshopper.newsapp.R;
import com.model.News_List_Model;
import com.utils.Utils;

public class NewsdetailScroll extends Fragment {

	View view;
	ArrayList<News_List_Model> mainmodel;
	ViewPager news_scroll_pager;
	int position = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.news_detail_scroll, container, false);

		news_scroll_pager = (ViewPager) view
				.findViewById(R.id.news_scroll_pager);

		Bundle bundle = this.getArguments();
		mainmodel = bundle.getParcelableArrayList(Utils.model_send_intent);
		position = bundle.getInt(Utils.position_send_intent);

		if (mainmodel != null && mainmodel.size() > 0) {
			MyPagerAdapter adapter = new MyPagerAdapter(
					getChildFragmentManager(), mainmodel);

			// wrap pager to provide infinite paging with wrap-around
			PagerAdapter wrappedAdapter = new InfinitePagerAdapter(adapter);

			news_scroll_pager.setAdapter(wrappedAdapter);
			news_scroll_pager.setCurrentItem(position, true);
		}

		return view;
	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

		ArrayList<News_List_Model> mainmodel;

		public MyPagerAdapter(FragmentManager fm,
				ArrayList<News_List_Model> mainmodel) {
			super(fm);

			this.mainmodel = mainmodel;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// return main_model.get(position).getText();
			return "";
		}

		@Override
		public int getCount() {
			return mainmodel.size();
		}

		@Override
		public Fragment getItem(int position) {
			return NewsDetail.newInstance(mainmodel.get(position)
					.getNews_list_passingurl());
		}

	}
}

package com.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.fragment.top.TopNewslist;
import com.grasshopper.newsapp.HomeScreen;
import com.grasshopper.newsapp.R;
import com.model.SliderModel;

public class NewsCategory extends Fragment {

	private View view;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private int main_cat_id = 0;
	private ArrayList<SliderModel> main_model;
	private ArrayList<SliderModel> sub_main_model = new ArrayList<SliderModel>();

	private ArrayList<SliderModel> new_main_model = new ArrayList<SliderModel>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.news_catagory, container, false);

		main_model = ((HomeScreen) getActivity()).main_model;

		// if (main_model.size() > 0)
		main_cat_id = main_model
				.get(((HomeScreen) getActivity()).list_position).getCat_id();

		for (int i = 0; i < main_model.size(); i++) {
			if (main_model.get(i).getParent_cat_id() == main_cat_id) {
				sub_main_model.add(main_model.get(i));
				new_main_model.add(main_model.get(i));
			}
		}

		// this condition if it has no sub categories
		if (sub_main_model.size() == 0) {
			sub_main_model.add(main_model
					.get(((HomeScreen) getActivity()).list_position));

			new_main_model.add(main_model
					.get(((HomeScreen) getActivity()).list_position));
		}

		// new_main_model = sub_main_model;

		for (int i = 0; i < sub_main_model.size(); i++) {

			if (sub_main_model.get(i).getCat_type_tag().equalsIgnoreCase("TP")) {

				new_main_model.remove(0);

				new_main_model.add(0, sub_main_model.get(i));

				new_main_model.remove(i);
				new_main_model.add(i, sub_main_model.get(0));
			}

		}

		tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);

		pager = (ViewPager) view.findViewById(R.id.pager);

		// initialiseTabHost();

		// pager.setCurrentItem(new_main_model.size());

		adapter = new MyPagerAdapter(getChildFragmentManager(), new_main_model);

		// wrap pager to provide infinite paging with wrap-around
		// PagerAdapter wrappedAdapter = new InfinitePagerAdapter(adapter);

		// pager.setAdapter(wrappedAdapter);

		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
						.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		// pager.setOnPageChangeListener(new OnPageChangeListener() {
		//
		// @Override
		// public void onPageSelected(int position) {
		// // TODO Auto-generated method stub
		//
		// Log.d("ankit", "I am here::::" + position);
		//
		// }
		//
		// @Override
		// public void onPageScrolled(int arg0, float arg1, int arg2) {
		// // TODO Auto-generated method stub
		//
		// int pos = pager.getCurrentItem();
		// mTabHost.setCurrentTab(pos);
		//
		// }
		//
		// @Override
		// public void onPageScrollStateChanged(int arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		tabs.setViewPager(pager);

		return view;
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		ArrayList<SliderModel> main_model;

		public MyPagerAdapter(FragmentManager fm,
				ArrayList<SliderModel> main_model) {
			super(fm);
			this.main_model = main_model;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return main_model.get(position).getText();
		}

		@Override
		public int getCount() {
			return main_model.size();
		}

		@Override
		public Fragment getItem(int position) {

			if (main_model.get(position).getCat_type_tag()
					.equalsIgnoreCase("TP")) {

				return new TopNewslist();

			} else

				return SubCategory.newInstance(main_model.get(position)
						.getCat_id());
		}

	}

	// @Override
	// public void onTabChanged(String tabId) {
	// // TODO Auto-generated method stub
	//
	// int pos = this.mTabHost.getCurrentTab();
	// pager.setCurrentItem(pos);
	//
	// }

	// private void initialiseTabHost() {
	// mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);
	// mTabHost.setup();
	//
	// // TODO Put here your Tabs
	//
	// for (int i = 0; i < new_main_model.size(); i++) {
	//
	// mTabHost.addTab(this.mTabHost.newTabSpec(
	// new_main_model.get(i).getText()).setIndicator(
	// new_main_model.get(i).getText()));
	//
	// }
	//
	// mTabHost.setOnTabChangedListener(this);
	// }

	// @Override
	// public void onPageScrollStateChanged(int position) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onPageScrolled(int position, float arg1, int arg2) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onPageSelected(int position) {
	// // TODO Auto-generated method stub
	//
	// // Log.d("ankit", "position:::" + position);
	//
	// // int pageCount = sub_main_model.size() - 1;
	// // if (position == 0) {
	// // pager.setCurrentItem(pageCount, false);
	// // } else if (position == pageCount) {
	// // pager.setCurrentItem(1, false);
	// // }
	//
	// }
}

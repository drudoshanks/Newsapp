package com.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fragment.TopNews;
import com.fragment.top.TopNewsDetailScrore;
import com.grasshopper.newsapp.HomeScreen;
import com.grasshopper.newsapp.R;
import com.model.Top_news_Model;
import com.squareup.picasso.Picasso;
import com.utils.Utils;

public class Top_news_adapter extends ArrayAdapter<Top_news_Model> {

	Context context;
	ArrayList<Top_news_Model> list;
	int resource;

	public static final int HEADER = 0;
	public static final int LIST = 1;

	private LayoutInflater mInflater;

	TopNews fragment;

	ArrayList<String> passingUrls;

	public Top_news_adapter(Context context, int resource,
			ArrayList<Top_news_Model> list, TopNews fragment,
			ArrayList<String> passingUrls) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub

		this.context = context;
		this.list = list;
		this.resource = resource;
		this.fragment = fragment;
		this.passingUrls = passingUrls;

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getItemViewType(int position) {
		if (list.get(position).getItem_type() == 0)
			// header
			return HEADER;

		else

			return LIST;

	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		int rowType = getItemViewType(position);

		final Top_news_Model model = list.get(position);

		if (convertView == null) {
			holder = new ViewHolder();
			switch (rowType) {
			case HEADER:
				convertView = mInflater.inflate(
						R.layout.top_news_list_item_header, null);
				holder.top_list_item_text = (TextView) convertView
						.findViewById(R.id.top_list_item_text);
				holder.top_list_item_image = (ImageView) convertView
						.findViewById(R.id.top_list_item_image);

				holder.top_list_item_image.requestLayout();

				holder.top_list_item_image.getLayoutParams().height = Utils
						.getImageHeight(context);

				holder.header_layout = (RelativeLayout) convertView
						.findViewById(R.id.header_layout);
				holder.top_list_item_header = (TextView) convertView
						.findViewById(R.id.top_list_item_header);
				break;
			case LIST:
				convertView = mInflater.inflate(R.layout.top_news_list_item,
						null);
				holder.top_list_item_text_left = (TextView) convertView
						.findViewById(R.id.top_list_item_text_left);
				holder.top_list_item_text_right = (TextView) convertView
						.findViewById(R.id.top_list_item_text_right);
				holder.top_list_item_image_left = (ImageView) convertView
						.findViewById(R.id.top_list_item_image_left);

				holder.top_list_item_image_left.requestLayout();

				holder.top_list_item_image_left.getLayoutParams().height = Utils
						.getImageHeight(context) / 2;

				holder.top_list_item_image_right = (ImageView) convertView
						.findViewById(R.id.top_list_item_image_right);

				holder.top_list_item_image_right.requestLayout();

				holder.top_list_item_image_right.getLayoutParams().height = Utils
						.getImageHeight(context) / 2;

				holder.left_layout = (RelativeLayout) convertView
						.findViewById(R.id.left_layout);

				holder.right_layout = (RelativeLayout) convertView
						.findViewById(R.id.right_layout);
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (model.getItem_type() == 0) {
			// do something to header data
			holder.top_list_item_header
					.setText(Html.fromHtml(model.getTitle()));

			// right code
			// holder.top_list_item_text.setText(Html.fromHtml(model
			// .getTitle_sub()));

			// change code
			holder.top_list_item_text.setText(Html.fromHtml(model.getTitle()));

			try {
				// holder.top_list_item_image

				String image_url = model.getImage_url();
				image_url = image_url.replace(" ", "%20");

				Picasso.with(context).load(Utils._url + image_url).fit()
						.into(holder.top_list_item_image);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			// if (model.isReadHead()) {
			// Drawable myIcon = holder.top_list_item_image.getDrawable();
			// myIcon.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
			// holder.top_list_item_image.setImageDrawable(myIcon);
			//
			// setBlackAndWhite(holder.top_list_item_image);
			// }

			holder.header_layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// list.get(getPosition(model)).setReadHead(true);
					// notifyDataSetChanged();
					addNewslayout(model.getPassing_url(), model.getCount());
				}
			});
		} else if (model.getItem_type() == 1) {
			// do something to item data
			// right code
			// holder.top_list_item_text_left.setText(Html.fromHtml(model
			// .getTitle_sub_left()));
			// holder.top_list_item_text_right.setText(Html.fromHtml(model
			// .getTitle_sub_right()));

			// delete code
			holder.top_list_item_text_left.setText(Html.fromHtml(model
					.getTitle_left()));
			holder.top_list_item_text_right.setText(Html.fromHtml(model
					.getTitle_right()));

			try {

				String image_url_left = model.getImage_url_left();
				image_url_left = image_url_left.replace(" ", "%20");

				Picasso.with(context)
						.load(Utils._url + model.getImage_url_left()).fit()
						.into(holder.top_list_item_image_left);

				String image_url_right = model.getImage_url_right();
				image_url_right = image_url_right.replace(" ", "%20");

				Picasso.with(context)
						.load(Utils._url + model.getImage_url_right()).fit()
						.into(holder.top_list_item_image_right);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			// final ImageView image_left = holder.top_list_item_image_left;
			// final ImageView image_right = holder.top_list_item_image_right;

			// if (model.isReadLeft()) {
			// Drawable myIcon = holder.top_list_item_image_left.getDrawable();
			// myIcon.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
			// holder.top_list_item_image_left.setImageDrawable(myIcon);
			//
			// setBlackAndWhite(holder.top_list_item_image_left);
			// }

			holder.left_layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// getPosition(model)
					// list.get(getPosition(model)).setReadLeft(true);
					// notifyDataSetChanged();
					addNewslayout(model.getPassing_url_left(),
							model.getLeftCount());
				}
			});

			// if (model.isReadRight()) {
			// Drawable myIcon = holder.top_list_item_image_right
			// .getDrawable();
			// myIcon.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
			// holder.top_list_item_image_right.setImageDrawable(myIcon);
			//
			// setBlackAndWhite(holder.top_list_item_image_right);
			// }

			holder.right_layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// list.get(getPosition(model)).setReadRight(true);
					// notifyDataSetChanged();
					addNewslayout(model.getPassing_url_right(),
							model.getRightCount());
				}
			});
		}

		return convertView;
	}

	public static class ViewHolder {
		public ImageView top_list_item_image_left, top_list_item_image_right,
				top_list_item_image;
		public TextView top_list_item_text_left, top_list_item_text_right,
				top_list_item_text, top_list_item_header;
		public RelativeLayout header_layout, left_layout, right_layout;
	}

	// private void addNewslayout(String passing_url, String title) {
	//
	// if (!passing_url.equalsIgnoreCase("")) {
	// TopNewsDetailScrore f = new TopNewsDetailScrore();
	// Bundle b = new Bundle();
	// b.putParcelableArrayList(Utils.model_send_intent, list);
	//
	// // b.putString(Utils.title_send_intent, title);
	// f.setArguments(b);
	//
	// ((HomeScreen) fragment.getActivity()).addfragment(f,
	// "TopNewsDetailScrore");
	// }
	//
	// }

	private void addNewslayout(String passing_url, int value) {

		if (!passing_url.equalsIgnoreCase("")) {

			TopNewsDetailScrore f = new TopNewsDetailScrore();
			Bundle b = new Bundle();
			b.putStringArrayList(Utils.model_send_intent, passingUrls);
			b.putInt(Utils.position_send_intent, value);

			// b.putString(Utils.title_send_intent, title);
			f.setArguments(b);

			((HomeScreen) fragment.getActivity()).addfragment(f,
					"TopNewsDetailScrore");
		}

	}



}

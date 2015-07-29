package com.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grasshopper.newsapp.R;
import com.model.OpListModel;
import com.squareup.picasso.Picasso;
import com.utils.Utils;

public class OpListAdapter extends ArrayAdapter<OpListModel> {

	Context context;

	public OpListAdapter(Context context, int resource,
			List<OpListModel> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub

		this.context = context;
	}

	private static class ViewHolder {
		ImageView news_list_item_image;
		TextView news_list_item_title, news_list_item_date,
				news_list_item_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		OpListModel model = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder; // view lookup cache stored in tag
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.news_list_item, parent,
					false);

			viewHolder.news_list_item_title = (TextView) convertView
					.findViewById(R.id.news_list_item_title);

			viewHolder.news_list_item_date = (TextView) convertView
					.findViewById(R.id.news_list_item_date);

			viewHolder.news_list_item_image = (ImageView) convertView
					.findViewById(R.id.news_list_item_image);

			viewHolder.news_list_item_image.requestLayout();

			viewHolder.news_list_item_image.getLayoutParams().height = Utils
					.getImageHeight(context) / 3;

			viewHolder.news_list_item_text = (TextView) convertView
					.findViewById(R.id.news_list_item_text);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// Populate the data into the template view using the data object
		viewHolder.news_list_item_title.setText(Html.fromHtml(model
				.getOp_list_title()));

		// this is test code
		viewHolder.news_list_item_text.setText(Html.fromHtml(model
				.getOp_list_title_sub()));

		viewHolder.news_list_item_date.setText(Html.fromHtml(model
				.getOp_list_publish_date()));

		try {

			String image_url = model.getOp_list_image_url();
			image_url = image_url.replace(" ", "%20");

			Picasso.with(context).load(Utils._url + image_url).fit()
					.into(viewHolder.news_list_item_image);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// Return the completed view to render on screen
		return convertView;
	}
}

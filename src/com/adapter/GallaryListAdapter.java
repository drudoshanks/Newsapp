package com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grasshopper.newsapp.R;
import com.model.VideoScreenModel;
import com.squareup.picasso.Picasso;
import com.utils.Utils;

public class GallaryListAdapter extends ArrayAdapter<VideoScreenModel> {

	Context context;

	public GallaryListAdapter(Context context, int resource,
			ArrayList<VideoScreenModel> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub

		this.context = context;
	}

	private static class ViewHolder {
		TextView gallary_list_item_header, gallary_list_item_date;
		ImageView gallary_list_item_image;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		VideoScreenModel model = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder; // view lookup cache stored in tag
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.gallary_image_list_item,
					parent, false);

			viewHolder.gallary_list_item_date = (TextView) convertView
					.findViewById(R.id.gallary_list_item_date);

			viewHolder.gallary_list_item_header = (TextView) convertView
					.findViewById(R.id.gallary_list_item_header);

			viewHolder.gallary_list_item_image = (ImageView) convertView
					.findViewById(R.id.gallary_list_item_image);
			
			viewHolder.gallary_list_item_image .requestLayout();

			viewHolder.gallary_list_item_image .getLayoutParams().height = Utils
					.getImageHeight(context);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// Populate the data into the template view using the data object
		viewHolder.gallary_list_item_date.setText(Html.fromHtml(model
				.getVideo_screen_time()));

		viewHolder.gallary_list_item_header.setText(Html.fromHtml(model
				.getVideo_screen_title()));

		try {

			String url = Utils._url + model.getVideo_screen_image_url();

			url = url.replace(" ", "%20");

			Picasso.with(context).load(url).fit()
					.into(viewHolder.gallary_list_item_image);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// Return the completed view to render on screen
		return convertView;
	}
}

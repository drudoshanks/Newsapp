package com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grasshopper.newsapp.R;
import com.model.VideoScreenModel;
import com.utils.Utils;

public class VideoScreenlistAdapter extends ArrayAdapter<VideoScreenModel> {

	Context context;

	public VideoScreenlistAdapter(Context context, int resource,
			ArrayList<VideoScreenModel> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub

		this.context = context;
	}

	private static class ViewHolder {
		TextView video_screen_list_item_date, video_screen_list_text;
		ImageView video_screen_list_item_image;
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
			convertView = inflater.inflate(R.layout.video_screen_list_item,
					parent, false);

			viewHolder.video_screen_list_item_date = (TextView) convertView
					.findViewById(R.id.video_screen_list_item_date);

			viewHolder.video_screen_list_text = (TextView) convertView
					.findViewById(R.id.video_screen_list_text);

			viewHolder.video_screen_list_item_image = (ImageView) convertView
					.findViewById(R.id.video_screen_list_item_image);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// Populate the data into the template view using the data object
		viewHolder.video_screen_list_item_date.setText(Html.fromHtml(model
				.getVideo_screen_time()));

		viewHolder.video_screen_list_text.setText(Html.fromHtml(model
				.getVideo_screen_title()));

		

		// Return the completed view to render on screen
		return convertView;
	}
}

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
import com.model.ToplistModel;
import com.squareup.picasso.Picasso;
import com.utils.Utils;

public class TopNewsAdapterNew extends ArrayAdapter<ToplistModel> {

	Context context;

	public TopNewsAdapterNew(Context context, int resource,
			ArrayList<ToplistModel> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub

		this.context = context;
	}

	private static class ViewHolder {
		TextView top_list_item_header, top_list_item_text;
		ImageView top_list_item_image;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		// Get the data item for this position
		final ToplistModel model = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder; // view lookup cache stored in tag
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.top_news_list_item_new,
					parent, false);

			viewHolder.top_list_item_header = (TextView) convertView
					.findViewById(R.id.top_list_item_header);

			viewHolder.top_list_item_text = (TextView) convertView
					.findViewById(R.id.top_list_item_text);

			viewHolder.top_list_item_image = (ImageView) convertView
					.findViewById(R.id.top_list_item_image);

			viewHolder.top_list_item_image.requestLayout();

			viewHolder.top_list_item_image.getLayoutParams().height = Utils
					.getImageHeight(context);

			// viewHolder.top_list_item_image.setTag("false");

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// Animation animation = AnimationUtils.loadAnimation(getContext(),
		// (position > lastPosition) ? R.anim.up_from_bottom
		// : R.anim.down_from_top);
		// convertView.startAnimation(animation);
		// lastPosition = position;

		// Populate the data into the template view using the data object
		viewHolder.top_list_item_header
				.setText(Html.fromHtml(model.getTitle()));

		viewHolder.top_list_item_text.setText(Html.fromHtml(model
				.getTitle_sub()));

		try {

			String videoUrl = (Utils._url + model.getImage_url().replace(" ",
					"%20"));

			Picasso.with(context).load(videoUrl).fit()
					.into(viewHolder.top_list_item_image);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// if (model.isRead()) {
		// Drawable myIcon = viewHolder.top_list_item_image.getDrawable();
		//
		// if (myIcon != null) {
		// myIcon.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
		// viewHolder.top_list_item_image.setImageDrawable(myIcon);
		//
		// // float brightness = 50;
		// //
		// // float[] colorMatrix = { 0.33f, 0.33f, 0.33f, 0, brightness, // red
		// // 0.33f, 0.33f, 0.33f, 0, brightness, // green
		// // 0.33f, 0.33f, 0.33f, 0, brightness, // blue
		// // 0, 0, 0, 1, 0 // alpha
		// // };
		// //
		// // ColorFilter colorFilter = new ColorMatrixColorFilter(
		// // colorMatrix);
		// // viewHolder.top_list_item_image.setColorFilter(colorFilter);
		//
		// // Utils.setBlackAndWhite(viewHolder.top_list_item_image);
		// }
		// }
		//
		// // Log.d("ankit", "Image change");
		//
		// } else if (((String) viewHolder.top_list_item_image.getTag())
		// .equalsIgnoreCase("false")) {
		//
		// // Log.d("ankit", "Do Nothing");
		//
		// }

		// viewHolder.top_list_item_image
		// .setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// // model.setRead(true);
		// v.setTag("true");
		// notifyDataSetChanged();
		//
		// }
		// });

		// Return the completed view to render on screen
		return convertView;
	}
}

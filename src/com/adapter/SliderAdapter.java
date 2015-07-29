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
import com.model.SliderModel;
import com.squareup.picasso.Picasso;
import com.utils.Utils;

public class SliderAdapter extends ArrayAdapter<SliderModel> {

	Context context;

	public SliderAdapter(Context context, List<SliderModel> objects) {
		super(context, R.layout.slider_item, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	private static class ViewHolder {
		TextView slider_item_text;
		ImageView slider_item_image;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		SliderModel model = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder; // view lookup cache stored in tag
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.slider_item, parent, false);

			viewHolder.slider_item_text = (TextView) convertView
					.findViewById(R.id.slider_item_text);

			viewHolder.slider_item_image = (ImageView) convertView
					.findViewById(R.id.slider_item_image);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// Populate the data into the template view using the data object
		viewHolder.slider_item_text.setText(Html.fromHtml(model.getText()));

		try {

			String image_url = model.getImage_url();
			image_url = image_url.replace(" ", "%20");
			

			Picasso.with(context).load(Utils._url +image_url)
			.into(viewHolder.slider_item_image);
			
//			Picasso.with(context).load(Utils._url+image_url)
//					.into(viewHolder.slider_item_image);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// Return the completed view to render on screen
		return convertView;
	}
}

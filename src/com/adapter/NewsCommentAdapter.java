package com.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.grasshopper.newsapp.R;
import com.model.NewsCommentModel;

public class NewsCommentAdapter extends ArrayAdapter<NewsCommentModel> {

	Context context;

	public NewsCommentAdapter(Context context, int resource,
			List<NewsCommentModel> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub

		this.context = context;
	}

	private static class ViewHolder {
		TextView news_comment_name, news_comment_comment, news_comment_time;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		NewsCommentModel model = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder; // view lookup cache stored in tag
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.news_comment_item, parent,
					false);

			viewHolder.news_comment_name = (TextView) convertView
					.findViewById(R.id.news_comment_name);

			viewHolder.news_comment_comment = (TextView) convertView
					.findViewById(R.id.news_comment_comment);

			viewHolder.news_comment_time = (TextView) convertView
					.findViewById(R.id.news_comment_time);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// Populate the data into the template view using the data object
//		viewHolder.news_comment_name.setText(Html.fromHtml(model
//				.getNews_comment_name()));
//
//		viewHolder.news_comment_comment.setText(Html.fromHtml(model
//				.getNews_comment_comment()));
//
//		viewHolder.news_comment_time.setText(Html.fromHtml(model
//				.getNews_comment_time()));f

		// Return the completed view to render on screen
		return convertView;
	}

}

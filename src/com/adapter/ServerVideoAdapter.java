package com.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fragment.server_videos.MyYoutubeFragment;
import com.fragment.server_videos.ServerVideos;
import com.fragment.server_videos.YouTubeActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.android.youtube.player.YouTubeThumbnailView.OnInitializedListener;
import com.grasshopper.newsapp.HomeScreen;
import com.grasshopper.newsapp.R;
import com.model.ServerVideoModel;
import com.utils.Utils;

public class ServerVideoAdapter extends ArrayAdapter<ServerVideoModel> {

	Context context;
	ServerVideos frag;

	public ServerVideoAdapter(Context context, int resource,
			List<ServerVideoModel> objects, ServerVideos frag) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub

		this.context = context;
		this.frag = frag;
	}

	private static class ViewHolder {

		// YouTubePlayerView youtube_view;

		TextView youtube_text;

		YouTubeThumbnailView youtubeimage;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		final ServerVideoModel model = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder; // view lookup cache stored in tag
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.server_video_list_item,
					parent, false);

			// viewHolder.youtube_view = (YouTubePlayerView) convertView
			// .findViewById(R.id.youtube_view);
			viewHolder.youtubeimage = (YouTubeThumbnailView) convertView
					.findViewById(R.id.youtubeimage);

			viewHolder.youtube_text = (TextView) convertView
					.findViewById(R.id.youtube_text);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// Populate the data into the template view using the data object
		// viewHolder.news_list_item_title.setText(Html.fromHtml(model
		// .getNews_list_title()));

		// viewHolder.youtube_view.initialize(Utils.GOOGLE_API_KEY, new
		// OnInitializedListener() {
		//
		// @Override
		// public void onInitializationSuccess(Provider arg0, YouTubePlayer
		// player,
		// boolean arg2) {
		// // TODO Auto-generated method stub
		//
		// player.loadVideo(model.getYoutubeVideoCode());
		//
		// }
		//
		// @Override
		// public void onInitializationFailure(Provider arg0,
		// YouTubeInitializationResult arg1) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		viewHolder.youtubeimage.setTag(model.getYt_list_title());
		viewHolder.youtubeimage.initialize(Utils.GOOGLE_API_KEY,
				new OnInitializedListener() {

					@Override
					public void onInitializationSuccess(
							YouTubeThumbnailView arg0,
							YouTubeThumbnailLoader loader) {
						// TODO Auto-generated method stub
						loader.setVideo(model.getYt_code());
					}

					@Override
					public void onInitializationFailure(
							YouTubeThumbnailView arg0,
							YouTubeInitializationResult arg1) {
						// TODO Auto-generated method stub

					}
				});

		viewHolder.youtubeimage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {

					// MyYoutubeFragment f = MyYoutubeFragment.newInstance();

					// ((HomeScreen) frag.getActivity()).addfragment(
					// UTFragment.newInstance(model.getYt_code()),
					// "MyYoutubeFragment");

					// ((HomeScreen) frag.getActivity()).addfragment(
					// MyYoutubeFragment.Instance(model.getYt_code()),
					// "MyYoutubeFragment");

					Intent intent = new Intent(context, YouTubeActivity.class);
					intent.putExtra("videoID", model.getYt_code());
					context.startActivity(intent);

				} catch (Exception e) {
					// TODO: handle exception
					// rare exception
					e.printStackTrace();
				}
			}
		});

		viewHolder.youtube_text.setText(model.getYt_list_title());

		// Return the completed view to render on screen
		return convertView;
	}
}

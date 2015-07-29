package com.fragment.live_video;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.grasshopper.newsapp.R;

public class LiveVideo extends Fragment {

	View view;
	ImageView video_button;

	private String pathToFileOrUrl = "rtsp://jlive.itv.net.in:1935/jantatv/myStream.sdp";
	private VideoView mVideoView;

	private RelativeLayout live_video_relative;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.live_video, container, false);

		getActivity().setRequestedOrientation(
				ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		if (!LibsChecker.checkVitamioLibs(getActivity())) {

			Toast.makeText(getActivity(), "Cannot play this video",
					Toast.LENGTH_LONG).show();

		}

		// getActivity().setRequestedOrientation(
		// ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		mVideoView = (VideoView) view.findViewById(R.id.surface_view);

		video_button = (ImageView) view.findViewById(R.id.video_button);

		video_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				video_button.setVisibility(View.GONE);
				mVideoView.setVisibility(View.VISIBLE);
				startPlay();
			}
		});

		if (pathToFileOrUrl == "") {
			Toast.makeText(getActivity(),
					"Please set the video path for your media file",
					Toast.LENGTH_LONG).show();

		} else {

			/*
			 * Alternatively,for streaming media you can use
			 * mVideoView.setVideoURI(Uri.parse(URLstring));
			 */
			mVideoView.setVideoPath(pathToFileOrUrl);
			mVideoView.setMediaController(new MediaController(getActivity()));
			mVideoView.requestFocus();

			mVideoView
					.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
						@Override
						public void onPrepared(MediaPlayer mediaPlayer) {
							// optional need Vitamio 4.0
							mediaPlayer.setPlaybackSpeed(1.0f);
						}
					});
		}

		live_video_relative = (RelativeLayout) view
				.findViewById(R.id.live_video_relative);
		live_video_relative.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		return view;
	}

	public void startPlay() {
		if (!TextUtils.isEmpty(pathToFileOrUrl)) {
			mVideoView.setVideoPath(pathToFileOrUrl);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		getActivity().setRequestedOrientation(
				ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

	}
}

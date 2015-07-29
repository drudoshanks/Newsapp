package com.fragment.server_videos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.PlaylistEventListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.grasshopper.newsapp.R;
import com.utils.Utils;

public class UTFragment extends YouTubePlayerSupportFragment {

	View view;
	private static final String ARG_POSITION = "position";
	Context context;

	String video_id = "";

	private YouTubePlayerView youTubePlayerView;

	static YouTubePlayer _player;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.youtube_video, container, false);

		Bundle bundle = this.getArguments();
		video_id = bundle.getString(ARG_POSITION, "");

		youTubePlayerView = (YouTubePlayerView) view.findViewById(R.id.player);

		youTubePlayerView.initialize(Utils.GOOGLE_API_KEY,
				new OnInitializedListener() {

					@Override
					public void onInitializationSuccess(Provider arg0,
							YouTubePlayer player, boolean arg2) {
						// TODO Auto-generated method stub

						_player = player;

						// player.setPlayerStateChangeListener(playerStateChangeListener);
						// player.setPlaybackEventListener(playbackEventListener);

						if (video_id != null) {

							player.cueVideo(video_id);

							player.setFullscreen(true);

							player.setPlaybackEventListener(new PlaybackEventListener() {

								@Override
								public void onStopped() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onSeekTo(int arg0) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onPlaying() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onPaused() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onBuffering(boolean arg0) {
									// TODO Auto-generated method stub

								}
							});

							player.setPlayerStateChangeListener(new PlayerStateChangeListener() {

								@Override
								public void onVideoStarted() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onVideoEnded() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onLoading() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onLoaded(String arg0) {
									// TODO Auto-generated method stub

									_player.setPlayerStyle(PlayerStyle.CHROMELESS);

									new CountDownTimer(300, 100) {

										@Override
										public void onTick(
												long millisUntilFinished) {
											// TODO Auto-generated method stub

										}

										@Override
										public void onFinish() {
											// TODO Auto-generated method stub
											_player.play();
										}
									}.start();

								}

								@Override
								public void onError(ErrorReason arg0) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAdStarted() {
									// TODO Auto-generated method stub

								}
							});

							player.setPlaylistEventListener(new PlaylistEventListener() {

								@Override
								public void onPrevious() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onPlaylistEnded() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onNext() {
									// TODO Auto-generated method stub

								}
							});

						}
					}

					@Override
					public void onInitializationFailure(Provider arg0,
							YouTubeInitializationResult arg1) {
						// TODO Auto-generated method stub

					}
				});

		return view;
	}

	public static UTFragment newInstance(String video_id) {
		UTFragment f = new UTFragment();
		Bundle b = new Bundle();
		b.putString(ARG_POSITION, video_id);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		context = activity;
	}

}

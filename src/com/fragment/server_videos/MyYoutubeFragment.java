package com.fragment.server_videos;

import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;

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
import com.utils.Utils;

public class MyYoutubeFragment extends YouTubePlayerSupportFragment {
	private static final String API_KEY = Utils.GOOGLE_API_KEY;

	// private static String videoId = "qNggvk8QtaY";
	static YouTubePlayer _player;

	public static MyYoutubeFragment Instance(final String videoId) {

		MyYoutubeFragment playerYouTubeFrag = new MyYoutubeFragment();

		try {

			playerYouTubeFrag.initialize(API_KEY, new OnInitializedListener() {

				@Override
				public void onInitializationSuccess(Provider arg0,
						YouTubePlayer player, boolean arg2) {
					// TODO Auto-generated method stub

					_player = player;

					// player.setPlayerStateChangeListener(playerStateChangeListener);
					// player.setPlaybackEventListener(playbackEventListener);

					if (videoId != null) {

						player.cueVideo(videoId);

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
									public void onTick(long millisUntilFinished) {
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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}

		return playerYouTubeFrag;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		getActivity().setRequestedOrientation(
				ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

	}
}

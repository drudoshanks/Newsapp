package com.fragment.server_videos;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.PlaylistEventListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.grasshopper.newsapp.R;
import com.utils.Utils;

public class YouTubeActivity extends YouTubeBaseActivity {

	private YouTubePlayerView youTubePlayerView;
	static YouTubePlayer _player;

	String videoId = "";
	SeekBar u_seekbar;
	int seekPos = 0;
	CountDownTimer timer;
	int totoalTime = 0;

	// boolean playPause = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.utube_activity);

		u_seekbar = (SeekBar) findViewById(R.id.u_seekbar);
		u_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub

			}
		});

		videoId = getIntent().getStringExtra("videoID");

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);

		youTubePlayerView.initialize(Utils.GOOGLE_API_KEY,
				new OnInitializedListener() {

					@Override
					public void onInitializationSuccess(Provider provider,
							YouTubePlayer player, boolean arg2) {
						// TODO Auto-generated method stub

						_player = player;

						if (videoId != null) {

							_player.cueVideo(videoId);

							_player.setPlaybackEventListener(new PlaybackEventListener() {

								@Override
								public void onStopped() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onSeekTo(int position) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onPlaying() {
									// TODO Auto-generated method stub

									// playPause = true;
									startCountDownTimer();

									Log.d("ankit", "I am play ");
								}

								@Override
								public void onPaused() {
									// TODO Auto-generated method stub

									// playPause = false;

									timer.onFinish();

									Log.d("ankit", "I am paused ");

								}

								@Override
								public void onBuffering(boolean arg0) {
									// TODO Auto-generated method stub

								}
							});

							_player.setPlayerStateChangeListener(new PlayerStateChangeListener() {

								@Override
								public void onVideoStarted() {
									// TODO Auto-generated method stub

									u_seekbar.setMax((int) (_player
											.getDurationMillis() / 1000));

									totoalTime = (int) (_player
											.getDurationMillis());
									// startCountDownTimer();

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
									_player.play();

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

							_player.setPlaylistEventListener(new PlaylistEventListener() {

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

		Button play = (Button) findViewById(R.id.play);
		play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_player.play();
			}
		});

		Button pause = (Button) findViewById(R.id.pause);
		pause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_player.pause();
			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	private void startCountDownTimer() {
		timer = new CountDownTimer(totoalTime, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub

				Log.d("ankit", "m here");

				totoalTime = (int) millisUntilFinished;
				u_seekbar.setProgress(seekPos);

				seekPos++;

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
			}
		}.start();
	}

}

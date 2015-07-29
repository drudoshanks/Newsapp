package com.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.intrasonics.Decoder;
import com.intrasonics.DecoderConfidence;
import com.intrasonics.DecoderListener;
import com.intrasonics.ITokenUpdateListener;
import com.intrasonics.triggerengine.Codeword;
import com.intrasonics.triggerengine.ITriggerEngine;
import com.intrasonics.triggerengine.ITriggerEngineListener;

public class MusicService extends Service implements DecoderListener,
		ITriggerEngineListener, ITokenUpdateListener {

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private SimpleDateFormat dateSDF = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm z");
	private Decoder mDecoder = null;

	private final int msgTypeMessage = 0;
	private final int msgTypeCodeword = 1;
	private final int msgTypeConfidence = 2;
	private final int msgTypeFailed = 3;
	private final int msgTokenUpdated = 4;

	private String mTokenFileName = "125.json";

	private ITriggerEngine triggerEngine = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		// Get a reference to the Intrasonics Decoder
		mDecoder = Decoder.getInstance();

		// Pass the application context into the decoder
		mDecoder.setApplicationContext(MusicService.this);

		// Register as a decoder listener
		mDecoder.registerListener(this);

		try {
			// Read in a licence token and pass it to the decoder
			mDecoder.setToken(readTokenFromFile(mTokenFileName));
		} catch (Exception ex) {
			uiUpdateHandler.sendMessage(uiUpdateHandler.obtainMessage(
					msgTypeFailed, ex.getMessage()));
		}

		// Connect to the Trigger Engine
		triggerEngine = (ITriggerEngine) mDecoder.connectEngineListener(this,
				ITriggerEngine.class);

		if (triggerEngine == null) {
			uiUpdateHandler.sendMessage(uiUpdateHandler.obtainMessage(
					msgTypeFailed, "Failed to connect to Trigger Engine"));
		}

		// Update the UI with the default decoder confidence
		updateDecoderConfidence(DecoderConfidence.Invalid);

		// Start the decoder
		mDecoder.startDecoding();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		// Unregister this object as the listener for the Trigger Engine
		if (triggerEngine != null) {
			mDecoder.disconnectFromEngine(this, triggerEngine);
		}

		// Stop the decoder.
		mDecoder.stopDecoding();

		// Unregister as a decoder listener
		mDecoder.unRegisterListener(this);
		mDecoder = null;

		startService(new Intent(MusicService.this, MusicService.class));

	}

	/**
	 * ITriggerEngineListener method for handling a codeword
	 * 
	 * @param codeword
	 *            The codeword and timestamp received from the decoder
	 */
	@Override
	public void onCodewordReceived(Codeword codeword) {
		uiUpdateHandler.sendMessage(uiUpdateHandler.obtainMessage(
				msgTypeCodeword, codeword));
	}

	/**
	 * Called when the Intrasonics decoder token is updated.
	 *
	 * @param updateDate
	 *            the time/date at which the token was updated.
	 * @param startDate
	 *            the time/date at which the token becomes valid.
	 * @param endDate
	 *            the time/date at which the token becomes invalid.
	 */
	@Override
	public void onTokenUpdated(String tokenId, Date startDate, Date endDate,
			long ttl) {
		dateSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
		String tokenUpdateString = "Token updated to " + tokenId
				+ ";\r\n\tstart: " + dateSDF.format(startDate) + "\r\n\tend: "
				+ dateSDF.format(endDate) + "\r\n\tttl:" + ttl;

		uiUpdateHandler.sendMessage(uiUpdateHandler.obtainMessage(
				msgTokenUpdated, tokenUpdateString));
	}

	/**
	 * Handler on the main thread, used when an Intrasonics Decoder event or
	 * error is received, to transfer processing onto the main thread. This
	 * handler simply inserts details of the error or event at the start of the
	 * TextView to produce a reverse-ordered list.
	 */
	private Handler uiUpdateHandler = new Handler() {
		public void handleMessage(Message msg) {
			String newText = "";

			switch (msg.what) {
			case msgTypeMessage: {
				newText = sdf.format(new Date()) + " " + (String) msg.obj;

				Log.d("ankit", newText);

				break;
			}
			case msgTypeCodeword: {
				Codeword codeword = (Codeword) msg.obj;
				newText = sdf.format(new Date(codeword.timestamp))
						+ " Codeword : " + codeword.codeword;
				// newText += "\n" + textView.getText().toString();
				// textView.setText(newText);
				Log.d("ankit", newText);
				break;
			}
			case msgTypeConfidence: {
				updateDecoderConfidence((DecoderConfidence) msg.obj);
				break;
			}
			case msgTypeFailed: {
				Log.d("ankit", newText);
				break;
			}
			case msgTokenUpdated: {
				Log.d("ankit", newText);
				break;
			}
			default:
				break;
			}
		}
	};

	/**
	 * Updates the decoder confidence animation and text on the screen, or for
	 * an unknown confidence level, removes the animation altogether.
	 * 
	 * @param idc
	 *            The current decoder confidence level
	 */
	
	private void updateDecoderConfidence(DecoderConfidence confidence) {
		if (confidence != null) {
			if (confidence == DecoderConfidence.Invalid) {

			} else {
				// Apply animation
				if (confidence == DecoderConfidence.High) {

				} else if (confidence == DecoderConfidence.Medium) {

				} else if (confidence == DecoderConfidence.Low) {

				} else {

				}

			}

		}
	}

	/**
	 * Read a token file into a string, ready for passing to the decoder.
	 * 
	 * @param fileName
	 *            The name of the file containing the token data
	 * 
	 * @return A String holding the contents of the specified file
	 * 
	 * @throws IOException
	 *             Upon failure to open and read the file
	 */
	public String readTokenFromFile(String fileName) throws IOException {
		String token = "empty";

		try {
			InputStream iS = getResources().getAssets().open(fileName);

			byte[] buffer = new byte[iS.available()];
			iS.read(buffer);

			ByteArrayOutputStream oS = new ByteArrayOutputStream();

			oS.write(buffer);
			token = oS.toString();
			oS.close();
			iS.close();
		} catch (IOException e) {
			Log.d("ankit", e.toString());
		}
		return token;
	}

	@Override
	public void onDecoderFailed(String error, Date time) {
		uiUpdateHandler.sendMessage(uiUpdateHandler.obtainMessage(
				msgTypeFailed, error));
	}

	@Override
	public void onDecoderConfidenceChanged(DecoderConfidence confidence) {
		uiUpdateHandler.sendMessage(uiUpdateHandler.obtainMessage(
				msgTypeConfidence, confidence));
	}

	@Override
	public void onDecoderStartedListening() {
		uiUpdateHandler.sendMessage(uiUpdateHandler.obtainMessage(
				msgTypeMessage, "Decoder has started listening"));
	}

	@Override
	public void onDecoderIdle() {
		uiUpdateHandler.sendMessage(uiUpdateHandler.obtainMessage(
				msgTypeMessage, "Decoder is idle"));
	}

}

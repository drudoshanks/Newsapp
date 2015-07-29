package com.service;

import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.location.LocationServices;
import com.utils.Session;
import com.utils.Utils;
import com.webservice.CallService;
import com.webservice.CallService.OnServicecall;

public class GetAndSendGCM_KEY extends Service implements ConnectionCallbacks,
		OnConnectionFailedListener {

	GoogleCloudMessaging gcm;
	private String regId = "";
	private String emei_id = "";
	private String latitude = "";
	private String longitude = "";
	Session session;

	/**
	 * Provides the entry point to Google Play services.
	 */
	protected GoogleApiClient mGoogleApiClient;

	/**
	 * Represents a geographical location.
	 */
	protected Location mLastLocation;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		session = new Session(GetAndSendGCM_KEY.this);

		buildGoogleApiClient();
		mGoogleApiClient.connect();

		return super.onStartCommand(intent, flags, startId);

	}

	/**
	 * Builds a GoogleApiClient. Uses the addApi() method to request the
	 * LocationServices API.
	 */
	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
	}

	public void getGCMkey() {

		new CountDownTimer(3000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub

				if (regId.equalsIgnoreCase(""))
					new GetRegistrationId().execute();
				else {
					// send gcm key and emei to my server
					sendKeys();
				}

			}
		}.start();

	}

	private class GetRegistrationId extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging
							.getInstance(GetAndSendGCM_KEY.this);
				}
				regId = gcm.register(Utils.GCM_KEY);

				Log.d("ankit", regId);

			} catch (IOException ex) {
				ex.printStackTrace();
				regId = "";

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			getGCMkey();
		}

	}

	private void sendKeys() {
		String urlStr = Utils._url + "saveDevideId.php?push_gcm_id=" + regId
				+ "&push_emei_id=" + emei_id + "&lat=" + latitude + "&lng="
				+ longitude;

		CallService callService = new CallService(GetAndSendGCM_KEY.this,
				urlStr, Utils.POST, new OnServicecall() {

					@Override
					public void onServicecall(String response) {
						// TODO Auto-generated method stub

						if (response.trim().equalsIgnoreCase("1")) {
							// session.setGcm_sent("1");
							stopSelf();
						} else {
							sendKeys();
						}

					}
				});

		callService.execute();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		sendAllToServer();
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub

		mLastLocation = LocationServices.FusedLocationApi
				.getLastLocation(mGoogleApiClient);
		if (mLastLocation != null) {

			latitude = String.valueOf(mLastLocation.getLatitude());
			longitude = String.valueOf(mLastLocation.getLongitude());

		}

		sendAllToServer();

	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		sendAllToServer();
	}

	private void sendAllToServer() {
		emei_id = Utils.getEMIE_ID(GetAndSendGCM_KEY.this);
		getGCMkey();
	}
}

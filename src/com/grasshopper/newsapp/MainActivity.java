package com.grasshopper.newsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.utils.Session;

public class MainActivity extends Activity {

	Session session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		session = new Session(MainActivity.this);
		// session.setClient_id("1");
		// session.setLang_id("2");

		// to be uncommented when needed
		session.setUser_id("80");

		// http://192.168.5.171/klapp/getClient.php

		// http://192.168.5.171/klapp/getLanguage.php?client_id=1

		// if (!session.getGcm_sent().equalsIgnoreCase("1"))
		// startService(new Intent(MainActivity.this, GetAndSendGCM_KEY.class));

		// try {
		// PackageInfo info = getPackageManager().getPackageInfo(
		// "com.grasshopper.newsapp", PackageManager.GET_SIGNATURES);
		// for (Signature signature : info.signatures) {
		// MessageDigest md = MessageDigest.getInstance("SHA");
		// md.update(signature.toByteArray());
		// Log.d("ankit",
		// Base64.encodeToString(md.digest(), Base64.DEFAULT));
		// }
		// } catch (NameNotFoundException e) {
		//
		// } catch (NoSuchAlgorithmException e) {
		//
		// }

		init();

		// startService(new Intent(MainActivity.this, MusicService.class));

		// getClientID();
		// makenotification();
	}

	private void init() {
		new CountDownTimer(3000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				if (session.getUser_id().equalsIgnoreCase(""))
					startActivity(new Intent(MainActivity.this, SignUp.class));
				else
					startActivity(new Intent(MainActivity.this,
							HomeScreen.class));

				finish();
			}
		}.start();
	}

	public static Intent openApp(Context context, String packageName) {
		PackageManager manager = context.getPackageManager();
		Intent i = manager.getLaunchIntentForPackage(packageName);
		if (i == null) {
			return i;
			// throw new PackageManager.NameNotFoundException();
		}
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		context.startActivity(i);
		return i;
	}

}

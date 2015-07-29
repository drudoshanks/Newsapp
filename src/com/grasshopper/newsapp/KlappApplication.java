package com.grasshopper.newsapp;

import android.app.Application;

import com.splunk.mint.Mint;

public class KlappApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Mint.initAndStartSession(KlappApplication.this, getResources()
				.getString(R.string.mint_api_key));

	}

}

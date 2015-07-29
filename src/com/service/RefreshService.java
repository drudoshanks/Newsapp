package com.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.utils.Session;
import com.utils.Utils;
import com.webservice.CallService;
import com.webservice.CallService.OnServicecall;

public class RefreshService extends Service {

	Session session;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		if (intent != null) {
			session = new Session(RefreshService.this);

			int whichRefresh = 0;

			try {
				whichRefresh = intent.getIntExtra(Utils.WHICH_REFRESH, 0);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			if (whichRefresh != 0) {

				switch (whichRefresh) {
				case 1:

					refreshTopStory();

					break;

				case 2:

					refreshCategory();

					break;

				case 3:

					int cat_id = 0;
					try {
						cat_id = intent.getIntExtra(Utils.CAT_ID, -1);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					if (cat_id!=-1)
						refreshStory(cat_id);

					break;

				default:
					break;
				}
			}
		}

		return super.onStartCommand(intent, flags, startId);
	}

	// to refresh top story
	private void refreshTopStory() {

		if (session != null) {
			String urlStr = Utils._url + "getTopStory.php?client_id="
					+ session.getClient_id() + "&lang_id="
					+ session.getLang_id();

			CallService getListdata = new CallService(RefreshService.this,
					urlStr,Utils.GET, new OnServicecall() {

						@Override
						public void onServicecall(String response) {
							// TODO Auto-generated method stub

							session.setTopNews(response);

						}
					});
			getListdata.execute();
		}

	}

	// to refresh category
	private void refreshCategory() {

	}

	// to refresh story
	private void refreshStory(int cat_id) {

	}
}

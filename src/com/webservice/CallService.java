package com.webservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;

public class CallService extends AsyncTask<Void, String, String> {

	OnServicecall OnServicecall;
	Context context;
	String urlStr;

	String mehtod = "";

	// ProgressDialog dialog;

	public interface OnServicecall {
		public void onServicecall(String response);

	}

	public CallService(Context context, String urlStr, String mehtod,
			OnServicecall OnServicecall) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.urlStr = urlStr;
		this.OnServicecall = OnServicecall;
		this.mehtod = mehtod;

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		// dialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_DARK);
		// // set indeterminate style
		// dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// dialog.setCancelable(false);
		// // set title and message
		// dialog.setMessage("Please wait");
		// // and show it
		// dialog.show();
	}

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub

		return getData(mehtod);
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		// try {
		//
		// int i = result.indexOf("<!--");
		//
		// result = result.substring(0, i);
		// } catch (Exception e) {
		// // TODO: handle exception
		//
		// e.printStackTrace();
		// }

		// dialog.dismiss();

		OnServicecall.onServicecall(result);
	}

	private String getData(String mehtod) {

		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod(mehtod);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));

			String data = null;
			String webPage = "";
			while ((data = reader.readLine()) != null) {
				webPage += data + "\n";
			}
			return webPage;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

}

package com.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

import com.model.Gallarymodel;
import com.model.NewsDetailModel;
import com.model.OpDetailModel;
import com.model.VideoDetailModel;

public class Utils {

	public static String _url = "http://192.168.5.171/klapp/";

	// public static String _url = "http://klappapp.bugs3.com/klapp/";

	public static String GOOGLE_API_KEY = "AIzaSyBXspoM4g3uJIWzl6GcXEux-jPwYObslJ0";

	public static String url_send_intent = "send_url";
	public static String title_send_intent = "send_title";
	public static String model_send_intent = "send_model";
	public static String position_send_intent = "send_position";

	// shared variables
	public static String client_id_shared = "client_id";
	public static String user_id_shared = "user_id";
	public static String lang_id_shared = "lang_id";
	public static String categories_shared = "categories";
	public static String topNews_shared = "topNews";
	// public static String gcm_sent = "gcm_sent"; // 1 for sent and else
	// anything for not sent
	public static String story_shared = "story";

	public static NewsDetailModel model = new NewsDetailModel();

	public static Gallarymodel gallary_model = new Gallarymodel();

	public static VideoDetailModel video_model = new VideoDetailModel();

	public static OpDetailModel op_model = new OpDetailModel();

	public static int TOP_ADDED_NEWS = 0;

	// shared methods
	public static void setShared(Context context, String name, String value) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putString(name, value);
		editor.commit();
	}

	public static String getShared(Context context, String name) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getString(name, "");

	}

	// user property type json variables
	public static String user_property_id = "user_property_id";
	// public static String user_property_lang_id = "lang_id";
	// public static String user_property_name_lang = "name_lang";
	public static String user_property_client_id = "client_id";
	public static String user_property_name_eng = "name_eng";

	// user property transaction
	public static String user_property_transaction_user_id = "user_id";
	public static String user_property_transaction_user_property_id = "user_property_id";
	public static String user_property_transaction_client_id = "client_id";
	public static String user_property_transaction_value = "value";

	// gcm key
	public static String GCM_KEY = "368279697767";

	// which refresh variable
	public static String WHICH_REFRESH = "WHICH_REFRESH";

	// category id variable
	public static String CAT_ID = "cat_id";

	// get or post methods
	public static String GET = "GET";
	public static String POST = "POST";

	@SuppressWarnings("deprecation")
	public static int getImageHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display d = wm.getDefaultDisplay();

		return (int) ((d.getWidth() * ASPECT_RATIO_HEIGHT) / ASPECT_RATIO_WIDTH);
	}

	public static int ASPECT_RATIO_HEIGHT = 9;
	public static int ASPECT_RATIO_WIDTH = 16;

	public static void shareThing(String sharetext, Context context) {

		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, sharetext);
		sendIntent.setType("text/plain");
		context.startActivity(sendIntent);

	}

	public static String getEMIE_ID(Context context) {

		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

//	public static void setBlackAndWhite(ImageView iv) {
//
//		float brightness = 50;
//
//		float[] colorMatrix = { 0.33f, 0.33f, 0.33f, 0, brightness, // red
//				0.33f, 0.33f, 0.33f, 0, brightness, // green
//				0.33f, 0.33f, 0.33f, 0, brightness, // blue
//				0, 0, 0, 1, 0 // alpha
//		};
//
//		ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
//		iv.setColorFilter(colorFilter);
//
//	}
}

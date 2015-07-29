package com.model;

public class ServerVideoModel {

	// private String youtubeVideoCode = "";
	// private String youtube_text = "";
	//
	// public String getYoutubeVideoCode() {
	// return youtubeVideoCode;
	// }
	//
	// public void setYoutubeVideoCode(String youtubeVideoCode) {
	// this.youtubeVideoCode = youtubeVideoCode;
	// }
	//
	// public String getYoutube_text() {
	// return youtube_text;
	// }
	//
	// public void setYoutube_text(String youtube_text) {
	// this.youtube_text = youtube_text;
	// }

	private int Yt_list_id = 0;
	private String Yt_list_image_url = "";
	private String Yt_list_title = "";
	private String Yt_list_title_sub = "";
	private String Yt_list_publish_date = "";
	private String Yt_list_passingurl = "";
	private String Yt_code = "";

	public String getYt_code() {
		return Yt_code;
	}

	public void setYt_code(String yt_code) {
		Yt_code = yt_code;
	}

	public int getYt_list_id() {
		return Yt_list_id;
	}

	public void setYt_list_id(int yt_list_id) {
		Yt_list_id = yt_list_id;
	}

	public String getYt_list_image_url() {
		return Yt_list_image_url;
	}

	public void setYt_list_image_url(String yt_list_image_url) {
		Yt_list_image_url = yt_list_image_url;
	}

	public String getYt_list_title() {
		return Yt_list_title;
	}

	public void setYt_list_title(String yt_list_title) {
		Yt_list_title = yt_list_title;
	}

	public String getYt_list_title_sub() {
		return Yt_list_title_sub;
	}

	public void setYt_list_title_sub(String yt_list_title_sub) {
		Yt_list_title_sub = yt_list_title_sub;
	}

	public String getYt_list_publish_date() {
		return Yt_list_publish_date;
	}

	public void setYt_list_publish_date(String yt_list_publish_date) {
		Yt_list_publish_date = yt_list_publish_date;
	}

	public String getYt_list_passingurl() {
		return Yt_list_passingurl;
	}

	public void setYt_list_passingurl(String yt_list_passingurl) {
		Yt_list_passingurl = yt_list_passingurl;
	}

}

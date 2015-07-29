package com.model;

import android.os.Parcel;
import android.os.Parcelable;

public class News_List_Model implements Parcelable {

	private int news_list_id = 0;
	private String news_list_image_url = "";
	private String news_list_title = "";
	private String news_list_title_sub = "";
	private String news_list_publish_date = "";
	private String news_list_passingurl = "";

	public String getNews_list_passingurl() {
		return news_list_passingurl;
	}

	public void setNews_list_passingurl(String news_list_passingurl) {
		this.news_list_passingurl = news_list_passingurl;
	}

	public static News_List_Model newInstance() {
		return new News_List_Model();
	}

	public String getNews_list_title_sub() {
		return news_list_title_sub;
	}

	public void setNews_list_title_sub(String news_list_title_sub) {
		this.news_list_title_sub = news_list_title_sub;
	}

	public int getNews_list_id() {
		return news_list_id;
	}

	public void setNews_list_id(int news_list_id) {
		this.news_list_id = news_list_id;
	}

	public String getNews_list_image_url() {
		return news_list_image_url;
	}

	public void setNews_list_image_url(String news_list_image_url) {
		this.news_list_image_url = news_list_image_url;
	}

	public String getNews_list_title() {
		return news_list_title;
	}

	public void setNews_list_title(String news_list_title) {
		this.news_list_title = news_list_title;
	}

	public String getNews_list_publish_date() {
		return news_list_publish_date;
	}

	public void setNews_list_publish_date(String news_list_publish_date) {
		this.news_list_publish_date = news_list_publish_date;
	}

	protected News_List_Model() {

	}

	protected News_List_Model(Parcel in) {
		news_list_id = in.readInt();
		news_list_image_url = in.readString();
		news_list_title = in.readString();
		news_list_title_sub = in.readString();
		news_list_publish_date = in.readString();
		news_list_passingurl = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(news_list_id);
		dest.writeString(news_list_image_url);
		dest.writeString(news_list_title);
		dest.writeString(news_list_title_sub);
		dest.writeString(news_list_publish_date);
		dest.writeString(news_list_passingurl);
	}

	public static final Parcelable.Creator<News_List_Model> CREATOR = new Parcelable.Creator<News_List_Model>() {
		@Override
		public News_List_Model createFromParcel(Parcel in) {
			return new News_List_Model(in);
		}

		@Override
		public News_List_Model[] newArray(int size) {
			return new News_List_Model[size];
		}
	};
}
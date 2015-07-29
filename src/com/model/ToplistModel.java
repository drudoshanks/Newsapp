package com.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ToplistModel implements Parcelable {

	private String passing_url = "";
	private String title = "";
	private String title_sub = ""; // for sub title added on 11 may 2015
	private String image_url = "";
	private boolean isRead = false;

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public ToplistModel() {

	}

	public String getPassing_url() {
		return passing_url;
	}

	public void setPassing_url(String passing_url) {
		this.passing_url = passing_url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle_sub() {
		return title_sub;
	}

	public void setTitle_sub(String title_sub) {
		this.title_sub = title_sub;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	protected ToplistModel(Parcel in) {
		passing_url = in.readString();
		title = in.readString();
		title_sub = in.readString();
		image_url = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(passing_url);
		dest.writeString(title);
		dest.writeString(title_sub);
		dest.writeString(image_url);
	}

	public static final Parcelable.Creator<ToplistModel> CREATOR = new Parcelable.Creator<ToplistModel>() {
		@Override
		public ToplistModel createFromParcel(Parcel in) {
			return new ToplistModel(in);
		}

		@Override
		public ToplistModel[] newArray(int size) {
			return new ToplistModel[size];
		}
	};
}
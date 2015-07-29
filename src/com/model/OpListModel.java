package com.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OpListModel implements Parcelable {

	private int Op_list_id = 0;
	private String Op_list_image_url = "";
	private String Op_list_title = "";
	private String Op_list_title_sub = "";
	private String Op_list_publish_date = "";
	private String Op_list_passingurl = "";

	public OpListModel() {

	}

	public int getOp_list_id() {
		return Op_list_id;
	}

	public void setOp_list_id(int op_list_id) {
		Op_list_id = op_list_id;
	}

	public String getOp_list_image_url() {
		return Op_list_image_url;
	}

	public void setOp_list_image_url(String op_list_image_url) {
		Op_list_image_url = op_list_image_url;
	}

	public String getOp_list_title() {
		return Op_list_title;
	}

	public void setOp_list_title(String op_list_title) {
		Op_list_title = op_list_title;
	}

	public String getOp_list_title_sub() {
		return Op_list_title_sub;
	}

	public void setOp_list_title_sub(String op_list_title_sub) {
		Op_list_title_sub = op_list_title_sub;
	}

	public String getOp_list_publish_date() {
		return Op_list_publish_date;
	}

	public void setOp_list_publish_date(String op_list_publish_date) {
		Op_list_publish_date = op_list_publish_date;
	}

	public String getOp_list_passingurl() {
		return Op_list_passingurl;
	}

	public void setOp_list_passingurl(String op_list_passingurl) {
		Op_list_passingurl = op_list_passingurl;
	}

	protected OpListModel(Parcel in) {
		Op_list_id = in.readInt();
		Op_list_image_url = in.readString();
		Op_list_title = in.readString();
		Op_list_title_sub = in.readString();
		Op_list_publish_date = in.readString();
		Op_list_passingurl = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(Op_list_id);
		dest.writeString(Op_list_image_url);
		dest.writeString(Op_list_title);
		dest.writeString(Op_list_title_sub);
		dest.writeString(Op_list_publish_date);
		dest.writeString(Op_list_passingurl);
	}

	public static final Parcelable.Creator<OpListModel> CREATOR = new Parcelable.Creator<OpListModel>() {
		@Override
		public OpListModel createFromParcel(Parcel in) {
			return new OpListModel(in);
		}

		@Override
		public OpListModel[] newArray(int size) {
			return new OpListModel[size];
		}
	};
}
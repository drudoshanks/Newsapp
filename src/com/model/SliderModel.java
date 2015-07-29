package com.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SliderModel implements Parcelable {

	private int cat_id = 0;
	private int parent_cat_id = 0;
	private int layout_no = 0;
	private String image_url = "";
	private String text = "";
	private String cat_type_tag = "";

	public String getCat_type_tag() {
		return cat_type_tag;
	}

	public void setCat_type_tag(String cat_type_tag) {
		this.cat_type_tag = cat_type_tag;
	}

	public static SliderModel newInstance() {

		return new SliderModel();
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}

	public int getParent_cat_id() {
		return parent_cat_id;
	}

	public void setParent_cat_id(int parent_cat_id) {
		this.parent_cat_id = parent_cat_id;
	}

	public int getLayout_no() {
		return layout_no;
	}

	public void setLayout_no(int layout_no) {
		this.layout_no = layout_no;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	protected SliderModel() {

	}

	protected SliderModel(Parcel in) {
		cat_id = in.readInt();
		parent_cat_id = in.readInt();
		layout_no = in.readInt();
		image_url = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(cat_id);
		dest.writeInt(parent_cat_id);
		dest.writeInt(layout_no);
		dest.writeString(image_url);
	}

	public static final Parcelable.Creator<SliderModel> CREATOR = new Parcelable.Creator<SliderModel>() {
		@Override
		public SliderModel createFromParcel(Parcel in) {
			return new SliderModel(in);
		}

		@Override
		public SliderModel[] newArray(int size) {
			return new SliderModel[size];
		}
	};
}
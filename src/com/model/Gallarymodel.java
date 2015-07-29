package com.model;

import java.util.ArrayList;

public class Gallarymodel {

	private String pub_date = ""; // publish date
	private String asset_id = ""; // image url
	private String propertyValue = ""; // heding
	private String propertySubValue = ""; // sub heading
	private String propertySTORY = ""; // complete story

	private ArrayList<GallaryImageModel> story_images = new ArrayList<GallaryImageModel>();


	public String getPub_date() {
		return pub_date;
	}

	public void setPub_date(String pub_date) {
		this.pub_date = pub_date;
	}

	public String getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(String asset_id) {
		this.asset_id = asset_id;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getPropertySubValue() {
		return propertySubValue;
	}

	public void setPropertySubValue(String propertySubValue) {
		this.propertySubValue = propertySubValue;
	}

	public String getPropertySTORY() {
		return propertySTORY;
	}

	public void setPropertySTORY(String propertySTORY) {
		this.propertySTORY = propertySTORY;
	}

	public ArrayList<GallaryImageModel> getStory_images() {
		return story_images;
	}

	public void setStory_images(ArrayList<GallaryImageModel> story_images) {
		this.story_images = story_images;
	}

}

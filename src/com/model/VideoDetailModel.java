package com.model;

public class VideoDetailModel {

	private String pub_date = ""; // publish date
	private String asset_id = ""; // image url
	private String propertyValue = ""; // heding
	private String propertySubValue = ""; // sub heading
	private String propertySTORY = ""; // complete story

	private String StoryUrl = ""; // story url

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

	public String getStoryUrl() {
		return StoryUrl;
	}

	public void setStoryUrl(String storyUrl) {
		StoryUrl = storyUrl;
	}

}

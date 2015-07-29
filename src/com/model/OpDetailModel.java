package com.model;

import java.util.ArrayList;

public class OpDetailModel {

	String pub_date = "";
	String image = "";
	String question = "";
	ArrayList<OpDetailSubModel> options = new ArrayList<OpDetailSubModel>();
	String createdate = "";
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getPub_date() {
		return pub_date;
	}

	public void setPub_date(String pub_date) {
		this.pub_date = pub_date;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ArrayList<OpDetailSubModel> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<OpDetailSubModel> options) {
		this.options = options;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

}

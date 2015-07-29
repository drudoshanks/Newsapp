package com.model;

public class UserPropertyModel {

	// {"user_property_id":"1","lang_id":"13","name_lang":"email","client_id":"2","name_eng":"email"}

	private String user_property_id = "";
	private String lang_id = "";
	private String name_lang = "";
	private String client_id = "";
	private String name_eng = "";

	public String getUser_property_id() {
		return user_property_id;
	}

	public void setUser_property_id(String user_property_id) {
		this.user_property_id = user_property_id;
	}

	public String getLang_id() {
		return lang_id;
	}

	public void setLang_id(String lang_id) {
		this.lang_id = lang_id;
	}

	public String getName_lang() {
		return name_lang;
	}

	public void setName_lang(String name_lang) {
		this.name_lang = name_lang;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getName_eng() {
		return name_eng;
	}

	public void setName_eng(String name_eng) {
		this.name_eng = name_eng;
	}

}

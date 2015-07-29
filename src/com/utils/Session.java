package com.utils;

import android.content.Context;

public class Session {

	Context context;

	public Session(Context context) {
		this.context = context;
	}
	
	//public String gcm_sent = "gcm_sent"; // 1 for sent and else anything for not sent

//	public String getGcm_sent() {
//		return Utils.getShared(context, Utils.gcm_sent);
//	}
//
//	public void setGcm_sent(String gcm_sent) {
//		Utils.setShared(context, Utils.gcm_sent, gcm_sent);
//	}

	public String getCategory() {
		return Utils.getShared(context, Utils.categories_shared);
	}

	public void setCategory(String category) {
		Utils.setShared(context, Utils.categories_shared, category);
	}

	public String getTopNews() {
		return Utils.getShared(context, Utils.topNews_shared);
	}

	public void setTopNews(String topNews) {
		Utils.setShared(context, Utils.topNews_shared, topNews);
	}

	public String getStory() {
		return Utils.getShared(context, Utils.story_shared);
	}

	public void setStory(String story) {
		Utils.setShared(context, Utils.story_shared, story);
	}

	public String getUser_id() {
		return Utils.getShared(context, Utils.user_id_shared);
	}

	public void setUser_id(String user_id) {
		Utils.setShared(context, Utils.user_id_shared, user_id);
	}

	public String getLang_id() {
		return Utils.getShared(context, Utils.lang_id_shared);
	}

	public void setLang_id(String lang_id) {
		Utils.setShared(context, Utils.lang_id_shared, lang_id);
	}

	public String getClient_id() {
		return Utils.getShared(context, Utils.client_id_shared);
	}

	public void setClient_id(String client_id) {
		Utils.setShared(context, Utils.client_id_shared, client_id);
	}

}

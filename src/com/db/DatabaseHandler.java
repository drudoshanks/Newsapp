package com.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.model.News_List_Model;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "KlappDb";
	private static final String TABLE_STORY = "news_story";

	private static final String cat_id = "cat_id";
	private static final String news_list_id = "news_list_id";
	private static final String news_list_image_url = "news_list_image_url";
	private static final String news_list_title = "news_list_title";
	private static final String news_list_title_sub = "news_list_title_sub";
	private static final String news_list_publish_date = "news_list_publish_date";
	private static final String news_list_passingurl = "news_list_passingurl";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// 3rd argument to be passed is CursorFactory instance
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_STORY + "("
				+ cat_id + " INTEGER," + news_list_id + " INTEGER,"
				+ news_list_image_url + " TEXT," + news_list_title + " TEXT,"
				+ news_list_title_sub + " TEXT," + news_list_publish_date
				+ " TEXT," + news_list_passingurl + " TEXT" + ")";

		db.execSQL(CREATE_CONTACTS_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORY);

		// Create tables again
		onCreate(db);

	}

	// set get delete

	// set data to the table
	public void addStories(ArrayList<News_List_Model> main_model, int id) {
		SQLiteDatabase db = this.getWritableDatabase();

		if (main_model != null && main_model.size() > 0)
			for (int i = 0; i < main_model.size(); i++) {

				News_List_Model model = main_model.get(i);
				if (model != null) {
					ContentValues values = new ContentValues();
					values.put(cat_id, id);
					values.put(news_list_id, model.getNews_list_id());
					values.put(news_list_image_url,
							model.getNews_list_image_url());
					values.put(news_list_title, model.getNews_list_title());
					values.put(news_list_title_sub,
							model.getNews_list_title_sub());
					values.put(news_list_publish_date,
							model.getNews_list_publish_date());
					values.put(news_list_passingurl,
							model.getNews_list_passingurl());

					// Inserting Row
					db.insert(TABLE_STORY, null, values);
				}

			}

		// 2nd argument is String containing nullColumnHack
		db.close(); // Closing database connection
	}

	// code to get data
	public ArrayList<News_List_Model> getAllStories(int id) {
		ArrayList<News_List_Model> main_model = new ArrayList<News_List_Model>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_STORY + " WHERE "
				+ cat_id + " =" + id;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.getCount() > 0) {
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					News_List_Model model = News_List_Model.newInstance();

					model.setNews_list_id(cursor.getInt(cursor
							.getColumnIndex(news_list_id)));
					model.setNews_list_image_url(cursor.getString(cursor
							.getColumnIndex(news_list_image_url)));
					model.setNews_list_title(cursor.getString(cursor
							.getColumnIndex(news_list_title)));
					model.setNews_list_title_sub(cursor.getString(cursor
							.getColumnIndex(news_list_title_sub)));
					model.setNews_list_publish_date(cursor.getString(cursor
							.getColumnIndex(news_list_publish_date)));
					model.setNews_list_passingurl(cursor.getString(cursor
							.getColumnIndex(news_list_passingurl)));

					// Adding contact to list
					main_model.add(model);
				} while (cursor.moveToNext());
			}

			// return contact list
			return main_model;
		}

		else
			return null;

	}
}

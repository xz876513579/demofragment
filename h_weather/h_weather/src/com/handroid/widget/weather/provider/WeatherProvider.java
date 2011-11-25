package com.handroid.widget.weather.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.handroid.widget.weather.object.FeatureConditions;

public class WeatherProvider extends ContentProvider {
	
	private SQLiteOpenHelper mOpenHelper;

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public boolean onCreate() {
		mOpenHelper = new WeatherDatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}
	
	/**
	 * Convenience method for inserting a row into the weather database.
	 * @param conditionValue the value that we want to insert into <b> condition_table </b>
	 * @return the row ID of the newly inserted row, or -1 if an error occurred 
	 */
	private long insertConditions(ContentValues conditionValue) {
		if (conditionValue == null)
			return -1;
		if (mOpenHelper == null)
			return -1;
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long rowId = db.insert(FeatureConditions.CONDITION_TABLE_NAME, null, conditionValue);
		return rowId;
	}

}

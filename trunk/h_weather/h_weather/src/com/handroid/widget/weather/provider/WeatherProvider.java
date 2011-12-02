package com.handroid.widget.weather.provider;

import java.util.TimeZone;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

import com.handroid.widget.weather.object.FeatureConditions;

public class WeatherProvider extends ContentProvider {
	
	private SQLiteOpenHelper mOpenHelper;

	private static final int 		CONSTANTS 		= 1;
	private static final int 		CONSTANT_ID 	= 2;
	private static final UriMatcher MATCHER;
	private static final String 	TABLE			= "conditions";
	private static final String 	AUTHORITY		= "com.handroid.widget.weather.provider.WeatherProvider";

	static {
		MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		MATCHER.addURI(AUTHORITY, "conditions", CONSTANTS);
		MATCHER.addURI(AUTHORITY, "conditions/#", CONSTANT_ID);
	}
	
	public static final class Constants implements BaseColumns {
		public static final Uri CONTENT_URI = Uri
				.parse("content://" + AUTHORITY + "/constants");
		public static final String DEFAULT_SORT_ORDER 	= com.handroid.widget.weather.object.FeatureSearchResultObj.NAME;
		public static final String ZMW 					= com.handroid.widget.weather.object.FeatureSearchResultObj.ZMW;
		public static final String C 					= com.handroid.widget.weather.object.FeatureSearchResultObj.C;
		public static final String TZ 					= com.handroid.widget.weather.object.FeatureSearchResultObj.TZ;
		public static final String TZS 					= com.handroid.widget.weather.object.FeatureSearchResultObj.TZS;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		if (isCollectionUri(uri)) {
			return ("vnd.handroid.cursor.dir/constant");
		}
		return ("vnd.handroid.cursor.item/constant");
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowId = -1;
		Uri newUri = null;
		
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

	private boolean isCollectionUri(Uri url) {
		return (MATCHER.match(url) == CONSTANTS);
	}
}

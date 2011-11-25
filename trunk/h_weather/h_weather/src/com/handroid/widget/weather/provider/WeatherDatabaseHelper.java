package com.handroid.widget.weather.provider;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.handroid.widget.weather.object.FeatureConditions;

public class WeatherDatabaseHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION 	= 1;
	static final String DATABASE_NAME 			= "weatherdb.db";

	private static final String CREATE_TABLE_CONDITIONS = "create table "
		+ FeatureConditions.CONDITION_TABLE_NAME + " (" + ");";

	public WeatherDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/**
     * Execute all of the SQL statements in the String[] array
     * @param db The database on which to execute the statements
     * @param sql An array of SQL statements to execute
     */
	public void execMultipleSQL(SQLiteDatabase db, String[] sql){		
		for( String s : sql )
            if (s.trim().length()>0)
            	db.execSQL(s);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		if (db == null)
			return;
		db.beginTransaction();
        try {
        	String[] querryStr = new String[]{CREATE_TABLE_CONDITIONS};
            // Create tables and test data
            execMultipleSQL(db, querryStr);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("Error creating tables and debug data", e.toString());
            // throw e;
        } finally {
            db.endTransaction();
        }
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (db == null)
			return;
		if (newVersion <= oldVersion)
			return;
		// drop old table here
		db.execSQL("DROP IF TABLE EXISTS " + FeatureConditions.CONDITION_TABLE_NAME);
		// create new table again
		onCreate(db);
	}

}

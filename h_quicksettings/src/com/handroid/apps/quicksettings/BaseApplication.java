package com.handroid.apps.quicksettings;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class BaseApplication extends Application{

	private static Toast mAppToast;
	
	private static Context mAppContext;

	@Override
	public void onCreate() {
		super.onCreate();
		// Log.i(TAG, "App, on create!");
		mAppContext = getApplicationContext();
	}
	
	public static void makeToastMsg(String msg) {
		if (mAppToast == null) {
			mAppToast = Toast.makeText(mAppContext, msg, /*LENGTH_SHORT*/0);
			mAppToast.show();
		} else {
			mAppToast.setText(msg);
			mAppToast.cancel();
			mAppToast.show();
		}
	}
}

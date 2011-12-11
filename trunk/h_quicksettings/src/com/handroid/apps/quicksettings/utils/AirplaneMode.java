package com.handroid.apps.quicksettings.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class AirplaneMode {

	private Context mContext;
	
	public AirplaneMode (Context context) {
		this.mContext = context;
	}
	
	public boolean getState() {
		if (this.mContext == null)
			return false;
		return (Settings.System.getInt(this.mContext.getContentResolver(),
				"airplane_mode_on", 0) == 1);
	}

//	public void toggleState(Context paramContext) {
//		boolean bool1 = getState(paramContext);
//		ContentResolver localContentResolver = paramContext
//				.getContentResolver();
//		int i;
//		Intent localIntent1;
//		String str;
//		if (bool1) {
//			i = 0;
//			boolean bool2 = Settings.System.putInt(localContentResolver,
//					"airplane_mode_on", i);
//			localIntent1 = new Intent("android.intent.action.AIRPLANE_MODE");
//			str = "state";
//			if (!bool1)
//				break label73;
//		}
//		label73: for (boolean bool3 = false;; bool3 = true) {
//			Intent localIntent2 = localIntent1.putExtra(str, bool3);
//			paramContext.sendBroadcast(localIntent1);
//			return;
//			i = 1;
//			break;
//		}
//	}

	public void toggleState(boolean paramBoolean) {
		if (this.mContext == null)
			return;
		
		if (getState() != paramBoolean)
			return;
		ContentResolver localContentResolver = this.mContext
				.getContentResolver();
		String str = "airplane_mode_on";
		
		boolean bool = Settings.System.putInt(localContentResolver, str, 1);
		Intent localIntent1 = new Intent(
				"android.intent.action.AIRPLANE_MODE");
		Intent localIntent2 = localIntent1.putExtra("state", paramBoolean);
		this.mContext.sendBroadcast(localIntent2);
	}
}

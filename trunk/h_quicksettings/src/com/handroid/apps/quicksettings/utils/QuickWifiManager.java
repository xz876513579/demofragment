package com.handroid.apps.quicksettings.utils;

import android.content.Context;
import android.net.wifi.WifiManager;

public class QuickWifiManager {

	private Context mContext;
	private WifiManager mWifiManager;
	
	public QuickWifiManager(Context context) {
		this.mContext = context;
		if (this.mContext != null)
			mWifiManager = (WifiManager) this.mContext.getSystemService(Context.WIFI_SERVICE);
	}
	
	public boolean isWifiEnabled() {
		if (mWifiManager != null) {
			if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
				return true;
		}
		return false;
	}

	public int getWifiState() {
		return mWifiManager.getWifiState();
	}
	
	public void enableWifi() {
		if (mWifiManager != null) {
			mWifiManager.setWifiEnabled(true);
		}
	}
	
	public void disableWifi() {
		if (mWifiManager != null) {
			mWifiManager.setWifiEnabled(false);
		}
	}

}

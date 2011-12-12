package com.handroid.apps.quicksettings.utils;

import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;
import android.util.Log;

public class DataConManager {

	private ConnectivityManager mConManager = null;
	@SuppressWarnings("unused")
	private TelephonyManager mTelManager = null;
	
	private final String TAG = "DataConManager";
	
	private Context mContext;

	public DataConManager(Context paramContext) {
		try {
			this.mContext = paramContext;
			TelephonyManager localTelephonyManager = (TelephonyManager) paramContext
					.getSystemService("phone");
			this.mTelManager = localTelephonyManager;
			ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext
					.getSystemService("connectivity");
			this.mConManager = localConnectivityManager;
			return;
		} catch (Exception localException) {
			this.mTelManager = null;
			this.mConManager = null;
		}
	}

	public boolean isDataEnableActived() {
		if (this.mConManager == null)
			return false;
		try {
			return (this.mConManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED);
		} catch (Exception localException) {
			Log.e(TAG, ">>> is enable check ERROR: " + localException.toString());
		}
		return false;
	}
	
	public State getDataState() {
		return mConManager.getNetworkInfo(0).getState();
	}

	/**
	 * Reflection method to enable or disable "Data enable" function in
	 * "Mobile networks" of Android's Settings </br>
	 * Sets the persisted value for enabling/disabling Mobile data.
	 * @param paramContext The application Context.
	 * @param paramBoolean enabled Whether the mobile data connection should be used or not.
	 */
	public boolean switchMobileEnableState(boolean paramBoolean)
			throws SecurityException {
		if (this.mContext == null)
			return false;
		
		ConnectivityManager mConnectivityManager = (ConnectivityManager) this.mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE /*"connectivity"*/);
		try {
			// using reflection to call the hidding method!
			Class<? extends ConnectivityManager> mainClassOfConnectivityManager = mConnectivityManager.getClass();
			Method hiddenConnectivityManagerMethod = mainClassOfConnectivityManager.getDeclaredMethod(
					"setMobileDataEnabled", Boolean.TYPE);
			hiddenConnectivityManagerMethod.setAccessible(true);
			hiddenConnectivityManagerMethod.invoke(mConnectivityManager,
					Boolean.valueOf(paramBoolean));
			return true;
		} catch (Exception localException) {
			Log.e(TAG, ">>> switch State ERROR: " + localException.toString());
		}
		return false;
	}

	/**
	 * Gets the value of the setting for enabling Mobile data.
	 * @param paramContext The application Context.
	 * @return Whether mobile data is enabled.
	 * @throws SecurityException
	 */
	public boolean getMobileDataEnabled()
			throws SecurityException {
		if (this.mContext == null)
			return false;

		ConnectivityManager mConnectivityManager = (ConnectivityManager) this.mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE /* "connectivity" */);
		try {
			// using reflection to call the hidding method!
			Class<? extends ConnectivityManager> mainClassOfConnectivityManager = mConnectivityManager
					.getClass();
			Method hiddenConnectivityManagerMethod = mainClassOfConnectivityManager
					.getDeclaredMethod("getMobileDataEnabled");
			hiddenConnectivityManagerMethod.setAccessible(true);
			boolean result = ((Boolean) hiddenConnectivityManagerMethod
					.invoke(mConnectivityManager)).booleanValue();
			return result;
		} catch (Exception localException) {
			Log.e(TAG, ">>> get mobile State ERROR: " + localException.toString());
		}
		return false;
	}
	
}

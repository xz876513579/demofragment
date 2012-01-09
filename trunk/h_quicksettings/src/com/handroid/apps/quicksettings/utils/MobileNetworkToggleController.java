package com.handroid.apps.quicksettings.utils;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.net.ConnectivityManager;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import com.handroid.apps.quicksettings.R;

public class MobileNetworkToggleController extends AbtractToggleController{
	
	private Button btnToggleMobileNetwork;
	private final int MSG_DISABLE_BTN_MOBILE_NETWORK 	= 103;
	private final int MSG_UPDATE_BTN_MOBILE_NETWORK 	= 104;
	
	private final int MSG_DATA_DISCONNECTED 			= 105;
	private final int MSG_DATA_CONNECTING				= 106;
	private final int MSG_DATA_CONNECTED				= 107;
	
	private TelephonyManager telMan;

	public MobileNetworkToggleController(Context context) {
		super(context);
		telMan = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		telMan.listen(phoneStateListener,PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
	}
	
	private PhoneStateListener phoneStateListener = new PhoneStateListener(){
		public void onDataConnectionStateChanged(int state) {
			Log.i("mapp", ">>> phone state change:" + state);
			switch (state) {
			case TelephonyManager.DATA_DISCONNECTED:
				// mHandler.sendMessage(mHandler.obtainMessage(MSG_UPDATE_BTN_MOBILE_NETWORK, false));
				mHandler.sendEmptyMessage(MSG_DATA_DISCONNECTED);
				break;
			case TelephonyManager.DATA_CONNECTING:
				mHandler.sendEmptyMessage(MSG_DATA_CONNECTING);
				break;
			case TelephonyManager.DATA_CONNECTED:
				// mHandler.sendMessage(mHandler.obtainMessage(MSG_UPDATE_BTN_MOBILE_NETWORK, true));
				mHandler.sendEmptyMessage(MSG_DATA_CONNECTED);
				break;
			}
		};
	};
	
	@Override
	public void doOnActivityResume() {
		super.doOnActivityResume();
		updateMobileNetworkButtonUI();
	}
	
	@Override
	public void doOnActivityPause() {
		super.doOnActivityPause();
	}

	@Override
	public void initToggleButton(Button[] btnObjs) {
		if (btnObjs == null) {
			return;
		}
		if (btnObjs[0].getId() == R.id.btn_togle_mobile_data) 
			btnToggleMobileNetwork = btnObjs[0];
	}
	
	@Override
	protected void updateUI(Message msg) {
		// super.updateUI(msg);
		switch (msg.what) {
		/*case MSG_DISABLE_BTN_MOBILE_NETWORK:
			btnToggleMobileNetwork.setEnabled(false);
			break;
			
		case MSG_UPDATE_BTN_MOBILE_NETWORK:
			updateAirplaneButtonState(true);
			
			if (((Boolean) msg.obj).booleanValue())
				updateAirplaneButtonState(true);
			else
				updateAirplaneButtonState(false);
			
			updateMobileNetworkButtonUI();
			break;*/
			
		case MSG_DATA_DISCONNECTED:
			btnToggleMobileNetwork.setEnabled(true);
			if (getMobileDataEnabled())
				updateAirplaneButtonState(true);
			else 
				updateAirplaneButtonState(false);
			break;
			
		case MSG_DATA_CONNECTED:
			btnToggleMobileNetwork.setEnabled(true);
			if (getMobileDataEnabled())
				updateAirplaneButtonState(true);
			break;
			
		case MSG_DATA_CONNECTING:
			updateAirplaneButtonState(false);
			btnToggleMobileNetwork.setEnabled(false);
			break;
		}
	}
	
	/*@Override
	protected void onReceivePhoneState(Context context, Intent intent) {
		String str = intent.getAction();
		if (TextUtils.isEmpty(str))
			return;
		if (str.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
			Message msg = null;
			msg = mHandler.obtainMessage(MSG_UPDATE_BTN_MOBILE_NETWORK, intent.getBooleanExtra("state", false));
			mHandler.sendMessage(msg);
		}
	}*/
	
	private void updateMobileNetworkButtonUI() {
		btnToggleMobileNetwork.setEnabled(true);
    	updateAirplaneButtonState(getMobileDataEnabled());
    }
	
	private void updateAirplaneButtonState(boolean enable){
		if (enable) {
			btnToggleMobileNetwork.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_network_on, 0, 0);
		} else {
			btnToggleMobileNetwork.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_network_off, 0, 0);
		}
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
		Log.d("mapp", ">>> switch state to: " + paramBoolean);
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
			if (!paramBoolean)
				mHandler.sendEmptyMessageDelayed(MSG_DATA_DISCONNECTED, 1000);
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
		boolean result = false;
		ConnectivityManager mConnectivityManager = (ConnectivityManager) this.mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE /* "connectivity" */);
		try {
			// using reflection to call the hidding method!
			Class<? extends ConnectivityManager> mainClassOfConnectivityManager = mConnectivityManager
					.getClass();
			Method hiddenConnectivityManagerMethod = mainClassOfConnectivityManager
					.getDeclaredMethod("getMobileDataEnabled");
			hiddenConnectivityManagerMethod.setAccessible(true);
			result = ((Boolean) hiddenConnectivityManagerMethod
					.invoke(mConnectivityManager)).booleanValue();
		} catch (Exception localException) {
			Log.e(TAG, ">>> get mobile State ERROR: " + localException.toString());
		}
		Log.i("mapp", ">>> mobile network enable=" + result);
		return result;
	}
}

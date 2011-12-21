package com.handroid.apps.quicksettings.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Message;
import android.widget.Button;

import com.handroid.apps.quicksettings.BaseApplication;
import com.handroid.apps.quicksettings.R;

public class WifiToggleController extends AbtractToggleController{
	
	private Button btnToggleWifi;
	private Button btnToggleWifiSettings;
	
	private WifiManager mWifiManager;

	public WifiToggleController(Context context) {
		super(context);
		mWifiManager = (WifiManager) this.mContext.getSystemService(Context.WIFI_SERVICE);
	}

//	@Override
//	public void doOnActivityPause() {
//		super.doOnActivityPause();
//	}

	@Override
	public void doOnActivityResume() {
		super.doOnActivityResume();
		updateWifiButtonUI();
	}
	
	@Override
	protected void updateUI(Message msg) {
		if (msg.what == MSG_WIFI_STATE) {
			switch (msg.arg1) {
			case WifiManager.WIFI_STATE_DISABLED:
				btnToggleWifi.setEnabled(true);
				updateBluetoothButtonState(false);
				break;
			case WifiManager.WIFI_STATE_DISABLING:
				btnToggleWifi.setEnabled(false);
				updateBluetoothButtonState(false);
				break;
			case WifiManager.WIFI_STATE_ENABLED:
				updateBluetoothButtonState(true);
				btnToggleWifi.setEnabled(true);
				break;
			case WifiManager.WIFI_STATE_ENABLING:
				BaseApplication.makeToastMsg("Turning on Wifi, please wait!");
				btnToggleWifi.setEnabled(false);
				updateBluetoothButtonState(false);
				break;
			case WifiManager.WIFI_STATE_UNKNOWN:
				break;
			}
		}
	}
	
	private void updateWifiButtonUI() {
		btnToggleWifi.setEnabled(true);
    	updateBluetoothButtonState(isWifiEnabled());
    }
	
	private void updateBluetoothButtonState(boolean enable){
		if (isWifiEnabled()) {
    		btnToggleWifiSettings.setEnabled(true);
    		btnToggleWifiSettings.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_wifi_settings_on, 0, 0);
    		btnToggleWifi.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_wifi_on, 0, 0);
    	} else {
    		btnToggleWifiSettings.setEnabled(false);
    		btnToggleWifiSettings.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_wifi_settings, 0, 0);
    		btnToggleWifi.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_wifi, 0, 0);
    	}
	}
	
	@Override
	protected void onReceivePhoneState(Context context, Intent intent) {
		int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
				WifiManager.WIFI_STATE_UNKNOWN);
		Message msg = null;		
		switch (extraWifiState) {
		case WifiManager.WIFI_STATE_DISABLED:
			msg = mHandler.obtainMessage(MSG_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED, 0);
			break;
		case WifiManager.WIFI_STATE_DISABLING:
			msg = mHandler.obtainMessage(MSG_WIFI_STATE, WifiManager.WIFI_STATE_DISABLING, 0);
			break;
		case WifiManager.WIFI_STATE_ENABLED:
			msg = mHandler.obtainMessage(MSG_WIFI_STATE, WifiManager.WIFI_STATE_ENABLED, 0);
			break;
		case WifiManager.WIFI_STATE_ENABLING:
			msg = mHandler.obtainMessage(MSG_WIFI_STATE, WifiManager.WIFI_STATE_ENABLING, 0);
			break;
		case WifiManager.WIFI_STATE_UNKNOWN:
			msg = mHandler.obtainMessage(MSG_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN, 0);
			break;
		}
		mHandler.sendMessage(msg);
		
	}

	@Override
	public void initToggleButton(Button[] btnObjs) {
		if (btnObjs == null) {
			return;
		}
		for (int i = 0; i< btnObjs.length; i++) {
			if (btnObjs[i].getId() == R.id.btn_togle_wifi) 
				btnToggleWifi = btnObjs[i];
			else if (btnObjs[i].getId() == R.id.btn_togle_wifi_settings)
				btnToggleWifiSettings = btnObjs[i];
		}
	}

	@Override
	public IntentFilter initIntentFilter() {
		IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		return intentFilter;
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
	// TODO Utils for checking Wifi Manager <S>
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
	// Utils for checking Bluetooth Adater <E>
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}

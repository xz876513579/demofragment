package com.handroid.apps.quicksettings.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Button;

import com.handroid.apps.quicksettings.R;

public class AirplaneToggleController extends AbtractToggleController{
	
	private Button btnToggleAirplane;
	
	private final int MSG_DISABLE_BTN_AIRPLANE 	= 100;
	private final int MSG_UPDATE_BTN_AIRPLANE 	= 101;
	
	public AirplaneToggleController(Context context) {
		super(context);
	}

//	@Override
//	public void doOnActivityPause() {
//		super.doOnActivityPause();
//	}

	@Override
	public void doOnActivityResume() {
		super.doOnActivityResume();
		updateAirplaneButtonUI();
	}
	
	@Override
	protected void updateUI(Message msg) {
		switch (msg.what) {
		case MSG_DISABLE_BTN_AIRPLANE:
			btnToggleAirplane.setEnabled(false);
			break;
			
		case MSG_UPDATE_BTN_AIRPLANE:
			updateAirplaneButtonState(true);
			
			if (((Boolean) msg.obj).booleanValue())
				updateAirplaneButtonState(true);
			else
				updateAirplaneButtonState(false);
			
			updateAirplaneButtonUI();
			break;
		}
	}
	
	private void updateAirplaneButtonUI() {
    	btnToggleAirplane.setEnabled(true);
    	updateAirplaneButtonState(isAirplaneTurnedOn());
    }
	
	private void updateAirplaneButtonState(boolean enable){
		if (enable) {
    		btnToggleAirplane.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_air_on, 0, 0);
		} else {
    		btnToggleAirplane.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_air_off, 0, 0);
		}
	}
	
	@Override
	protected void onReceivePhoneState(Context context, Intent intent) {
		String str = intent.getAction();
		if (TextUtils.isEmpty(str))
			return;
		if (str.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
			Message msg = null;
			msg = mHandler.obtainMessage(MSG_UPDATE_BTN_AIRPLANE, intent.getBooleanExtra("state", false));
			mHandler.sendMessage(msg);
		}
	}

	@Override
	public void initToggleButton(Button[] btnObjs) {
		if (btnObjs == null) {
			return;
		}
		if (btnObjs[0].getId() == R.id.btn_togle_airplane) 
			btnToggleAirplane = btnObjs[0];
	}

	@Override
	public IntentFilter initIntentFilter() {
		// Register with system to listen all event of Bluetooth, WiFi...
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		return intentFilter;
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
	// TODO Utils for checking Bluetooth Adater <S>
	public boolean isAirplaneTurnedOn() {
		return Settings.System.getInt(
			      this.mContext.getContentResolver(), 
			      Settings.System.AIRPLANE_MODE_ON, 0) == 1;
	}
	
	public void enableAirplane() {
		// toggle airplane mode
		Settings.System.putInt(
		      this.mContext.getContentResolver(),
		      Settings.System.AIRPLANE_MODE_ON, 1);
		Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
		intent.putExtra("state", true);
		mHandler.sendEmptyMessage(MSG_DISABLE_BTN_AIRPLANE);
		this.mContext.sendBroadcast(intent);
	}
	
	public void disableAirplane() {
		Settings.System.putInt(
			      this.mContext.getContentResolver(),
			      Settings.System.AIRPLANE_MODE_ON, 0);
		Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intent.putExtra("state", false);
		mHandler.sendEmptyMessage(MSG_DISABLE_BTN_AIRPLANE);
		this.mContext.sendBroadcast(intent);
	}
	// Utils for checking Bluetooth Adater <E>
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}

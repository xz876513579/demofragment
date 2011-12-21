package com.handroid.apps.quicksettings.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Button;

import com.handroid.apps.quicksettings.BaseApplication;
import com.handroid.apps.quicksettings.R;

public class BluetoothToggleController extends AbtractToggleController{
	
	private Button btnToggleBluetooth;
	private Button btnToggleBluetoothSettings;
	
	private BluetoothAdapter mBluetoothAdapter;

	public BluetoothToggleController(Context context) {
		super(context);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}

//	@Override
//	public void doOnActivityPause() {
//		super.doOnActivityPause();
//	}

	@Override
	public void doOnActivityResume() {
		super.doOnActivityResume();
		updateBluetoothButtonUI();
	}
	
	@Override
	protected void updateUI(Message msg) {
		if (msg.what == MSG_BLUETOOTH_STATE) {
			switch (msg.arg1) {
			case BluetoothAdapter.STATE_ON:
				updateBluetoothButtonState(true);
				btnToggleBluetooth.setEnabled(true);
				break;
			case BluetoothAdapter.STATE_TURNING_ON:
				BaseApplication.makeToastMsg("Turning on Bluetooth, please wait!");
				btnToggleBluetooth.setEnabled(false);
				updateBluetoothButtonState(false);
				break;
			case BluetoothAdapter.STATE_OFF:
				btnToggleBluetooth.setEnabled(true);
				updateBluetoothButtonState(false);
				break;
			case BluetoothAdapter.STATE_TURNING_OFF:
				btnToggleBluetooth.setEnabled(false);
				updateBluetoothButtonState(false);
				break;		
			default:
				updateBluetoothButtonUI();
				break;
			}
		}
	}
	
	private void updateBluetoothButtonUI() {
    	btnToggleBluetooth.setEnabled(true);
    	updateBluetoothButtonState(isBluetoothTurnedOn());
    }
	
	private void updateBluetoothButtonState(boolean enable){
		if (enable) {
    		btnToggleBluetoothSettings.setEnabled(true);
    		btnToggleBluetooth.setEnabled(true);
    		btnToggleBluetoothSettings.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_bluetooth_settings_on, 0, 0);
    		btnToggleBluetooth.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_bluetooth_on, 0, 0);
		} else {
    		btnToggleBluetoothSettings.setEnabled(false);
    		btnToggleBluetoothSettings.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_bluetooth_settings_off, 0, 0);
    		btnToggleBluetooth.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_bluetooth_off, 0, 0);
		}
	}
	
	@Override
	protected void onReceivePhoneState(Context context, Intent intent) {
		String str = intent.getAction();
		if (TextUtils.isEmpty(str))
			return;
		// -------- BLUETOOTH
		if (str.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
			int extraBluetoothState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
			Message msg = null;
			switch (extraBluetoothState) {
			case BluetoothAdapter.STATE_TURNING_ON:
				msg = mHandler.obtainMessage(MSG_BLUETOOTH_STATE, BluetoothAdapter.STATE_TURNING_ON, 0);
				break;
			case BluetoothAdapter.STATE_TURNING_OFF:
				msg = mHandler.obtainMessage(MSG_BLUETOOTH_STATE, BluetoothAdapter.STATE_TURNING_OFF, 0);
				break;
			case BluetoothAdapter.STATE_OFF:
				msg = mHandler.obtainMessage(MSG_BLUETOOTH_STATE, BluetoothAdapter.STATE_OFF, 0);
				break;
			case BluetoothAdapter.STATE_ON:
				msg = mHandler.obtainMessage(MSG_BLUETOOTH_STATE, BluetoothAdapter.STATE_ON, 0);
				break;
			}
			mHandler.sendMessage(msg);
		}
	}

	@Override
	public void initToggleButton(Button[] btnObjs) {
		if (btnObjs == null) {
			return;
		}
		for (int i = 0; i< btnObjs.length; i++) {
			if (btnObjs[i].getId() == R.id.btn_togle_bluetooth) 
				btnToggleBluetooth = btnObjs[i];
			else if (btnObjs[i].getId() == R.id.btn_togle_bluetooth_settings)
				btnToggleBluetoothSettings = btnObjs[i];
		}
	}

	@Override
	public IntentFilter initIntentFilter() {
		// Register with system to listen all event of Bluetooth, WiFi...
		IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		return intentFilter;
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
	// TODO Utils for checking Bluetooth Adater <S>
	public boolean isBluetoothTurnedOn() {
		if (mBluetoothAdapter != null) {
			if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON)
				return true;
			/*} else if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
				// Intent enableBtIntent = new
				// Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				// startActivityForResult(enableBtIntent, 113);
				return false;
			} else {
				// State.INTERMEDIATE_STATE;
			}*/
		}
		return false;
	}
	
	public int getBluetoothState() {
		return mBluetoothAdapter.getState();
	}
	
	public void enableBluetooth() {
		if (mBluetoothAdapter != null) {
			mBluetoothAdapter.enable();
		}
	}
	
	public void disableBluetooth() {
		if (mBluetoothAdapter != null) {
			mBluetoothAdapter.disable();
		}
	}
	// Utils for checking Bluetooth Adater <E>
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}

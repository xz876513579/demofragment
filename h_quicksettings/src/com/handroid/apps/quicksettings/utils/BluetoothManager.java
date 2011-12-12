package com.handroid.apps.quicksettings.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

public class BluetoothManager {

	private Context mContext;
	private BluetoothAdapter mBluetoothAdapter;
	
	public BluetoothManager(Context context) {
		this.mContext = context;
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	public boolean isBluetoothTurnedOn() {
		if (mBluetoothAdapter != null) {
			if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON)
				return true;
//			} else if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
//				// Intent enableBtIntent = new
//				// Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//				// startActivityForResult(enableBtIntent, 113);
//				return false;
//			} else {
//				// State.INTERMEDIATE_STATE;
//			}
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

}

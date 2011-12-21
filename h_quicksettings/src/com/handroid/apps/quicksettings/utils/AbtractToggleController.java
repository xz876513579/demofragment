package com.handroid.apps.quicksettings.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import com.handroid.apps.quicksettings.BaseApplication;

public abstract class AbtractToggleController {
	
	protected static final String TAG 			= "QuickToggleController";
	protected final int MSG_BLUETOOTH_STATE 	= 1000;
	protected final int MSG_WIFI_STATE 			= 1001;
	protected final int MSG_GPS_STATE 			= 1002;
	protected final int MSG_PHONE_ROATION_STATE = 1003;
	protected final int MSG_AIRPLANE			= 1004;

	protected Context mContext;
	protected Button[] mButtonToggleObjS;
	
	public AbtractToggleController(Context context) {
		if (context == null)
			this.mContext = BaseApplication.getAppContext();
		else 
			this.mContext = context;
	}
	
	protected Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			updateUI(msg);
		};
	};

	protected void updateUI(Message msg){};
	protected void onReceivePhoneState(Context context, Intent intent){};

	public abstract void initToggleButton(Button[] btnObjs);
	
	/**
	 * Register with system to listen all event of Bluetooth, WiFi...
	 * @return IntentFilter objects. 
	 */
	public /*abstract*/ IntentFilter initIntentFilter() {
		return null;
	};

	/**
	 * override this method to update UI state when Activity in onResume() state
	 */
	public void doOnActivityResume() {
		regisReceiver();
	}
	
	/**
	 * override this method to update UI state when Activity in onPause() state
	 */
	public void doOnActivityPause() {
		unregisReceive();
	}
	
	private void regisReceiver() {
    	IntentFilter mFilter = initIntentFilter();
        if (this.mContext != null && mFilter != null) {
        	Log.i(TAG, ">>> regis receiver phone state! " + getClass().getSimpleName());
        	this.mContext.registerReceiver(PhoneStateReceiver, mFilter);
        }
    }
    
    private void unregisReceive() {
    	Log.i(TAG, ">>> unregis receiver phone state! " + getClass().getSimpleName());
    	if (this.mContext != null) {
    		try {
    			this.mContext.unregisterReceiver(PhoneStateReceiver);
    		} catch (IllegalArgumentException e) {
    			// e.printStackTrace();
    			Log.w(TAG, ">>> cannot excute unregisReceive, Abort!" + PhoneStateReceiver.isInitialStickyBroadcast());
			}
    	}
    }
	
	private BroadcastReceiver PhoneStateReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			onReceivePhoneState(context, intent);
		}
	};
	
}

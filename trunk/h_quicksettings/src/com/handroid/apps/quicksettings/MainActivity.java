package com.handroid.apps.quicksettings;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.handroid.apps.quicksettings.utils.BluetoothManager;
import com.handroid.apps.quicksettings.utils.DataConManager;
import com.handroid.apps.quicksettings.utils.QuickWifiManager;

public class MainActivity extends Activity {
	
	Button btnToggleWifi;
	Button btnToggleWifiSettings;
	
	Button btnToggleBrightness;
	
	Button btnToggleMobileNetwork;
	
	Button btnToggleBluetooth;
	Button btnToggleBluetoothSettings;
	
	Button btnToggleManageApps;
	
	Button btnDone;
	
	// private WifiManager wifiManager;
	private BluetoothManager mBluetoothManager ;
	private QuickWifiManager mQuickWifiManager ;
	private DataConManager mDataConManager;
	
	private final int MSG_BLUETOOTH_STATE 		= 1000;
	private final int MSG_WIFI_STATE 			= 1001;
	private final int MSG_MOBILE_NETWORK_STATE 	= 1002;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Have the system blur any windows behind this one.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        setContentView(R.layout.main);
        
        mQuickWifiManager = new QuickWifiManager(getApplicationContext());
    	mBluetoothManager = new BluetoothManager(getApplicationContext());
    	mDataConManager = new DataConManager(getApplicationContext());

        // --------------------- Audio manager
//        AudioManager mVibrator = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        // RINGER_MODE_NORMAL
//        // RINGER_MODE_SILENT
//        mVibrator.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//        // BaseApplication.makeToastMsg("Ringer mode was: " + mVibrator.getRingerMode());

        // --------------------- Mobile network data connection
//        DataConManager mDataConManager = new DataConManager(getApplicationContext());
//        mDataConManager.switchMobileEnableState(true);
//        // BaseApplication.makeToastMsg("Data enable state: " + mDataConManager.getMobileDataEnabled());
        
		BaseApplication.makeToastMsg("airplane_mode_on: "
				+ (Settings.System.getInt(getApplicationContext()
						.getContentResolver(), "airplane_mode_on", 0) == 1));
		
        btnDone = (Button) findViewById(R.id.btn_qsettings_done);
        btnDone.setOnClickListener(viewOnClickListener);

        btnToggleWifi = (Button) findViewById(R.id.btn_togle_wifi);
        btnToggleWifi.setOnClickListener(viewOnClickListener);
        btnToggleWifiSettings = (Button) findViewById(R.id.btn_togle_wifi_settings);
        btnToggleWifiSettings.setOnClickListener(viewOnClickListener);
        
        btnToggleBluetooth = (Button) findViewById(R.id.btn_togle_bluetooth);
        btnToggleBluetooth.setOnClickListener(viewOnClickListener);
        btnToggleBluetoothSettings = (Button) findViewById(R.id.btn_togle_bluetooth_settings);
        btnToggleBluetoothSettings.setOnClickListener(viewOnClickListener);

        btnToggleBrightness = (Button) findViewById(R.id.btn_togle_brightness);
        btnToggleBrightness.setOnClickListener(viewOnClickListener);
        
        btnToggleManageApps = (Button) findViewById(R.id.btn_togle_manage_apps);
        btnToggleManageApps.setOnClickListener(viewOnClickListener);
        
        btnToggleMobileNetwork = (Button) findViewById(R.id.btn_togle_mobile_data);
        btnToggleMobileNetwork.setOnClickListener(viewOnClickListener);
        
        checkAllStateAndUpdateUI();
    }
    
    Handler mHandler = new Handler() {
    	@Override
    	public void handleMessage(android.os.Message msg) {
    		super.handleMessage(msg);
    		switch (msg.what) {
    		// -------------- MSG_BLUETOOTH_STATE
			case MSG_BLUETOOTH_STATE:
				switch (mBluetoothManager.getBluetoothState()) {
				case BluetoothAdapter.STATE_TURNING_ON:
					BaseApplication.makeToastMsg("Turning on Bluetooth, please wait!");
					btnToggleBluetoothSettings.setEnabled(false);
					updateBluetoothButtonUI();
					btnToggleBluetooth.setEnabled(false);
					this.sendEmptyMessageDelayed(MSG_BLUETOOTH_STATE, 6000);
					break;

				case BluetoothAdapter.STATE_TURNING_OFF:
					btnToggleBluetooth.setEnabled(true);
					btnToggleBluetoothSettings.setEnabled(true);
					updateBluetoothButtonUI();
					this.sendEmptyMessageDelayed(MSG_BLUETOOTH_STATE, 1500);
					break;
					
				case BluetoothAdapter.STATE_ON:
				case BluetoothAdapter.STATE_OFF:
					updateBluetoothButtonUI();
					break;
					
				default:
					updateBluetoothButtonUI();
					break;
				}
				break;
				
    		// -------------- MSG_WIFI_STATE
			case MSG_WIFI_STATE:
				switch (mQuickWifiManager.getWifiState()) {
				case WifiManager.WIFI_STATE_ENABLING:
					BaseApplication.makeToastMsg("Turning on Wifi, please wait!");
					btnToggleWifiSettings.setEnabled(false);
					updateWifiButtonUI();
					btnToggleWifi.setEnabled(false);
					this.sendEmptyMessageDelayed(MSG_WIFI_STATE, 6000);
					break;

				case WifiManager.WIFI_STATE_DISABLING:
					btnToggleWifi.setEnabled(true);
					btnToggleWifiSettings.setEnabled(true);
					updateWifiButtonUI();
					this.sendEmptyMessageDelayed(MSG_WIFI_STATE, 1500);
					break;
					
				case WifiManager.WIFI_STATE_ENABLED:
				case WifiManager.WIFI_STATE_DISABLED:
					updateWifiButtonUI();
					break;
					
				default:
					updateWifiButtonUI();
					break;
				}
				break;
    		// -------------- DEFAULT CASE
			default:
				break;
			}
    	};
    };
    
    private void checkAllStateAndUpdateUI(){ 
    	// check bluetooth state
    	updateBluetoothButtonUI();
    	updateWifiButtonUI();
    	updateDataNetworkButtonUI();
    }
    
    private void updateBluetoothButtonUI() {
    	btnToggleBluetooth.setEnabled(true);
    	if (mBluetoothManager.isBluetoothTurnedOn()) {
    		btnToggleBluetoothSettings.setEnabled(true);
    		btnToggleBluetoothSettings.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_bluetooth_settings_on, 0, 0);
    		btnToggleBluetooth.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_bluetooth_on, 0, 0);
    	} else {
    		btnToggleBluetoothSettings.setEnabled(false);
    		btnToggleBluetoothSettings.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_bluetooth_settings_off, 0, 0);
    		btnToggleBluetooth.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_bluetooth_off, 0, 0);
    	}
    }
    
    private void updateWifiButtonUI() {
    	btnToggleWifi.setEnabled(true);
    	if (mQuickWifiManager.isWifiEnabled()) {
    		btnToggleWifiSettings.setEnabled(true);
    		btnToggleWifiSettings.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_wifi_settings_on, 0, 0);
    		btnToggleWifi.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_wifi_on, 0, 0);
    	} else {
    		btnToggleWifiSettings.setEnabled(false);
    		btnToggleWifiSettings.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_wifi_settings, 0, 0);
    		btnToggleWifi.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_wifi, 0, 0);
    	}
    }
    
    private void updateDataNetworkButtonUI() {
		if (mDataConManager.getMobileDataEnabled()) {
			btnToggleMobileNetwork.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_network_on, 0, 0);
		} else {
			btnToggleMobileNetwork.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_network_off, 0, 0);
		}
    }
    @Override
    protected void onStart() {
    	super.onStart();
    	overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	// overridePendingTransition(R.anim.zoom_exit, R.anim.zoom_enter);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	// overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    };
    
    OnClickListener viewOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent mIntent;
			switch (v.getId()) {
			// ----------- WIFI SETTINGS
			case R.id.btn_togle_wifi:
				// do nothing when our wifi is anabling or disabling 
				if (mQuickWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING ||
						mQuickWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLING)
					return;
				if (mQuickWifiManager.isWifiEnabled()) {
					mQuickWifiManager.disableWifi();
				}  else {
					mQuickWifiManager.enableWifi();
				}
				btnToggleWifi.setEnabled(false);
				btnToggleWifiSettings.setEnabled(false);
				mHandler.sendEmptyMessage(MSG_WIFI_STATE);
				break;
			case R.id.btn_togle_wifi_settings:
				mIntent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
				startActivity(mIntent);
				break;

			// ----------- BRIGHTNESS SETTINGS
			case R.id.btn_togle_brightness:
				break;
				
			// ----------- FINISH QUICK SETTINGS APP
			case R.id.btn_qsettings_done:
				finish();
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				break;
				
			// ----------- BLUETOOTH SETTINGS
			case R.id.btn_togle_bluetooth:
				// do nothing when our Bluetooth is turning on turning off 
				if (mBluetoothManager.getBluetoothState() == BluetoothAdapter.STATE_TURNING_ON ||
						mBluetoothManager.getBluetoothState() == BluetoothAdapter.STATE_TURNING_OFF)
					return;

				if (mBluetoothManager.isBluetoothTurnedOn()) {
					mBluetoothManager.disableBluetooth();
				}  else {
					mBluetoothManager.enableBluetooth();
				}
				btnToggleBluetooth.setEnabled(false);
				btnToggleBluetoothSettings.setEnabled(false);
				mHandler.sendEmptyMessage(MSG_BLUETOOTH_STATE);
				break;
			case R.id.btn_togle_bluetooth_settings:
				mIntent = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
				startActivity(mIntent);
				break;
				
			// ----------- MANAGE APPS SETTINGS
			case R.id.btn_togle_manage_apps:
				mIntent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
				startActivity(mIntent);
				break;
				
			// ----------- MOBILE DATA NETWORK SETTINGS
			case R.id.btn_togle_mobile_data:
				if (mDataConManager.getMobileDataEnabled()) {
					
				} else {
					
				}
				break;
				
			default:
				BaseApplication.makeToastMsg("Cannot find any event handling for this button clicked!");				
				break;
			}
		}
	};
}
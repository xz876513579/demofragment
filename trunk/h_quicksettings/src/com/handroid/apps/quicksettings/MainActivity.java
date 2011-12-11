package com.handroid.apps.quicksettings;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Button btnToggleWifi;
	Button btnToggleBrightness;
	
	private WifiManager wifiManager;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Have the system blur any windows behind this one.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        setContentView(R.layout.main);
        
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

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
        
        // --------------------- Bluetooth 
//        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); 
//        if(mBluetoothAdapter != null) {
//            if(mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
//                mBluetoothAdapter.disable();
//            } else if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF){
//				// Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//				// startActivityForResult(enableBtIntent, 113);
//                mBluetoothAdapter.enable();
//            } else {
//                //State.INTERMEDIATE_STATE;
//            } 
//        }
        
		BaseApplication.makeToastMsg("airplane_mode_on: "
				+ (Settings.System.getInt(getApplicationContext()
						.getContentResolver(), "airplane_mode_on", 0) == 1));
		
        btnToggleWifi = (Button) findViewById(R.id.btn_togle_wifi);
        btnToggleWifi.setOnClickListener(viewOnClickListener);
        
        btnToggleBrightness = (Button) findViewById(R.id.btn_togle_brightness);
        btnToggleBrightness.setOnClickListener(viewOnClickListener);
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
    	overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    };
    
    OnClickListener viewOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_togle_wifi:
				BaseApplication.makeToastMsg("Toogle WiFi!");
				btnToggleWifi.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon, 0, 0);
				if (wifiManager != null) {
					// wifiManager.setWifiEnabled(!wifiManager.isWifiEnabled());
				}
				break;

			case R.id.btn_togle_brightness:
				break;
				
			default:
				BaseApplication.makeToastMsg("Cannot find any event handling for this View!");				
				break;
			}
		}
	};
}
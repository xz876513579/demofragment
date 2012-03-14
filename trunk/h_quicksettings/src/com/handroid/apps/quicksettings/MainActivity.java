package com.handroid.apps.quicksettings;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

import com.handroid.apps.quicksettings.utils.AirplaneToggleController;
import com.handroid.apps.quicksettings.utils.BluetoothToggleController;
import com.handroid.apps.quicksettings.utils.GpsToggleController;
import com.handroid.apps.quicksettings.utils.MobileNetworkToggleController;
import com.handroid.apps.quicksettings.utils.PhoneRotationToggleController;
import com.handroid.apps.quicksettings.utils.PreferenceUtils;
import com.handroid.apps.quicksettings.utils.WifiToggleController;
import com.handroid.apps.quicksettings.widget.ImageViewRounded;

public class MainActivity extends Activity {
	
	// private final String TAG = "QuickSettings";
	
	Button btnToggleWifi;
	Button btnToggleWifiSettings;
	
	Button btnTogglePhoneRotation;
	
	Button btnToggleMobileNetwork;
	
	Button btnToggleBluetooth;
	Button btnToggleBluetoothSettings;
	
	Button btnToggleBattery;
	
	Button btnToggleLight;
	Button btnToggleSoundVibrate;
	
	Button btnToggleManageApps;
	
	Button btnToogleGpsSettings;
	
	Button btnToggleAirPlane;
	
	Button btnDone;
	
	ImageView btnChangeSkins;
	ImageViewRounded imvBacgroundSkins;
	int[] wallpapperIds = { R.drawable.wallpaper1, R.drawable.wallpaper2,
			R.drawable.wallpaper3, R.drawable.wallpaper4,
			R.drawable.wallpaper5, R.drawable.wallpaper6,
			R.drawable.wallpaper7, R.drawable.wall_trans,
			R.drawable.wall_trans_white, R.drawable.wall_light, 
			R.drawable.wall_dark };
	int wallpaperIdx = 0;
	
	ImageView btnCallSettings;
	Dialog mDlgMenuAppSettings;
	CheckBox mCbShowNotification;
	NotificationManager notificationManager;
	
	// private DataConManager mDataConManager;
	private AudioManager mAudioManager;
	private MediaPlayer mMediaRingtonePlayer;
	
	private BluetoothToggleController mBluetoothToggleController;
	private WifiToggleController mWifiToggleController;
	private GpsToggleController mGpsToggleController;
	private PhoneRotationToggleController mPhoneRotationToggleController;
	private AirplaneToggleController mAirplaneToggleController;
	private MobileNetworkToggleController mMobileNetworkToggleController;
	
	// private final int MSG_MOBILE_NETWORK_STATE 	= 1002;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Have the system blur any windows behind this one.
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
//                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        
        mBluetoothToggleController = new BluetoothToggleController(getApplicationContext());
        mWifiToggleController = new WifiToggleController(getApplicationContext());
        mGpsToggleController = new GpsToggleController(getApplicationContext());
        mPhoneRotationToggleController = new PhoneRotationToggleController(getApplicationContext());
        mAirplaneToggleController = new AirplaneToggleController(getApplicationContext());
        mMobileNetworkToggleController = new MobileNetworkToggleController(getApplicationContext());        
        
        setContentView(R.layout.main);
        
//    	mDataConManager = new DataConManager(getApplicationContext());
    	mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

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
        
//		BaseApplication.makeToastMsg("airplane_mode_on: "
//				+ (Settings.System.getInt(getApplicationContext()
//						.getContentResolver(), "airplane_mode_on", 0) == 1));
		
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

        btnTogglePhoneRotation = (Button) findViewById(R.id.btn_togle_phone_rotation);
        btnTogglePhoneRotation.setOnClickListener(viewOnClickListener);
        
        btnToggleManageApps = (Button) findViewById(R.id.btn_togle_manage_apps);
        btnToggleManageApps.setOnClickListener(viewOnClickListener);
        
        btnToggleMobileNetwork = (Button) findViewById(R.id.btn_togle_mobile_data);
        btnToggleMobileNetwork.setOnClickListener(viewOnClickListener);
        
        btnToggleBattery = (Button) findViewById(R.id.btn_togle_battery);
        btnToggleBattery.setOnClickListener(viewOnClickListener);
        
        btnToggleLight = (Button) findViewById(R.id.btn_togle_light);
        btnToggleLight.setOnClickListener(viewOnClickListener);
        
        btnToggleSoundVibrate = (Button) findViewById(R.id.btn_togle_sound_vibrate);
        btnToggleSoundVibrate.setOnClickListener(viewOnClickListener);
        
        btnToogleGpsSettings = (Button) findViewById(R.id.btn_togle_gps_settings);
        btnToogleGpsSettings.setOnClickListener(viewOnClickListener);
        
        btnToggleAirPlane = (Button) findViewById(R.id.btn_togle_airplane);
        btnToggleAirPlane.setOnClickListener(viewOnClickListener);
        
        btnChangeSkins = (ImageView) findViewById(R.id.btn_toggle_skins);
        btnChangeSkins.setOnClickListener(viewOnClickListener);
        imvBacgroundSkins = (ImageViewRounded) findViewById(R.id.imv_wallpaper);
        
        btnCallSettings = (ImageView) findViewById(R.id.btn_toggle_info);
        btnCallSettings.setOnClickListener(viewOnClickListener);
        
        Button[] arrBluetoothBtnToggle = new Button[] {btnToggleBluetooth, btnToggleBluetoothSettings};
        mBluetoothToggleController.initToggleButton(arrBluetoothBtnToggle);
        
        Button[] arrWifiBtnToggle = new Button[] {btnToggleWifi, btnToggleWifiSettings};
        mWifiToggleController.initToggleButton(arrWifiBtnToggle);
        
        Button[] arrGpsBtnToggle = new Button[] {btnToogleGpsSettings};
        mGpsToggleController.initToggleButton(arrGpsBtnToggle);
        
        Button[] arrPhoneRotationBtnToggle = new Button[] {btnTogglePhoneRotation};
        mPhoneRotationToggleController.initToggleButton(arrPhoneRotationBtnToggle);
        
        Button[] arrPhoneAirplaneBtnToggle = new Button[] {btnToggleAirPlane};
        mAirplaneToggleController.initToggleButton(arrPhoneAirplaneBtnToggle);
        
        Button[] arrPhoneMobileNetworkBtnToggle = new Button[] {btnToggleMobileNetwork};
        mMobileNetworkToggleController.initToggleButton(arrPhoneMobileNetworkBtnToggle);
        
        updateSoundVibrateButtonState();
        updateWallpaperSkins();
        
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }
    
    private void updateWallpaperSkins() {
    	wallpaperIdx = PreferenceUtils.getIntPref(
                getApplicationContext(), 
                Constant.PREF_NAME, 
                Constant.PREF_WALL_POS, 
                0);
		imvBacgroundSkins.setImageDrawable(getResources().getDrawable(
				wallpapperIds[wallpaperIdx]));
		imvBacgroundSkins.refreshSkinBackground();
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
//		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
//				&& event.getAction() == KeyEvent.ACTION_DOWN)
//    		return true;
    	return super.dispatchKeyEvent(event);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
//    	checkAllStateAndUpdateUI();
    	mBluetoothToggleController.doOnActivityResume();
    	mWifiToggleController.doOnActivityResume();
    	mGpsToggleController.doOnActivityResume();
    	mPhoneRotationToggleController.doOnActivityResume();
    	mAirplaneToggleController.doOnActivityResume();
    	mMobileNetworkToggleController.doOnActivityResume();
		registerReceiver(batteryReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
		mMediaRingtonePlayer = MediaPlayer.create(getApplicationContext(),
				R.raw.crystal_ring);
	};
    
    @Override
    protected void onPause() {
    	super.onPause();
    	mBluetoothToggleController.doOnActivityPause();
    	mWifiToggleController.doOnActivityPause();
    	// mGpsToggleController.doOnActivityPause();
    	// mPhoneRotationToggleController.doOnActivityPause();
    	mAirplaneToggleController.doOnActivityPause();
    	mMobileNetworkToggleController.doOnActivityPause();
    	
    	unregisterReceiver(batteryReceiver);
    	if (mMediaRingtonePlayer != null) {
    		mMediaRingtonePlayer.release();
    	}
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    }
    
    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
    	@Override
    	public void onReceive( Context context, Intent intent ) {
	    	int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
	    	
	    	int temp = Math.round(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10.0F);
	    	
	    	// temp = temp * 9 / 5 + 32; // enable this line if we want using ºF
	    	// android.util.Log.i(TAG, "--> battery: " + level + "%" + " " + (temp / 10.0F) + "C");
    		// btnToggleBattery.setText(getString(R.string.btn_text_battery, level, temp));
	    	if (level <= 100 && level > 90) {
	    		btnToggleBattery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_batt_100, 0, 0);
	    	} else {
	    		if (level < 90 && level > 65) {
	    			btnToggleBattery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_batt_75, 0, 0);
	    		} else {
	    			if (level < 65 && level > 35) {
	    				btnToggleBattery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_batt_50, 0, 0);
	    			} else {
	    				if (level < 35 && level > 15) {
	    					btnToggleBattery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_batt_25, 0, 0);	    					
	    				} else {
	    					btnToggleBattery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_batt_10, 0, 0);
	    				}
	    			}
	    		}
	    	}
			btnToggleBattery.setText(level + getString(R.string.txt_percent)
					+ "\n" + temp + getString(R.string.txt_degree));
    	}
	};
    
    private void updateSoundVibrateButtonState() {
    	if (mAudioManager == null) {
    		btnToggleSoundVibrate.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_ringer_off, 0, 0);
			btnToggleSoundVibrate.setText("Error!");
    		return ;
    	}
    		 
    	switch (mAudioManager.getRingerMode()) {
		case AudioManager.RINGER_MODE_SILENT:
			btnToggleSoundVibrate.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_ringer_off, 0, 0);
			btnToggleSoundVibrate.setText(R.string.btn_text_vibrate_silent);
			break;
		case AudioManager.RINGER_MODE_NORMAL:
			btnToggleSoundVibrate.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_ringer_on, 0, 0);
			btnToggleSoundVibrate.setText(R.string.btn_text_vibrate_normal);
			break;
		case AudioManager.RINGER_MODE_VIBRATE:
			btnToggleSoundVibrate.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_vibration_on, 0, 0);
			btnToggleSoundVibrate.setText(R.string.btn_text_vibrate_vibrate);
			break;
		default:
			break;
		}
    }
    
    // TODO OnClick event
    OnClickListener viewOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent mIntent;
			switch (v.getId()) {
			
			// ----------- WIFI SETTINGS
			case R.id.btn_togle_wifi:
				// do nothing when our wifi is anabling or disabling 
				if (mWifiToggleController.getWifiState() == WifiManager.WIFI_STATE_ENABLING ||
						mWifiToggleController.getWifiState() == WifiManager.WIFI_STATE_DISABLING)
					return;
				if (mWifiToggleController.isWifiEnabled()) {
					mWifiToggleController.disableWifi();
				}  else {
					mWifiToggleController.enableWifi();
				}
				btnToggleWifi.setEnabled(false);
				btnToggleWifiSettings.setEnabled(false);
				break;
			case R.id.btn_togle_wifi_settings:
				mIntent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
				mIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				try {
					startActivity(mIntent);
				} catch (ActivityNotFoundException e) {
					BaseApplication.makeToastMsg(getString(R.string.activity_not_found));
				}
				break;

			// ----------- PHONE ROTATION SETTINGS
			case R.id.btn_togle_phone_rotation:
				if (mPhoneRotationToggleController.isPhoneRotationTurnedOn() == 0) {
					mPhoneRotationToggleController.setPhoneRotationState(true);
				} else {
					mPhoneRotationToggleController.setPhoneRotationState(false);
				}
				mPhoneRotationToggleController.updatePhoneRotationButtonState(mPhoneRotationToggleController.isPhoneRotationTurnedOn());
				break;
				
			// ----------- FINISH QUICK SETTINGS APP
			case R.id.btn_qsettings_done:
				finish();
				// overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				break;
				
			// ----------- BLUETOOTH SETTINGS
			case R.id.btn_togle_bluetooth:
				// do nothing when our Bluetooth is turning on turning off 
				if (mBluetoothToggleController.getBluetoothState() == BluetoothAdapter.STATE_TURNING_ON ||
						mBluetoothToggleController.getBluetoothState() == BluetoothAdapter.STATE_TURNING_OFF)
					return;

				if (mBluetoothToggleController.isBluetoothTurnedOn()) {
					mBluetoothToggleController.disableBluetooth();
				}  else {
					mBluetoothToggleController.enableBluetooth();
				}
				btnToggleBluetooth.setEnabled(false);
				btnToggleBluetoothSettings.setEnabled(false);
				break;
			case R.id.btn_togle_bluetooth_settings:
				mIntent = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
				mIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				try {
					startActivity(mIntent);
				} catch (ActivityNotFoundException e) {
					BaseApplication.makeToastMsg(getString(R.string.activity_not_found));
				}
				break;
				
			// ----------- MANAGE APPS SETTINGS
			case R.id.btn_togle_manage_apps:
				mIntent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
				mIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				try {
					startActivity(mIntent);
				} catch (ActivityNotFoundException e) {
					BaseApplication.makeToastMsg(getString(R.string.activity_not_found));
				}
				break;
				
			// ----------- MOBILE DATA NETWORK SETTINGS
			case R.id.btn_togle_mobile_data:
				if (mMobileNetworkToggleController.getMobileDataEnabled()) {
					mMobileNetworkToggleController.switchMobileEnableState(false);
				} else {
					mMobileNetworkToggleController.switchMobileEnableState(true);
				}
				break;
				
			// ----------- BATTERY
			case R.id.btn_togle_battery:
				// mIntent = new Intent().setClassName("com.android.settings", "com.android.settings.ChooseLockGeneric");
				mIntent = new Intent().setClassName("com.android.settings",
						"com.android.settings.fuelgauge.PowerUsageSummary");
				mIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				try {
					startActivity(mIntent);
				} catch (ActivityNotFoundException e) {
					BaseApplication.makeToastMsg(getString(R.string.activity_not_found));
				} catch (SecurityException se) {
					se.printStackTrace();
					BaseApplication.makeToastMsg(getString(R.string.activity_not_found));
				}
				break;
				
			// ----------- PHONE LIGHT
			case R.id.btn_togle_light:
				BaseApplication.makeToastMsg("Coming soon!");
				Settings.System.putInt(getApplicationContext()
						.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS, 20);

				break;

			// ----------- PHONE VIBRATE
			case R.id.btn_togle_sound_vibrate:
				if (mAudioManager != null) {
					mAudioManager.setRingerMode((mAudioManager.getRingerMode() + 1) % 3);
					switch (mAudioManager.getRingerMode()) {
					case AudioManager.RINGER_MODE_VIBRATE:
						// play vibrate
						((Vibrator) getSystemService(Context.VIBRATOR_SERVICE))
								.vibrate(200);
						break;
						
					case AudioManager.RINGER_MODE_NORMAL:
						// play vibrate
						((Vibrator) getSystemService(Context.VIBRATOR_SERVICE))
								.vibrate(300);
						// play sound
						mMediaRingtonePlayer.start();
						break;
					
					default:
						break;
					}
				}
				updateSoundVibrateButtonState();
				break;
				
			// ----------- GPS
			case R.id.btn_togle_gps_settings:
				mIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				mIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				try {
					startActivity(mIntent);
				} catch (ActivityNotFoundException e) {
					BaseApplication.makeToastMsg(getString(R.string.activity_not_found));
				} catch (SecurityException se) {
					se.printStackTrace();
					BaseApplication.makeToastMsg(getString(R.string.activity_not_found));
				}
				break;
				
			// ----------- Air plane
			case R.id.btn_togle_airplane:
				if (mAirplaneToggleController.isAirplaneTurnedOn()) {
					mAirplaneToggleController.disableAirplane();
				} else {
					mAirplaneToggleController.enableAirplane();
				}
				break;
				
			// ------------ Change back ground skins
			case R.id.btn_toggle_skins:
				wallpaperIdx = (wallpaperIdx + 1) % wallpapperIds.length;
		    	// saving current wall position
		    	PreferenceUtils.saveIntPref(
		                getApplicationContext(), 
		                Constant.PREF_NAME, 
		                Constant.PREF_WALL_POS, 
		                wallpaperIdx);
				updateWallpaperSkins();
				break;

			case R.id.btn_toggle_info:
				if (mDlgMenuAppSettings == null) {
					mDlgMenuAppSettings = new Dialog(
							MainActivity.this, 
							R.style.Theme_QuickSettingsDialog);
					mDlgMenuAppSettings.requestWindowFeature(Window.FEATURE_NO_TITLE);
			        View mMenuLayout = LayoutInflater.from(
			        		getApplicationContext()).inflate(
			        				R.layout.app_settings, null);
			        mDlgMenuAppSettings.setContentView(mMenuLayout);
			        
					Button btnOk = (Button) mMenuLayout
							.findViewById(R.id.bt_appsetting_ok_id);
			        btnOk.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							mDlgMenuAppSettings.dismiss();
						}
					});
					mCbShowNotification = (CheckBox) mMenuLayout
							.findViewById(R.id.radiobtn_show_notification);
					mCbShowNotification
							.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			        	@Override
						public void onCheckedChanged(
								CompoundButton buttonView,
								boolean isChecked) {
			        		if (isChecked) {
			        			PreferenceUtils.saveBoolPref(
			    		                getApplicationContext(), 
			    		                Constant.PREF_NAME, 
			    		                Constant.PREF_CHECK_SHOW_NOTIFICATION, 
			    		                true);
			    		        Notification notification = new Notification(
			    		        		R.drawable.ic_launcher,
			    		                "Launch QuickSettingsApp" , 
			    		                System.currentTimeMillis());
			    		        // Hide the notification after its selected
			    		        notification.flags |= Notification.FLAG_NO_CLEAR;
			    		        
			    		        Intent intent = new Intent(getApplicationContext(), 
			    		        		MainActivity.class);
			    		        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			    		        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

								PendingIntent pendingIntent = PendingIntent
										.getActivity(getApplicationContext(),
												0, intent, 0);
			    		        notification.setLatestEventInfo(
			    		        		getApplicationContext(),
			    		        		"Quick System Settings App", 
			    		                "Press to launch QuickSettingsApp",
			    		                pendingIntent);
			    		        if (notificationManager != null) {
			    		        	notificationManager.notify(113, notification);
			    		        }
			        		} else {
			        			if (notificationManager != null) {
			        				PreferenceUtils.saveBoolPref(
				    		                getApplicationContext(), 
				    		                Constant.PREF_NAME, 
				    		                Constant.PREF_CHECK_SHOW_NOTIFICATION, 
				    		                false);
			        				notificationManager.cancel(113);
			        			}
			        		}
			        	}
			        });
			        mDlgMenuAppSettings.setCancelable(true);
				}
				if (!mDlgMenuAppSettings.isShowing()) {
					if (mCbShowNotification != null) {
						mCbShowNotification.setChecked(
							PreferenceUtils.getBoolPref(
	    		                getApplicationContext(), 
	    		                Constant.PREF_NAME, 
	    		                Constant.PREF_CHECK_SHOW_NOTIFICATION, 
	    		                false));
					}
					mDlgMenuAppSettings.show();
				} else {
					mDlgMenuAppSettings.dismiss();
				}
				break;
				
			default:
				BaseApplication.makeToastMsg("Cannot find any event handling for this button clicked!");				
				break;
			}
		}
	};
}
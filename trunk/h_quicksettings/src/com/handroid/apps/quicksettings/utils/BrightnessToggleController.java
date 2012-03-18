package com.handroid.apps.quicksettings.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.widget.Button;

import com.handroid.apps.quicksettings.Constant;
import com.handroid.apps.quicksettings.R;

public class BrightnessToggleController extends AbtractToggleController{
	
	private Button btnToggleBrightness = null;
	private Context mContext;
	private final int BACKLIGHT_LOW = 10;
	private final int BACKLIGHT_MIDDLE = 50;
	private final int BACKLIGHT_HIGHT = 150;
	private final int BACKLIGHT_CURRENT = -1;
	private final int BACKLIGHT_AUTO = -2;
	
	public BrightnessToggleController(Context context) {
		super(context);
		mContext = context;
	}

	/*@Override
	public void doOnActivityPause() {
		super.doOnActivityPause();
	}*/

	@Override
	public void doOnActivityResume() {
		super.doOnActivityResume();
		try {
			updateBtnUI();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	private void updateBtnUI() throws NullPointerException {
		if (!isBrightnessSetToAutomatic()) {
			int currentScreenBrightness = Settings.System.getInt(
					mContext.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS, 10);
			switch (currentScreenBrightness) {
			case BACKLIGHT_HIGHT:
				btnToggleBrightness.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_light_hight, 0, 0);
				btnToggleBrightness.setText("Brightness\nHight");
				PreferenceUtils.saveIntPref(
		                mContext, 
		                Constant.PREF_NAME, 
		                Constant.PREF_CURRENT_LIGHT, 
		                BACKLIGHT_HIGHT);
				break;
				
			case BACKLIGHT_MIDDLE:
				btnToggleBrightness.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_light_middle, 0, 0);
				btnToggleBrightness.setText("Brightness\nMiddle");
				PreferenceUtils.saveIntPref(
		                mContext, 
		                Constant.PREF_NAME, 
		                Constant.PREF_CURRENT_LIGHT, 
		                BACKLIGHT_MIDDLE);
				break;
				
			case BACKLIGHT_LOW:
				btnToggleBrightness.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_light_low, 0, 0);
				btnToggleBrightness.setText("Brightness\nLow");
				PreferenceUtils.saveIntPref(
		                mContext, 
		                Constant.PREF_NAME, 
		                Constant.PREF_CURRENT_LIGHT, 
		                BACKLIGHT_LOW);
				break;
				
			default:
				btnToggleBrightness.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_light_current, 0, 0);
				btnToggleBrightness.setText("Brightness\n" + currentScreenBrightness);
				PreferenceUtils.saveIntPref(
		                mContext, 
		                Constant.PREF_NAME, 
		                Constant.PREF_CURRENT_LIGHT, 
		                BACKLIGHT_CURRENT);
				break;
			}
		} else {
			btnToggleBrightness.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_light_auto, 0, 0);
			btnToggleBrightness.setText("Automatic\nBrightness");
			PreferenceUtils.saveIntPref(
	                mContext, 
	                Constant.PREF_NAME, 
	                Constant.PREF_CURRENT_LIGHT, 
	                BACKLIGHT_AUTO);
		}
	}
	
	private boolean isBrightnessSetToAutomatic() {
		try {
			int brightnessMode = Settings.System.getInt(
					mContext.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE);

			if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
				return true;
			}
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void updateScreenLevel() {
		int currentScreenLevel = PreferenceUtils.getIntPref(
                mContext, 
                Constant.PREF_NAME, 
                Constant.PREF_CURRENT_LIGHT, 
                BACKLIGHT_CURRENT);
		switch (currentScreenLevel) {
		case BACKLIGHT_AUTO:
			setAutobrightness(false);
			break;
			
		case BACKLIGHT_CURRENT:
			break;
			
		default:
			break;
		}
	}

	private boolean isSupportAutomaticBrightness() {
		if (Build.VERSION.SDK_INT == 4) {
			return false;
		}
		try {
			if (((SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE))
					.getDefaultSensor(Sensor.TYPE_LIGHT) == null) {
				Log.i("mapp", "Ooops, your phone does not support auto brightness mode!");
				return false;
			}
		} catch (NullPointerException e) {
			Log.i("mapp", "Ooops, error: " + e.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * setAutobrightness
	 * @param setAuto set <b>true</b> if we want to enter automatic brightness mode.
	 */
	private void setAutobrightness(boolean setAuto) {
		if (!isSupportAutomaticBrightness()) {
			return;
		}
		try {
			if (setAuto) {
				Settings.System.putInt(mContext.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS_MODE,
						Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
			} else {
				Settings.System.putInt(mContext.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS_MODE,
						Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
			}
		} catch (NullPointerException e) {
			Log.e("mapp", "Cannot set automaticmode " + e.toString());
		}
	}
	
	@Override
	public void initToggleButton(Button[] btnObjs) {
		if (btnObjs == null) {
			return;
		}
		if (btnObjs[0].getId() == R.id.btn_togle_light)
			btnToggleBrightness = btnObjs[0];
	}

}

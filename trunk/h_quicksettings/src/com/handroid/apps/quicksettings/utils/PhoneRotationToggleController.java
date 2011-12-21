package com.handroid.apps.quicksettings.utils;

import android.content.Context;
import android.provider.Settings;
import android.widget.Button;

import com.handroid.apps.quicksettings.R;

public class PhoneRotationToggleController extends AbtractToggleController{
	
	private Button btnTogglePhoneRotationSettings;
	
	public PhoneRotationToggleController(Context context) {
		super(context);
	}

	@Override
	public void doOnActivityResume() {
		// super.doOnActivityResume(); // in this case we don't need to regis any Broadcast, so comment out super calling!
		updatePhoneRotationButtonState(isPhoneRotationTurnedOn());
	}
	
	
	public void updatePhoneRotationButtonState(int state){
		if (btnTogglePhoneRotationSettings == null)
			return;
		
		if (state == 1) {
    		btnTogglePhoneRotationSettings.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_auto_rotation_on, 0, 0);
		} else {
    		btnTogglePhoneRotationSettings.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_auto_rotation_off, 0, 0);
		}
	}
	
	@Override
	public void initToggleButton(Button[] btnObjs) {
		if (btnObjs == null) {
			return;
		}
		if (btnObjs[0].getId() == R.id.btn_togle_phone_rotation)
			btnTogglePhoneRotationSettings = btnObjs[0];
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
	// TODO Utils for checking PhoneRotation state <S>
	public int isPhoneRotationTurnedOn() {
		return Settings.System.getInt(this.mContext.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
	}
	
	public void setPhoneRotationState(boolean canRotate) {
		if (canRotate) {
			Settings.System.putInt(this.mContext.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
		} else {
			Settings.System.putInt(this.mContext.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
		}
	}
	// Utils for checking PhoneRotation Adater <E>
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}

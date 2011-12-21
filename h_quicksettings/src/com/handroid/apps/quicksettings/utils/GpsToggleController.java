package com.handroid.apps.quicksettings.utils;

import android.content.Context;
import android.location.LocationManager;
import android.widget.Button;

import com.handroid.apps.quicksettings.R;

public class GpsToggleController extends AbtractToggleController{
	
	private Button btnToggleGpsSettings;
	
	private LocationManager mLocationManager;

	public GpsToggleController(Context context) {
		super(context);
		if (context != null)
			mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE );
	}

	@Override
	public void doOnActivityResume() {
		// super.doOnActivityResume(); // in this case we don't need to regis any Broadcast, so comment out super calling!
		updateGpsButtonState(isGpsTurnedOn());
	}
	
	
	private void updateGpsButtonState(boolean enable){
		if (btnToggleGpsSettings == null)
			return;
		
		if (enable) {
    		btnToggleGpsSettings.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_gps_on, 0, 0);
		} else {
    		btnToggleGpsSettings.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_gps_off, 0, 0);
		}
	}
	
	@Override
	public void initToggleButton(Button[] btnObjs) {
		if (btnObjs == null) {
			return;
		}
		if (btnObjs[0].getId() == R.id.btn_togle_gps_settings)
			btnToggleGpsSettings = btnObjs[0];
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
	// TODO Utils for checking GPS state <S>
	public boolean isGpsTurnedOn() {
		if (mLocationManager != null) {
			return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}
		return false;
	}
	// Utils for checking Gps Adater <E>
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}

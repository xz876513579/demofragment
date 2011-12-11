package com.handroid.apps.quicksettings.utils;

import android.content.Context;

public class AudioManager {
	
	private Context mContext;
	
	public AudioManager (Context context) {
		this.mContext = context;
	}
	
	public boolean set () {
		if (this.mContext == null)
			return false;
        AudioManager mVibrator = (AudioManager) this.mContext.getSystemService(Context.AUDIO_SERVICE);

        return true;
	}
}

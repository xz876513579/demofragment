package com.handroid.apps.quicksettings.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class CoolButton extends Button {
	
	private Typeface mTypeface;
	// private ProgressBar mProgressBar;

	public CoolButton(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		if (context != null) {
			mTypeface = Typeface.createFromAsset(context.getAssets(),
					"fonts/MiArial.ttf");
		} 
		if (mTypeface != null)
			this.setTypeface(mTypeface);
		
		// mProgressBar = new ProgressBar(context);
		// mProgressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}
}

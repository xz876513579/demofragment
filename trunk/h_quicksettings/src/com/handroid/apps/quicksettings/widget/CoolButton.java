package com.handroid.apps.quicksettings.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class CoolButton extends Button {
	
	private Typeface mTypeface;

	public CoolButton(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		if (context != null) {
			mTypeface = Typeface.createFromAsset(context.getAssets(),
					"fonts/MiArial.ttf");
		} 
		if (mTypeface != null)
			this.setTypeface(mTypeface);
	}
	
}

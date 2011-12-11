package com.handroid.apps.quicksettings.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CoolTextView extends TextView {
	
	private Typeface mTypeface;

	/*public CoolTextView(Context context) {
		super(context);
		if (context != null) {
			mTypeface = Typeface.createFromAsset(context.getAssets(),
					"fonts/NDT_Arial.ttf");
		} 
		if (mTypeface != null)
			this.setTypeface(mTypeface);

	}*/
	
	public CoolTextView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		if (context != null) {
			mTypeface = Typeface.createFromAsset(context.getAssets(),
					"fonts/MiArial.ttf");
		} 
		if (mTypeface != null)
			this.setTypeface(mTypeface);
	}
	
}

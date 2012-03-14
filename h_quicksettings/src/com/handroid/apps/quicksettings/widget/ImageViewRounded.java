package com.handroid.apps.quicksettings.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageViewRounded extends ImageView {
    
    private BitmapDrawable mViewDrawable;
    private Bitmap mFullSizeBitmap;
    private Bitmap mRoundBitmap;
    private Bitmap mScaledBitmap;

	public ImageViewRounded(Context context) {
		super(context);
		initRoundImage();
	}

	public ImageViewRounded(Context context, AttributeSet attrs) {
		super(context, attrs);
		initRoundImage();
	}

	public ImageViewRounded(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initRoundImage();
	}
	
	private void initRoundImage() {
	    mViewDrawable = (BitmapDrawable) getDrawable();
	    if (mViewDrawable != null) {
	        mFullSizeBitmap = mViewDrawable.getBitmap();
	    }
	    needToUpdateImageSrc = true;
	}
	
	private boolean needToUpdateImageSrc = false;
	public void refreshSkinBackground() {
		initRoundImage();
		requestLayout();
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
	    // mViewDrawable = (BitmapDrawable) getDrawable();

		if (mViewDrawable == null) {
			return;
		}

		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}

		// Bitmap fullSizeBitmap = mViewDrawable.getBitmap();

		int scaledWidth = getMeasuredWidth();
		int scaledHeight = getMeasuredHeight();

		if (mScaledBitmap == null && scaledWidth > 0 && scaledHeight > 0
				|| needToUpdateImageSrc) {
    		if (scaledWidth == mFullSizeBitmap.getWidth()
    				&& scaledHeight == mFullSizeBitmap.getHeight()) {
    			mScaledBitmap = mFullSizeBitmap;
    		} else {
    			mScaledBitmap = Bitmap.createScaledBitmap(mFullSizeBitmap,
    					scaledWidth, scaledHeight, true /* filter */);
    		}
		}

        if (mRoundBitmap == null || needToUpdateImageSrc) {
    		mRoundBitmap = getRoundedCornerBitmap(getContext(),
    				mScaledBitmap, 3, scaledWidth, scaledHeight, false, false,
    				false, false);
    		needToUpdateImageSrc = false;
		}
        if (!mRoundBitmap.isRecycled()) {
        	canvas.drawBitmap(mRoundBitmap, 0, 0, null);
        }
	}

	public Bitmap getRoundedCornerBitmap(Context context, Bitmap input,
			int pixels, int w, int h, boolean squareTL, boolean squareTR,
			boolean squareBL, boolean squareBR) {

		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final float densityMultiplier = context.getResources()
				.getDisplayMetrics().density;

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);

		// make sure that our rounded corner is scaled appropriately
		final float roundPx = pixels * densityMultiplier;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		// draw rectangles over the corners we want to be square
		if (squareTL) {
			canvas.drawRect(0, 0, w / 2, h / 2, paint);
		}
		if (squareTR) {
			canvas.drawRect(w / 2, 0, w, h / 2, paint);
		}
		if (squareBL) {
			canvas.drawRect(0, h / 2, w / 2, h, paint);
		}
		if (squareBR) {
			canvas.drawRect(w / 2, h / 2, w, h, paint);
		}

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(input, 0, 0, paint);

		return output;
	}

}

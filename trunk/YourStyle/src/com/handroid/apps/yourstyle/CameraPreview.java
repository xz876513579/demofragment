package com.handroid.apps.yourstyle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.handroid.apps.yourstyle.widget.CameraPreviewView;

public class CameraPreview extends Activity {
    private CameraPreviewView mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.photo_capture_layout);

    }

}

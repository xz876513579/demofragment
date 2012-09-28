package com.handroid.apps.yourstyle;

import java.util.List;

import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.CameraProfile;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.handroid.apps.yourstyle.widget.CameraPreviewView;
import com.handroid.camera.CameraSettings;
import com.handroid.camera.CameraUtils;
import com.handroid.camera.ComboPreferences;

public class CameraPreview extends BaseActivity implements OnClickListener{
    
    private static final String TAG = "camera_prev";

    private CameraPreviewView mPreview;
    
    private Button mBtnCapture;
    private ComboPreferences mComboPref;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // init first variables
        mComboPref = new ComboPreferences(this);

        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.photo_capture_layout);
        
        mPreview = (CameraPreviewView) findViewById(R.id.cam_review);
        
        mBtnCapture = (Button) findViewById(R.id.btn_capture);
        mBtnCapture.setOnClickListener(this);
    }
    
    @Override
    public void onAttachedToWindow() {
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
        super.onAttachedToWindow();
    }

    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_capture:
            mPreview.photoCapture();
            break;

        default:
            break;
        }
    }
    
    private void updateCameraParametersPreference() {

        /*if (mFocusAreaSupported) {
            mCameraParameters.setFocusAreas(mFocusManager.getFocusAreas());
        }*/

        /*if (mMeteringAreaSupported) {
            // Use the same area for focus and metering.
            mCameraParameters.setMeteringAreas(mFocusManager.getMeteringAreas());
        }*/

        // Set picture size.
        String pictureSize = mComboPref.getString(
                CameraSettings.KEY_PICTURE_SIZE, null);
        if (pictureSize == null) {
            CameraUtils.getInstance().initialCameraPictureSize(this, mCameraParameters);
        } else {
            List<Size> supported = mCameraParameters.getSupportedPictureSizes();
            CameraUtils.getInstance().setCameraPictureSize(
                    pictureSize, supported, mCameraParameters);
        }
        Size size = mCameraParameters.getPictureSize();

        // Set a preview size that is closest to the viewfinder height and has
        // the right aspect ratio.
        List<Size> sizes = mCameraParameters.getSupportedPreviewSizes();
        Size optimalSize = CameraUtils.getInstance().getOptimalPreviewSize(this, sizes,
                (double) size.width / size.height);
        Size original = mCameraParameters.getPreviewSize();
        if (!original.equals(optimalSize)) {
            mCameraParameters.setPreviewSize(optimalSize.width, optimalSize.height);

            // Zoom related settings will be changed for different preview
            // sizes, so set and read the parameters to get latest values
            mCameraDevice.setParameters(mCameraParameters);
            mCameraParameters = mCameraDevice.getParameters();
        }
        Log.v(TAG, "Preview size is " + optimalSize.width + "x" + optimalSize.height);

        // Since change scene mode may change supported values,
        // Set scene mode first,
        mSceneMode = mComboPref.getString(
                CameraSettings.KEY_SCENE_MODE,
                getString(R.string.pref_camera_scenemode_default));
        if (isSupported(mSceneMode, mCameraParameters.getSupportedSceneModes())) {
            if (!mCameraParameters.getSceneMode().equals(mSceneMode)) {
                mCameraParameters.setSceneMode(mSceneMode);

                // Setting scene mode will change the settings of flash mode,
                // white balance, and focus mode. Here we read back the
                // parameters, so we can know those settings.
                mCameraDevice.setParameters(mCameraParameters);
                mCameraParameters = mCameraDevice.getParameters();
            }
        } else {
            mSceneMode = mCameraParameters.getSceneMode();
            if (mSceneMode == null) {
                mSceneMode = Parameters.SCENE_MODE_AUTO;
            }
        }

        // Set JPEG quality.
        int jpegQuality = CameraProfile.getJpegEncodingQualityParameter(mCameraId,
                CameraProfile.QUALITY_HIGH);
        mCameraParameters.setJpegQuality(jpegQuality);

        // For the following settings, we need to check if the settings are
        // still supported by latest driver, if not, ignore the settings.

        // Set exposure compensation
        int value = CameraUtils.getInstance().readExposure(mComboPref);
        int max = mCameraParameters.getMaxExposureCompensation();
        int min = mCameraParameters.getMinExposureCompensation();
        if (value >= min && value <= max) {
            mCameraParameters.setExposureCompensation(value);
        } else {
            Log.w(TAG, "invalid exposure range: " + value);
        }

        if (Parameters.SCENE_MODE_AUTO.equals(mSceneMode)) {
            // Set flash mode.
            String flashMode = mComboPref.getString(
                    CameraSettings.KEY_FLASH_MODE,
                    getString(R.string.pref_camera_flashmode_default));
            List<String> supportedFlash = mCameraParameters.getSupportedFlashModes();
            if (isSupported(flashMode, supportedFlash)) {
                mCameraParameters.setFlashMode(flashMode);
            } else {
                flashMode = mCameraParameters.getFlashMode();
                if (flashMode == null) {
                    flashMode = getString(
                            R.string.pref_camera_flashmode_no_flash);
                }
            }

            // Set white balance parameter.
            String whiteBalance = mComboPref.getString(
                    CameraSettings.KEY_WHITE_BALANCE,
                    getString(R.string.pref_camera_whitebalance_default));
            if (isSupported(whiteBalance,
                    mCameraParameters.getSupportedWhiteBalance())) {
                mCameraParameters.setWhiteBalance(whiteBalance);
            } else {
                whiteBalance = mCameraParameters.getWhiteBalance();
                if (whiteBalance == null) {
                    whiteBalance = Parameters.WHITE_BALANCE_AUTO;
                }
            }

            // Set focus mode.
            mFocusManager.overrideFocusMode(null);
            mCameraParameters.setFocusMode(mFocusManager.getFocusMode());
        } else {
            mFocusManager.overrideFocusMode(mCameraParameters.getFocusMode());
        }

        if (mContinousFocusSupported) {
            if (mCameraParameters.getFocusMode().equals(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                mCameraDevice.setAutoFocusMoveCallback(mAutoFocusMoveCallback);
            } else {
                mCameraDevice.setAutoFocusMoveCallback(null);
            }
        }
    }
    
    private /*static*/ boolean isSupported(String value, List<String> supported) {
        return supported == null ? false : supported.indexOf(value) >= 0;
    }

}

package com.handroid.apps.yourstyle.widget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.ConditionVariable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreviewView extends SurfaceView implements SurfaceHolder.Callback {

    private /*static*/ final String TAG = "camera_review";

    public CameraPreviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    SurfaceHolder mHolder;
    Camera mCamera;
    Context mContext;

    public CameraPreviewView(Context context) {
        super(context);
        init(context);
    }
    
    private void init(Context context) {
        mContext = context;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        private ConditionVariable mSig = new ConditionVariable();
        public void onPictureTaken(byte[] imageData, Camera camera) {
            if (imageData != null) {
                storeByteImage(mContext, imageData, 100,
                        "ImageName");
                // start preview again
                mCamera.startPreview();
            }
        };
    };
    
    public /*static*/ boolean storeByteImage(Context mContext, byte[] imageData,
            int quality, String expName) {

        /*File sdImageMainDirectory = new File("/sdcard");
        FileOutputStream fileOutputStream = null;
        try {
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize = 5;
            
            Bitmap myImage = BitmapFactory.decodeByteArray(imageData, 0,
                    imageData.length,options);

            fileOutputStream = new FileOutputStream(
                    sdImageMainDirectory.toString() +"/image.jpg");
                            
            BufferedOutputStream bos = new BufferedOutputStream(
                    fileOutputStream);

            myImage.compress(CompressFormat.JPEG, quality, bos);

            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        String path = "/sdcard/image.jpg";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(path), false);
            out.write(imageData);
        } catch (Exception e) {
            Log.e(TAG, "Failed to write image", e);
            return false;
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    
    public void photoCapture() {
        mCamera.takePicture(null, mPictureCallback, mPictureCallback);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, acquire the camera and tell it where
        // to draw.
        mCamera = Camera.open();
        try {
           mCamera.setPreviewDisplay(holder);
           /*Parameters param = mCamera.getParameters();
           param.setRotation(180);
           mCamera.setParameters(param);*/
           
           mCamera.setDisplayOrientation(90);
           
           // support auto focus
//            mCamera.autoFocus(new Camera.AutoFocusCallback() {
//                Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
//                    public void onShutter() {
//                        // Play your sound here.
//                    }
//                };
//
//                public void onAutoFocus(boolean success, Camera camera) {
//                    camera.takePicture(shutterCallback, null, mPictureCallback);
//                }
//            });
        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
            // TODO: add more exception handling logic here
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
        // Because the CameraDevice object is not a shared resource, it's very
        // important to release it when the activity is paused.
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }


    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.05;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        Camera.Parameters parameters = mCamera.getParameters();

        List<Size> sizes = parameters.getSupportedPreviewSizes();
        Size optimalSize = getOptimalPreviewSize(sizes, w, h);
        parameters.setPreviewSize(optimalSize.width, optimalSize.height);

        mCamera.setParameters(parameters);
        mCamera.startPreview();
    }
    
    private class MyOrientationEventListener extends OrientationEventListener {
        public MyOrientationEventListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            /*// We keep the last known orientation. So if the user first orient
            // the camera then point the camera to floor or sky, we still have
            // the correct orientation.
            if (orientation == ORIENTATION_UNKNOWN)
                return;
            int newOrientation = Util.roundOrientation(orientation,
                    mOrientation);

            if (mOrientation != newOrientation) {
                mOrientation = newOrientation;
                // The input of effects recorder is affected by
                // android.hardware.Camera.setDisplayOrientation. Its value only
                // compensates the camera orientation (no Display.getRotation).
                // So the orientation hint here should only consider sensor
                // orientation.
                if (effectsActive()) {
                    mEffectsRecorder.setOrientationHint(mOrientation);
                }
            }

            // When the screen is unlocked, display rotation may change. Always
            // calculate the up-to-date orientationCompensation.
            int orientationCompensation = (mOrientation + Util
                    .getDisplayRotation(VideoCamera.this)) % 360;

            if (mOrientationCompensation != orientationCompensation) {
                mOrientationCompensation = orientationCompensation;
                // Do not rotate the icons during recording because the video
                // orientation is fixed after recording.
                if (!mMediaRecorderRecording) {
                    setOrientationIndicator(mOrientationCompensation, true);
                }
            }

            // Show the toast after getting the first orientation changed.
            if (mHandler.hasMessages(SHOW_TAP_TO_SNAPSHOT_TOAST)) {
                mHandler.removeMessages(SHOW_TAP_TO_SNAPSHOT_TOAST);
                showTapToSnapshotToast();
            }*/
        }
    }

}
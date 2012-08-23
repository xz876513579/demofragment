package com.android.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

public class CameraUtils {
    
    private /*static*/ final String TAG = "CameraUtils";
    private /*static*/ final String JPEG_FILE_PREFIX = "IMG_";
    private /*static*/ final String JPEG_FILE_SUFFIX = ".jpg";
    
    // Standard storage location for digital camera files
    private /*static*/ final String CAMERA_DIR = "/dcim/";
    
    private File getAlbumStorageDir(String albumName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            return new File(
                    Environment.getExternalStoragePublicDirectory(
                      Environment.DIRECTORY_PICTURES
                    ), 
                    albumName
                  );
        } else {
            return new File (
                    Environment.getExternalStorageDirectory()
                    + CAMERA_DIR
                    + albumName
            );
        }
    }
    
    private File getAlbumDir(String albumName) {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            
            storageDir = getAlbumStorageDir(albumName);

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }
            
        } else {
            Log.v(TAG, "External storage is not mounted READ/WRITE.");
        }
        
        return storageDir;
    }
    
    private File createImageFile(String albumName) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir(albumName);
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

}

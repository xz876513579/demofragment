package com.handroid.apps.yourstyle;

import java.io.File;
import java.lang.reflect.Field;

import org.jiggawatt.giffle.Giffle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        
//        boolean isWifiConnected = NetworkUtils.getInstance().isWifiConnected(getApplicationContext());
//        Toast.makeText(getApplicationContext(), "is wifi connected = " + isWifiConnected, 0).show();
        
        int width = 360;
        int height = 600;
        String gifFilePath = "/mnt/sdcard/fog.gif";
        int numOfColor = 256;
        int frameDelay = 150;
        boolean isGifEncoderWorking = Giffle.getInstance().Init(gifFilePath,
                width, height, numOfColor, 100, frameDelay) == 0;
        
        String path = "/mnt/sdcard/TestGif";
        File imageFilesPath = new File(path);
        int[] pixels = new int[width*height];
        if (imageFilesPath.exists() && isGifEncoderWorking) {
        	for (String imageFile : imageFilesPath.list()) {
        		Bitmap bitmap = BitmapFactory.decodeFile(path + "/" + imageFile);
        		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        		Giffle.getInstance().AddFrame(pixels);
        		bitmap.recycle();
        		bitmap = null;
        	}
        	Giffle.getInstance().Close();
        	Log.d("mapp", ">>> encode gif file DONE");
        } else {
        	if (!isGifEncoderWorking) {
        		Log.e("mapp", "Init gif encoder failed");
        	} else {
        		Log.e("mapp", "Cannot file image path!");
        	}
        }
        
        
//        // Filename, width, height, colors, quality, frame delay
//        if (isGifEncoderWorking) {
//            Log.d("mapp", "Init gif encoder OK");
//            
//            int[] pixels = new int[width*height];
//            // bitmap should be 32-bit ARGB, e.g. like the ones you get when decoding 
//            // a JPEG using BitmapFactory
//            
//            for (int i = 1; i < 7; i ++) {
//                int resId = getResId("troll_00" + i, getApplicationContext(), Drawable.class);;
//                if (resId != -1) {
//                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
//                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
//                    // Convert to 256 colors and add to foo.gif
//                    Giffle.getInstance().AddFrame(pixels);
//                    bitmap.recycle();
//                    bitmap = null;
//                }
//            }
//            File imagesPath = new File("/mnt/sdcard/TestGif");
//    
//            Giffle.getInstance().Close();
//            Log.d("mapp", ">>> encode gif file DONE");
//        } else {
//            Log.e("mapp", "Init gif encoder failed");
//        }
    }
    
    public int getResId(String variableName, Context context, Class<?> c) {
        try {
            // Class<drawable> res = R.drawable.class;
            Field field = c.getField(variableName);
            return field.getInt(null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_layout, menu);
        return true;
    }

    
}

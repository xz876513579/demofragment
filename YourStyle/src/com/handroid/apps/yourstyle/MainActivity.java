package com.handroid.apps.yourstyle;

import org.jiggawatt.giffle.Giffle;

import com.android.utils.NetworkUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        
        boolean isWifiConnected = NetworkUtils.getInstance().isWifiConnected(getApplicationContext());
        Toast.makeText(getApplicationContext(), "is wifi connected = " + isWifiConnected, 0).show();
        
        int width = 25;
        int height = 25;
        // Filename, width, height, colors, quality, frame delay
        if (Giffle.sInstance().Init("/mnt/sdcard/foo.gif", width, height, 256, 100, 4) != 0) {
            Log.e("mapp", "Init failed");
        } else {
            Log.d("mapp", "Init OK");
        }
        
        int[] pixels = new int[width*height];
        // bitmap should be 32-bit ARGB, e.g. like the ones you get when decoding 
        // a JPEG using BitmapFactory
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_search);
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        // Convert to 256 colors and add to foo.gif
        Giffle.sInstance().AddFrame(pixels);

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        bitmap1.getPixels(pixels, 0, width, 0, 0, width, height);
        // Convert to 256 colors and add to foo.gif
        Giffle.sInstance().AddFrame(pixels);

        
        Giffle.sInstance().Close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_layout, menu);
        return true;
    }

    
}

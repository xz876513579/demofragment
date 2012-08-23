package com.android.utils;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Ensure to add uses-permission to your manifest for accessing network state <br><b> 
 * android.permission.ACCESS_NETWORK_STATE
 */
public class NetworkUtils {
    
    private static WeakReference<NetworkUtils> mNetworkUtils;
    
    public static NetworkUtils getInstance() throws NullPointerException{
        if (mNetworkUtils == null) {
            mNetworkUtils = new WeakReference<NetworkUtils>(new NetworkUtils());
        }
        return mNetworkUtils.get();
    }
    
    private NetworkInfo getNetworkInfo(Context context) {
        if (context == null) {
            return null;
        }
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
         
        return cm.getActiveNetworkInfo();
    }
    
    public boolean isNetworkConnectedOrConnecting(Context context) {
        if (context == null) { 
            return false;
        }
        return getNetworkInfo(context).isConnectedOrConnecting();
    }
    
    public boolean isWifiConnected(Context context) {
        if (context == null) { 
            return false;
        }
        return getNetworkInfo(context).getType() == ConnectivityManager.TYPE_WIFI;
    }
}

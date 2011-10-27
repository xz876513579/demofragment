package com.handroid.demofragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class DemoFragmentActivity extends FragmentActivity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fragment_layout);
    }
}
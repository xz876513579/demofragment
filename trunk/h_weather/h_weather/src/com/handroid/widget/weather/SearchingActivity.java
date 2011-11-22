package com.handroid.widget.weather;

import android.app.Activity;
import android.os.Bundle;

public class SearchingActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final String querryStr = getString(R.string.feature_searching_api, "");
	}
}

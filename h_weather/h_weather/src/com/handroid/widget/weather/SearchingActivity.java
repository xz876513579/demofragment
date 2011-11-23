package com.handroid.widget.weather;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SearchingActivity extends Activity{
	
	EditText edtSearchStr;
	Button btnSearchButton;
	ListView listResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searching_layout);
		
		edtSearchStr = (EditText) findViewById(R.id.edt_search_querry_string);
		btnSearchButton = (Button) findViewById(R.id.btn_search_querry_search);
		btnSearchButton.setOnClickListener(mItemOnClickListener);

		listResult = (ListView) findViewById(R.id.lv_search_list_result);
	}
	
	OnClickListener mItemOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_search_querry_search:
				final String querryStr = getString(
						R.string.feature_searching_api, edtSearchStr
								.getEditableText().toString());
				BaseApplication.makeToastMsg(querryStr);
				break;
			}
		}
	};
	
}

package com.handroid.widget.weather;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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
		
		// Get a cursor with all people
        /*Cursor c = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
        startManagingCursor(c);

        ListAdapter adapter = new SimpleCursorAdapter(this, 
                // Use a template that displays a text view
                android.R.layout.simple_list_item_1, 
                // Give the cursor to the list adatper
                c, 
                // Map the NAME column in the people database to...
                new String[] {People.NAME} ,
                // The "text1" view defined in the XML template
                new int[] {android.R.id.text1}); 
        listResult.setAdapter(adapter);*/
	}
	
	@Override
	protected void onResume() {
		super.onResume();
        // Create a calendar object and set it time based on the local time zone
        Calendar localTime = Calendar.getInstance();
        /*localTime.set(Calendar.HOUR, hour);
        localTime.set(Calendar.MINUTE, min);
        localTime.set(Calendar.SECOND, sec);*/

        // Create a calendar object for representing a Germany time zone. Then we
        // wet the time of the calendar with the value of the local time
        Calendar desTime = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
        desTime.setTimeInMillis(localTime.getTimeInMillis());

        BaseApplication.makeToastMsg("London time: " + desTime.get(Calendar.HOUR) + ":" + desTime.get(Calendar.MINUTE) + ":" + desTime.get(Calendar.SECOND));
	};
	
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

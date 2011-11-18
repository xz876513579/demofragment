package com.handroid.widget.weather;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

public abstract class BaseActivity extends Activity{
	abstract protected HashMap<String, String> loadJSON(JSONObject json)
			throws JSONException;
}

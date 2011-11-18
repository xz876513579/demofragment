package com.handroid.widget.weather.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handroid.widget.weather.BaseApplication;

public class WeatherAPI {

	public static String getContent(InputStream is) throws IOException {
		StringBuilder builder = new StringBuilder();
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ((line = buffer.readLine()) != null) {
			/*try {
				JSONArray ja = new JSONArray(line);
				for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    // listItems.add(jo.getString("text"));
                    jo.get("");
                }
			} catch (JSONException e) {
				e.printStackTrace();
			}*/
			builder.append(line);
		}
		buffer.close();
		return builder.toString().trim();
	}
	
}

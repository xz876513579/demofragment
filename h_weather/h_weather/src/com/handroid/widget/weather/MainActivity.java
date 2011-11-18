package com.handroid.widget.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;

import com.handroid.widget.weather.utils.WeatherAPI;

public class MainActivity extends BaseActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String url = "http://api.wunderground.com/api/05fa72c82bc431ef/forecast/q/zmw:00000.1.03323.json";
        loadUrl(url);
    }
    
    private boolean loadUrl(String url) {
    	if (TextUtils.isEmpty(url))
    		return false;
    	
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				String jsonRaw = WeatherAPI.getContent(content);
				JSONObject jsonObject = new JSONObject(jsonRaw);
				JSONArray collection = new JSONArray(((JSONObject)((JSONObject)jsonObject.get("forecast")).get("txt_forecast")).get("forecastday").toString());
				//JSONArray collection = new JSONArray(jsonRaw);
				for (int i = 0; i < collection.length(); i++) {
					try {
						this.loadJSON(collection.getJSONObject(i));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
				return true;
			} else {
				// Log.e(ParseJSON.class.toString(), "Failed to download file");
				return false;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	/*try {
			URL request = new URL(url);
			String jsonRaw = WeatherAPI.getContent((InputStream) request.getContent());
			JSONObject jsonObject = new JSONObject(jsonRaw);
			JSONArray collection = new JSONArray(((JSONObject)((JSONObject)jsonObject.get("forecast")).get("txt_forecast")).get("forecastday").toString());
			//JSONArray collection = new JSONArray(jsonRaw);
			for (int i = 0; i < collection.length(); i++) {
				try {
					this.loadJSON(collection.getJSONObject(i));
				} catch (JSONException e) {
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}*/
    	
    	return false;
    }

	@Override
	protected HashMap<String, String> loadJSON(JSONObject json)
			throws JSONException {
		HashMap<String, String> albumInfo = new HashMap<String, String>();
		
		json.get("period");
		json.get("icon_url");
		json.get("title");
		json.get("fcttext");
		json.get("fcttext_metric");
		json.get("pop");
		
		/*albumInfo.put(Album.ID, albumObject.getString("pk"));

		albumObject = albumObject.getJSONObject("fields");
		String album = albumObject.getString("title");
		albumInfo.put(Album.TITLE, album);
		albumInfo.put(Artist.ID, albumObject.getString("artist"));
		String artist = albumObject.getString("artist_text");
		albumInfo.put(Album.ARTIST, artist);
		albumInfo.put(Album.SKU, albumObject.getString("sku"));
		albumInfo.put(Album.GENRE, albumObject.getString("genre_text"));
		albumInfo.put(Album.ARTWORK, MagnatuneAPI.getCoverArtUrl(artist, album, 50));*/
		return albumInfo;
	}
    
}
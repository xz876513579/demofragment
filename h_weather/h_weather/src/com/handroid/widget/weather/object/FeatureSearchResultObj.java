package com.handroid.widget.weather.object;

/**
 * http://autocomplete.wunderground.com/aq?query=liverpool&format=json<br>
 * <br>The querry result like:<br>
 * { "name": "Liverpool, United Kingdom", "type": "city", "c": "GB", 
 * "zmw": "00000.1.03323", "tz": "Europe/London", "tzs": "GMT", "l": "/q/zmw:00000.1.03323" }
 * @author NhatVT
 */
public class FeatureSearchResultObj {

	public static final String NAME		= "name";
	public static final String TYPE		= "type";
	public static final String C		= "c";
	public static final String ZMW		= "zmw";
	public static final String TZ		= "tz";
	public static final String TZS		= "tzs";
	public static final String L		= "l";
	
	private String mName;
	private String zmw;
	public void setSearchingName(String mName) {
		this.mName = mName;
	}
	public String getSearchingName() {
		return mName;
	}
	public void setZmw(String zmw) {
		this.zmw = zmw;
	}
	public String getZmw() {
		return zmw;
	}
}

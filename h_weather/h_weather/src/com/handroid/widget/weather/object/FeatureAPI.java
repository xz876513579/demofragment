package com.handroid.widget.weather.object;

public class FeatureAPI {
	
	// ****************************
	// Request URL format
	// ****************************
	// http://api.wunderground.com/api/KEY/FEATURE/[FEATURE…]/q/QUERY.FORMAT
	//	Definitions:
	//
	//		KEY
	//		    your unique API key
	//		FEATURE
	//		    --------------------one of the following feature_id's-------------------------
	//		    feature_id 	description
	//		    geolookup 		Returns the the city name, zip code / postal code, latitude-longitude coordinates and nearby personal weather stations.
	//		    conditions 		Returns the current temperature, weather condition, humidity, wind, 'feels like' temperature, barometric pressure, and visibility.
	//		    forecast 		Returns a summary of the weather for the next 3 days. This includes high and low temperatures, a string text forecast and the conditions.
	//		    astronomy 		Returns The moon phase, sunrise and sunset times.
	//		    radar 			Returns a URL link to the .gif radar image.
	//		    satellite 		Returns a URL link to .gif visual and infrared satellite images.
	//		    webcams 		Returns locations of nearby Personal Weather Stations and URL's for images from their web cams.
	//		    history 		history_YYYYMMDD returns a summary of the observed weather for the specified date.
	//		    alerts 			Returns the short name description, expiration time and a long text description of a severe alert - If one has been issued for the searched upon location.
	//		    hourly 			Returns an hourly forecast for the next 36 hours immediately following the API request.
	//		    hourly7day 		Returns an hourly forecast for the next 7 days
	//		    forecast7day 	Returns a summary of the weather for the next 7 days. This includes high and low temperatures, a string text forecast and the conditions.
	//		    yesterday 		Returns a summary of the observed weather history for yesterday.
	//		    planner 		planner_MMDDMMDD returns a weather summary based on historical information between the specified dates (30 days max).
	//		    autocomplete 	-
	//		    almanac 		Historical average temperature for today
	//		FORMAT
	//		    output format
	//		        json:  	for jsonp, you may add ?callback=CALLBACK to the request URL
	//		        xml:	XML documentation coming soon.
	//
	//		QUERY
	//		    the location for which you want weather information. Examples:
	//		        CA/San_Francisco
	//		        60290 (U.S. zip code)
	//		        Australia/Sydney
	//		        37.8,-122.4 (latitude,longitude)
	//		        KJFK (airport code)
	//		        pws:KCASANFR70 (PWS id)
	//		        autoip (AutoIP address location)
	//		        autoip.json?geo_ip=38.102.136.138 (Specific IP address location)

	// EXAM
	// ------------querry location string:
	// http://autocomplete.wunderground.com/aq?query=liverpool&format=xml
	// ------------get condtions:
	// http://api.wunderground.com/api/05fa72c82bc431ef/conditions/q/zmw:00000.1.03323.json
	
	
	// ****************************
	// Current Condition Phrases
	// ****************************
	//	The following conditions are Light, Heavy, or normal which has no classifier.
	//    [Light/Heavy] Drizzle
	//    [Light/Heavy] Rain
	//    [Light/Heavy] Snow
	//    [Light/Heavy] Snow Grains
	//    [Light/Heavy] Ice Crystals
	//    [Light/Heavy] Ice Pellets
	//    [Light/Heavy] Hail
	//    [Light/Heavy] Mist
	//    [Light/Heavy] Fog
	//    [Light/Heavy] Smoke
	//    [Light/Heavy] Volcanic Ash
	//    [Light/Heavy] Widespread Dust
	//    [Light/Heavy] Sand
	//    [Light/Heavy] Haze
	//    [Light/Heavy] Spray
	//    [Light/Heavy] Dust Whirls
	//    [Light/Heavy] Sandstorm
	//    [Light/Heavy] Low Drifting Snow
	//    [Light/Heavy] Low Drifting Widespread Dust
	//    [Light/Heavy] Low Drifting Sand
	//    [Light/Heavy] Blowing Snow
	//    [Light/Heavy] Blowing Widespread Dust
	//    [Light/Heavy] Blowing Sand
	//    [Light/Heavy] Rain Mist
	//    [Light/Heavy] Rain Showers
	//    [Light/Heavy] Snow Showers
	//    [Light/Heavy] Ice Pellet Showers
	//    [Light/Heavy] Hail Showers
	//    [Light/Heavy] Small Hail Showers
	//    [Light/Heavy] Thunderstorm
	//    [Light/Heavy] Thunderstorms and Rain
	//    [Light/Heavy] Thunderstorms and Snow
	//    [Light/Heavy] Thunderstorms and Ice Pellets
	//    [Light/Heavy] Thunderstorms with Hail
	//    [Light/Heavy] Thunderstorms with Small Hail
	//    [Light/Heavy] Freezing Drizzle
	//    [Light/Heavy] Freezing Rain
	//    [Light/Heavy] Freezing Fog
	//
	//Overcast
	//Clear
	//Partly Cloudy
	//Mostly Cloudy
	//Scattered Clouds
	
	// ****************************
	// Field Descriptions & Phrases
	// ****************************
	//		Note that values will = -9999 for Null or Non applicable variables
	//	[tempm] => Temp in C
	//	[tempi] => Temp in F
	//	[dewptm] =>Dewpoint in C
	//	[dewpti] => Duepoint in F
	//	[hum] => Humidity %
	//	[wspdm] => WindSpeed kph
	//	[wspdi] => Windspeed in mph
	//	[wgustm] => Wind gust in kph
	//	[wgusti] => Wind gust in mph
	//	[wdird] => Wind direction in degrees
	//	[wdire] => Wind direction description (ie, SW, NNE)
	//	[vism] => Visibility in Km
	//	[visi] => Visability in Miles
	//	[pressurem] => Pressure in mBar
	//	[pressurei] => Pressure in inHg
	//	[windchillm] => Wind chill in C
	//	[windchilli] => Wind chill in F
	//	[heatindexm] => Heat index C
	//	[heatindexi] => Heat Index F
	//	[precipm] => Precipitation in mm
	//	[precipi] => Precipitation in inches
	//	[pop] => Probability of Precipitation
	//	[conds] => See possible condition phrases below
	
}

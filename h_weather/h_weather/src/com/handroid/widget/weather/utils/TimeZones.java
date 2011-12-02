package com.handroid.widget.weather.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeZones {

	public void testTimeZone(int hour, int min, int sec) {
        // Create a calendar object and set it time based on the local time zone
        Calendar localTime = Calendar.getInstance();
        /*localTime.set(Calendar.HOUR, hour);
        localTime.set(Calendar.MINUTE, min);
        localTime.set(Calendar.SECOND, sec);*/

        // Create a calendar object for representing a Germany time zone. Then we
        // wet the time of the calendar with the value of the local time
        Calendar desTime = new GregorianCalendar(TimeZone.getTimeZone("Europe/London"));
        desTime.setTimeInMillis(localTime.getTimeInMillis());

        System.out.printf("Germany time: %02d:%02d:%02d\n", desTime.get(Calendar.HOUR), desTime.get(Calendar.MINUTE), desTime.get(Calendar.SECOND));
	}
	
}

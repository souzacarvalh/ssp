package org.ffsc.ssp.web.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class TimeUtils {

	public static String getDateAsString(){
		return new DateTime().toString(DateTimeFormat.forPattern("dd/MM/YYYY"));
	}
	
	public static String getDateAsString(Date date){
		return new DateTime(date.getTime()).toString(DateTimeFormat.forPattern("dd/MM/YYYY"));
	}
}
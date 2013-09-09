package org.cc.mvel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Collection;

public class MVEL {

	public static String fmt1 = "yyyy/MM/dd HH:ss";

	public static String to_date(Object d) {
		return to_date(fmt1, d);
	}

	public static String to_date(String fmt, Object d) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		d = (d != null) ? d : new Date();
		return sdf.format(d);
	}

	public static Object safe(Object value, Object dv) {
		return (value != null) ? value : dv;
	}

	public static Object safe(Object value) {
		return (value != null) ? value : "";
	}

	public static Object substring(Object value, int len) {
		if (value != null) {
			String v = value.toString();
			if (v.length() <= len) {
				return v;
			} else {
				return v.substring(0, len);
			}
		}
		return "";
	}

	public static Object substring(Object value, int len, String tail) {
		if (value != null) {
			String v = value.toString();
			if (v.length() <= len) {
				return v;
			} else {
				return v.substring(0, len) + tail;
			}
		}
		return "";
	}

	public static int length(Object value) {
		if (value instanceof String) {
			return ((String) value).trim().length();
		} else if (value instanceof Collection) {
			return ((Collection) value).size();
		}
		return 0;
	}
	
	public static boolean startWith(Object value, String text){
		if(value instanceof String){
			return ((String) value).startsWith(text);
		}
		return false ; 
	}

	public static String fmt(Object value, String format){
		if(value == null){
			return "";
		}else if(value.getClass() == Date.class){
			return new SimpleDateFormat(format).format((Date)value);
		}else if(value.getClass() == Integer.class){
			return new DecimalFormat(format).format((Integer)value);
		}else if(value.getClass() == Long.class){
			return new DecimalFormat(format).format((Long)value);
		}else{
			return new DecimalFormat(format).format(Integer.parseInt(value.toString(), 10));
		}
	}
}

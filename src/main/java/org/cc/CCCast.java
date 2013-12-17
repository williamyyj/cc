/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.cc.ICC;
import org.cc.type.ICCType;
import org.cc.type.CCBoolType;
import org.cc.type.CCDateType;
import org.cc.type.CCDoubleType;
import org.cc.type.CCIntType;
import org.cc.type.CCLongType;
import org.cc.type.CCStringType;

public class CCCast {

	protected ICCType<Boolean> dt_bool = new CCBoolType();
	protected ICCType<Integer> dt_int = new CCIntType();
	protected ICCType<Long> dt_long = new CCLongType();
	protected ICCType<Double> dt_double = new CCDoubleType();
	protected CCDateType dt_date = new CCDateType();
	protected ICCType<String> dt_string = new CCStringType();

	public boolean asBool(Object o, boolean dv) {
		return dt_bool.value(o, dv);
	}

	public int asInt(Object o, int dv) {
		return dt_int.value(o, dv);
	}

	public long asLong(Object o, long dv) {
		return dt_long.value(o, dv);
	}

	public double asDouble(Object o, double dv) {
		return dt_double.value(o, dv);
	}

	public Object convert(Class<?> cls, Object o) {
		if (cls.equals(Boolean.class) || cls.equals(boolean.class)) {
			return asBool(o, false);
		} else if (cls.equals(Integer.class) || cls.equals(int.class)) {
			return asInt(o, 0);
		} else if (cls.equals(Long.class) || cls.equals(long.class)) {
			return asLong(o, 0L);
		} else if (cls.equals(Double.class) || cls.equals(double.class)) {
			return asDouble(o, 0.0);
		} else if (cls.equals(Date.class)) {
			return asDate(o, (Date) null);
		} else if (cls.equals(String.class)) {
			return asString(o, null);
		}
		throw new RuntimeException("Can't convert " + o + " to " + cls);
	}

	public Object convert(String dt, Object o) {
		if (ICCType.dt_bool.equals(dt)) {
			return asBool(o, false);
		} else if (ICCType.dt_int.equals(dt)) {
			return asInt(o, 0);
		} else if (ICCType.dt_long.equals(dt)) {
			return asLong(o, 0L);
		} else if (ICCType.dt_double.equals(dt)) {
			return asDouble(o, 0.0);
		} else if (ICCType.dt_date.equals(dt)) {
			return asDate(o, (Date) null);
		} else if (ICCType.dt_string.equals(dt)) {
			return asString(o, null);
		}
		throw new RuntimeException("Can't type " + dt + " : " + o);
	}

	public Date asDate(Object o, Date d) {
		return dt_date.value(o, d);
	}

	public Date asDate(Object o, String fmt) {
		return dt_date.check(o, fmt);
	}

	public String asString(Object o, String dv) {
		return dt_string.value(o, dv);
	}

	public Class<?> nativeClass(Object o) {
		return (o != null) ? o.getClass() : null;
	}

	public String quote(String string) {
		if (string == null || string.length() == 0) {
			return "\"\"";
		}

		char b;
		char c = 0;
		String hhhh;
		int i;
		int len = string.length();
		StringBuilder sb = new StringBuilder(len + 4);
		sb.append('"');
		for (i = 0; i < len; i += 1) {
			b = c;
			c = string.charAt(i);
			switch (c) {
				case '\\':
				case '"':
					sb.append('\\');
					sb.append(c);
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\r':
					sb.append("\\r");
					break;
				default:
					if (c < ' ' || (c >= '\u0080' && c < '\u00a0')
							|| (c >= '\u2000' && c < '\u2100')) {
						hhhh = "000" + Integer.toHexString(c);
						sb.append("\\u").append(hhhh.substring(hhhh.length() - 4));
					} else {
						sb.append(c);
					}
			}
		}
		sb.append('"');
		return sb.toString();
	}

	public String valueToString(Object value) {

		if (value == null || value.equals("null")) {
			return "null";
		}

		if (value instanceof Number) {
			return numberToString((Number) value);
		}

		if (value instanceof Boolean) {
			return value.toString();
		}

		if (value instanceof Date) {
			return "$date@" + asDate(value, ICCType.fmt_datetime);
		}

		if (value instanceof ICC) {
			return ((ICC) value).toString();
		}


		if (value instanceof Map) {
			return new CCMap((Map) value).toString();
		}

		if (value instanceof Collection) {
			return new CCList((Collection) value).toString();
		}

		if (value.getClass().isArray()) {
			return new CCList(value).toString();
		}

		return quote(value.toString());

	}
	
	public String valueToString(Object value, String base, String indent) {

		if (value == null || value.equals("null")) {
			return "null";
		}

		if (value instanceof Number) {
			return numberToString((Number) value);
		}

		if (value instanceof Boolean) {
			return value.toString();
		}

		if (value instanceof Date) {
			return "$date@" + asDate(value, ICCType.fmt_datetime);
		}

		if (value instanceof ICC) {
			return ((ICC) value).toString(base,indent);
		}


		if (value instanceof Map) {
			return new CCMap((Map) value).toString(base,indent);
		}

		if (value instanceof Collection) {
			return new CCList((Collection) value).toString(base,indent);
		}

		if (value.getClass().isArray()) {
			return new CCList(value).toString(base,indent);
		}

		return quote(value.toString());

	}
	
	

	public String numberToString(Number number) {
		if (number == null) {
			throw new RuntimeException("Null pointer");
		}

		String string = number.toString();
		if (string.indexOf('.') > 0 && string.indexOf('e') < 0
				&& string.indexOf('E') < 0) {
			while (string.endsWith("0")) {
				string = string.substring(0, string.length() - 1);
			}
			if (string.endsWith(".")) {
				string = string.substring(0, string.length() - 1);
			}
		}
		return string;
	}

	public String to_alias(String fid) {
		char[] buf = fid.toLowerCase().toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : buf) {
			if (c == '.' || c == '-' || c == '_') {
				continue;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	public String to_short(String fid) {
		String text1 = fid.toLowerCase();
		String[] buf = text1.split("[\\.\\-\\_]");
		StringBuilder sb = new StringBuilder();
		if (buf.length == 1) {
			sb.append(buf[0]);
		} else {
			for (int i = 1; i < buf.length; i++) {
				sb.append(buf[i]);
			}
		}
		return sb.toString();
	}

	public void indent(StringBuilder sb, String indent, String value) {
		if (indent != null) {
			sb.append(indent).append(value);
		} else {
			sb.append(value);
		}
	}
}
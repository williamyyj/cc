package org.cc;


import org.cc.util.CCCache;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cc.ICC;
import org.cc.ICCMap;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/2
 * Time: 上午 11:26
 * To change this template use File | Settings | File Templates.
 */
public class CC {

    private static Pattern p = Pattern.compile("\\$\\{([^\\}]+)\\}");

    public static <E> int asInt(ICC<E> cc , E id) {
        return asInt(cc,id,0);
    }

    public static <E> int asInt(ICC<E> cc , E id , int dv) {
        Object o = cc.obj(id);
        return (o instanceof Number) ? ((Number) o).intValue() : dv ;
    }
	
	    public static <E> long asLong(ICC<E> cc , E id) {
        return asLong(cc,id,0L);
    }

    public static <E> long asLong(ICC<E> cc , E id , long dv) {
        Object o = cc.obj(id);
        return (o instanceof Number) ? ((Number) o).longValue() : dv ;
    }

	
	public static <E> double asDouble(ICC<E> cc , E id) {
        return asDouble(cc,id,0.0);
    }

    public static <E> double asDouble(ICC<E> cc , E id , double dv) {
        Object o = cc.obj(id);
        return (o instanceof Number) ? ((Number) o).doubleValue() : dv ;
    }


    public static <E> String str(ICC<E> cc , E id ) {
        return str(cc,id,"");
    }

    public static <E> String str(ICC<E> cc , E id , String dv) {
        Object o = cc.obj(id);
        return (o != null) ? o.toString() : dv ;
    }

    public static <E> boolean bool(ICC<E> cc , E id ) {
        return bool(cc,id,false);
    }

    public static <E> boolean bool(ICC<E> cc , E id , boolean dv) {
        Object o = cc.obj(id);
        return (o instanceof Boolean) ? (Boolean) o : dv ;
    }

    public static <E> Date date(ICC<E> cc , E id ) {
        return date(cc,id,new Date());
    }

    public static <E> Date date(ICC<E> cc , E id , Date dv) {
        Object o = cc.obj(id);
        return (o instanceof Date) ? (Date) o : dv ;
    }

    public static String quote(String string) {
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

    public static String json(Object value) {

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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            return "$date@" + sdf.format(value);
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

    public static String json(Object value, String base, String indent) {

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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            return "$date@" + sdf.format(value);
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


    public static String numberToString(Number number) {
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

    public static ICCMap load(File f , String enc){
        return CCCache.load_map(f,enc);
    }

    public static void save(File f, String enc, ICCMap m) throws IOException {
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(new FileOutputStream(f), enc);
            osw.write(m.toString("\t"));
            osw.flush();
        } finally {
            if (osw != null) {
                osw.close();
            }
        }
    }

    public static String to_alias(String fid) {
        char[] buf = fid.toLowerCase().toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : buf) {
            if (c == '.' || c == '-' || c == '_') {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String to_short(String fid) {
        char[] buf = fid.toLowerCase().toCharArray();
        StringBuilder sb = new StringBuilder();
		int idx = 1 ; 
        for (char c : buf) {
            if (c == '.' && idx==1 ) {
				sb.setLength(0);
				continue;
            } else if (c == '.' || c == '-' || c == '_') {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }


    public static String ref(ICCMap mapping, String text, String dv) {
        if(text!=null){
            StringBuffer sb = new StringBuffer();
            Matcher m = p.matcher(text);
            while (m.find()) {
                String re = mapping.str(m.group(1));
                re = (re == null) ? "" : re;
                re = m.quoteReplacement(re);  //
                m.appendReplacement(sb,re);
            }
            m.appendTail(sb);
            return sb.toString();
        }
        return dv;
    }


    public static void indent(StringBuilder sb, String indent, String value) {
        if (indent != null) {
            sb.append(indent).append(value);
        } else {
            sb.append(value);
        }
    }



}

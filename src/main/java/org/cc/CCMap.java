package org.cc;

import org.cc.CC;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cc.ICC;
import org.cc.ICCList;
import org.cc.ICCMap;

/**
 * Created with IntelliJ IDEA. User: william Date: 2013/7/2 Time: 上午 11:27 To change this template use File | Settings | File
 * Templates.
 */
public class CCMap extends HashMap<String, Object> implements ICCMap {

	protected boolean isIndent = true;
	protected static String pattern = "\\$\\{([^\\{\\}]+)\\}";
	protected Pattern pp = Pattern.compile(pattern);

	public CCMap() {
	}

	public CCMap(boolean isIndent) {
		this.isIndent = isIndent;
		//this.put("$indent",isIndent);
	}

	public CCMap(Map m) {
		this.putAll(m);
	}

	@Override
	public Object[] toArray(String... names) {
		if (names != null) {
			Object[] ret = new Object[names.length];
			int idx = 0;
			for (String name : names) {
				ret[idx] = get(name);
			}
			return ret;
		}
		return new Object[]{};
	}

	@Override
	public String str(String id) {
		return CC.str(this, id);
	}

	@Override
	public String str(String id, String dv) {
		return CC.str(this, id, dv);
	}

	@Override
	public Date date(String id) {
		return CC.date(this, id);
	}

	@Override
	public Date date(String id, Date dv) {
		return CC.date(this, id, dv);
	}

	@Override
	public boolean bool(String id) {
		return CC.bool(this, id);
	}

	@Override
	public boolean bool(String id, boolean dv) {
		return CC.bool(this, id, dv);
	}

	@Override
	public Object obj(String id) {
		return get(id);
	}

	@Override
	public ICCList list(String id) {
		Object o = get(id);
		return (o instanceof ICCList) ? (ICCList) o : null;
	}

	@Override
	public ICCMap map(String id) {
		Object o = get(id);
		return (o instanceof ICCMap) ? (ICCMap) o : null;
	}

	@Override
	public int len() {
		return size();
	}

	@Override
	public boolean has(String id) {
		return this.containsKey(id);
	}

	@Override
	public Object set(String id, Object value) {
		return put(id, value);
	}

	@Override
	public void del(String id) {
		remove(id);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		Set<Map.Entry<String, Object>> es = this.entrySet();
		for (Map.Entry<String, Object> e : es) {
			sb.append('"').append(e.getKey()).append('"').append(':').append(CC.json(e.getValue())).append(',');
		}
		if (es.size() > 0) {
			sb.setLength(sb.length() - 1);
		}
		sb.append('}');
		return sb.toString();
	}

	@Override
	public String toString(String indent) {
		return toString(null, indent);
	}

	public String toString(String base, String indent) {
		if (!isIndent) {
			return toString();
		}
		StringBuilder sb = new StringBuilder();
		String next = (base != null) ? base + indent : indent;
		sb.append("{\n");
		Set<Map.Entry<String, Object>> es = this.entrySet();
		for (Map.Entry<String, Object> e : es) {
			CC.indent(sb, next, CC.json(e.getKey()));
			sb.append(" : ").append(CC.json(e.getValue(), next, indent));
			sb.append(",\n");
		}
		if (len() > 0) {
			sb.setLength(sb.length() - 2);
		}
		sb.append('\n');
		CC.indent(sb, base, "}");
		return sb.toString();
	}

	@Override
	public void setIsIndent(boolean isIndent) {
		this.isIndent = isIndent;
	}

	public int toI(String id) {
		return CC.asInt(this, id);
	}

	public int toI(String id, int dv) {
		return CC.asInt(this, id, dv);
	}

	public long toL(String id) {
		return CC.asLong(this, id);
	}

	public long toL(String id, int dv) {
		return CC.asLong(this, id, dv);
	}

	public double toF(String id) {
		return CC.asDouble(this, id);
	}

	public double toF(String id, int dv) {
		return CC.asDouble(this, id, dv);
	}

	public Map<String, Object> model() {
		return this;
	}

	public void disable_indent(String id) {
		ICCList list = list(id);
		if (list != null) {
			int len = list.len();
			for (int i = 0; i < len; i++) {
				Object o = list.obj(i);
				if (o instanceof ICC) {
					((ICC) o).setIsIndent(false);
				}
			}
		}
	}

	public Set<String> names() {
		return this.keySet();
	}

	/**
	 * { xxx:ok yyy:${xxx}/zzzz} p(yyy) : ok/zzz
	 *
	 * @param o
	 * @return
	 */
	public Object param_eval(Object o) {
		if (o instanceof String) {
			Matcher m = pp.matcher(o.toString());
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String name = m.group(1);
				String value = (String) this.ccpath(name);
				if (value != null) {
					m.appendReplacement(sb, value.toString());
				}
			}
			m.appendTail(sb);
			return sb.toString();
		}
		return o;
	}

	public Object ccpath(String query) {
		String[] items = query.split(":");
		Object p = this;
		for (String key : items) {
			if (p instanceof ICCMap) {
				Object c = ((ICCMap) p).obj(key);
				p = this.param_eval(c);
			} else if (p instanceof ICCList) {
				int idx = cast_int(key, -1);
				if (idx < 0) {
					return null;
				}
				Object c = ((CCList) p).get(idx);
				p = c;
			} else {
				return null;
			}
		}
		return p;
	}

	public int cast_int(Object v, int dv) {
		if (v instanceof Number) {
			return ((Number) v).intValue();
		} else if (v instanceof String) {
			String text = (String) v;
			return Integer.parseInt(text);
		}
		return dv;
	}

	/**
	 * smart set ccpath value s("abc:def",10) ---> {abc: { def : 10 }}
	 *
	 * @param key
	 * @param value
	 */
	public void ccpath(String key, Object value) {
		String[] items = key.split(":");
		if (items.length == 1) {
			put(items[0], value);
		}
		ICCMap p = this;
		int len = items.length - 1;
		for (int i = 0; i < len; i++) {
			ICCMap c = p.map(items[i]);
			//System.out.println(items[i] + ":" + c);
			if (c == null) {
				c = new CCMap();
				p.set(items[i], c);
			}
			p = c;
		}
		p.set(items[len], value);
	}
}

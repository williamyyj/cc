/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.util;

import org.cc.CCList;
import org.cc.CCMap;
import org.cc.ICCList;
import org.cc.ICCMap;

/**
 *
 * @author william
 */
public class CCUtils {

	public static ICCMap find(ICCList list, String id, String value) {
		if (list != null) {
			int len = list.len();
			for (int i = 0; i < len; i++) {
				Object o = list.obj(i);
				if (o instanceof ICCMap) {
					ICCMap m = (ICCMap) o;
					if (m.has(id) && m.asString(id).equalsIgnoreCase(value)) {
						return m;
					}
				}
			}
		}
		return null;
	}
	
	public static int indexOf(ICCList list, String id, String value) {
		if (list != null) {
			int len = list.len();
			for (int i = 0; i < len; i++) {
				Object o = list.obj(i);
				if (o instanceof ICCMap) {
					ICCMap m = (ICCMap) o;
					if (m.has(id) && m.asString(id).equalsIgnoreCase(value)) {
						return i;
					}
				}
			}
		}
		return -1;
	}

	public static ICCList include(ICCList list, String id, String... names) {
		ICCList ret = new CCList();
		int len = ret.len();
		for (int i = 0; i < list.len(); i++) {
			ICCMap m = list.map(i);
			if (m != null && in(names, m.asString(id))) {
				ret.set(m);
			}
		}
		return ret;
	}

	public static ICCList exclude(ICCList list, String id, String... names) {
		ICCList ret = new CCList();
		int len = ret.len();
		for (int i = 0; i < list.len(); i++) {
			ICCMap m = list.map(i);
			if (m != null && !in(names, m.asString(id))) {
				ret.set(m);
			}
		}
		return ret;
	}

	public static boolean in(Object[] items, Object item) {
		for (Object o : items) {
			if (item.equals(o)) {
				return true;
			}
		}
		return false;
	}

	public static void ext(ICCList list, String name, ICCMap m) {
		int idx = indexOf(list, "name", name);
		ICCMap source = list.map(idx);
		ICCMap item = new CCMap();
		item.model().putAll(source.model());
		item.model().putAll(m.model());
		list.set(idx,item); // 至少原始物件關連不破壞
	}
	
}

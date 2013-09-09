package org.cc.mvel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/*
 *   使用MVEL 做 Template
 * 
 */

public class MVELUtils {
	private static Map<String, MVELTemplate> cache;
	private static Map<String, MVELTemplate> cache() {
		if (cache == null) {
			cache = new HashMap<String, MVELTemplate>();
		}
		return cache;
	}

	public static ITemplate getTemplate(File f) {
		try {
			String fname = f.getAbsolutePath();
			MVELTemplate template = cache().get(fname);
			if (template == null) {
				template = new MVELTemplate(f);
				cache().put(fname, template);
			}
			return template;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

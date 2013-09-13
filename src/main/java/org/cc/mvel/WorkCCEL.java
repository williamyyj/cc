/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.mvel;

import java.io.PrintWriter;
import java.util.Map;
import org.cc.CCObject;
import org.cc.ICCMap;
import org.cc.db.DBSchema;
import org.cc.util.CCConfig;

/**
 *
 * @author william
 */
public class WorkCCEL {

	public static void main(String[] args) throws Exception {
		// D:\will\work\mcc\prj\baphiq\cfg.json
		System.out.println("===== start ");
		CCConfig cfg = new CCConfig("prj/baphiq");
		DBSchema dm = new DBSchema(cfg);
		CCEL el = new CCEL("prj/mvel/ht", "mvel");
		String table = "CardApplyView";
		String alias = "CardApply";
		String path = cfg.str("path_mapper");
		el.set_out(new PrintWriter(System.out));
		ICCMap m = new CCObject();
		m.set("pkg_mapper", cfg.obj("pkg_mapper"));
		m.set("pkg_bean", cfg.obj("pkg_bean"));
		m.set("classId", alias);
		ICCMap meta = dm.tb_meta(table);
		System.out.println(meta.json());
		m.set("meta", meta);
		m.set("$cr", "\r\n");
		el.exec("my/mapper", (Map) m);
		el.out().flush();
		System.out.println();
	}
}

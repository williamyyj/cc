package org.cc.mvel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.cc.CCCast;
import org.cc.CCObject;
import org.cc.ICCMap;
import org.cc.db.DBSchema;
import org.cc.tml.CCELNode;
import org.cc.tml.ICCEL;
import org.cc.tml.ICCVM;
import org.cc.util.CCConfig;
import org.mvel2.MVEL;
import org.slf4j.Logger;

public class CCEL implements ICCVM {

	protected CCCast cast = new CCCast();
	protected CCConfig cfg;
	protected int _ip;
	protected int _sp = 0;
	protected int _tab = 0;
	protected ICCEL[] _cs;
	protected VM[] _SS = new VM[1024];
	protected Map<String, Object> m;
	protected Map<String, CCELNode> m_cs;
	protected String base;
	protected String suffix;
	protected PrintWriter out;

	public CCEL(String base, String suffix) {
		this.base = base;
		this.suffix = suffix;
		m = new HashMap<String, Object>();
		m_cs = new HashMap<String, CCELNode>();
	}

	public void exec(String id, Map<String, Object> m) throws Exception {
		File f = new File(base, id + ".mvel");
		CCELNode nodes = m_cs.get(f.getAbsolutePath());
		if (nodes == null) {
			nodes = new CCELNode(f);
			m_cs.put(f.getAbsolutePath(), nodes);
		}
		exec(nodes.toArray(), m);
	}

	protected void exec(ICCEL[] cs, Map<String, Object> m) throws Exception {
		push_vm();
		_cs = cs;
		_ip = 0;
		while (_cs != null && _ip < _cs.length) {
			m.put("$vm_ip", _ip);
			exec(_ip, m);
		}
		pop_vm();
	}

	public void exec(int start, int end, Map<String, Object> m) throws Exception {
		_ip = start;
		while (end > _ip) {
			exec(_ip, m);
		}
	}

	public void exec(int idx, Map<String, Object> m) throws Exception {
		//System.out.println("\n=================== ip : " + _ip + " ===========================");
		//System.out.println("========="+ _cs[_ip].source());
		//System.out.println("================================================================");	 
		_cs[idx].processTemplate(this, m);
	}

	public Map<String, Object> m() {
		return null;
	}

	public void next() {
		_ip += 1;
	}

	public void next(int pos) {
		_ip = pos;
	}

	public PrintWriter out() {
		return out;
	}

	public void set_out(PrintWriter out) {
		this.out = out;
	}

	public void set_out(File file) throws IOException {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter( new FileOutputStream(file),"UTF-8"));
		set_out(pw);
	}

	
	public Logger log() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Object eval(Map<String, Object> m, Object text) {
		try {
			// 目前利用 mvel 來做運算
			return MVEL.eval(text.toString(), m);
		} catch (Exception e) {
			return text;
		}
	}

	public Object[] to_params(Map<String, Object> m, String args, int start) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public boolean not_null(Object o) {
		if (o instanceof Collection) {
			return ((Collection) o).size() > 0;
		} else if (o != null) {
			return true;
		}
		return false;
	}

	protected void push_vm() {
		VM vm = new VM();
		vm._ip = _ip;
		vm._cs = _cs;
		_SS[_sp++] = vm;
	}

	protected void pop_vm() {
		VM vm = _SS[--_sp];
		_ip = vm._ip;
		_cs = vm._cs;
	}

	public class VM {

		public int _ip;
		public ICCEL[] _cs;
		public int _tab;
	}

	public void set_m(Map<String, Object> m) {
		this.m = m;
	}

	public void print(Object o) {
		out.print(o);
		out.flush();
	}

	public void println() {
		out.println();
		out.flush();
	}

	public void print(String text, int tabId) {
		if (tabId > 0) {
			String line = "\r\n";
			for (int i = 0; i < tabId; i++) {
				line += "\t";
			}
			char[] cbuf = text.toCharArray();
			int len = cbuf.length;
			for (int i = 0; i < len; i++) {
				char c = cbuf[i];
				if (c == 13 && len > i + 1 && cbuf[i + 1] == 10) {
					i++;
					out.append(line);
				} else if (c == 13 || c == 10) {
					out.append(line);
				} else {
					out.append(c);
				}
			}
		} else {
			out.print(text);
		}
		out.flush();
	}

	public int tab() {
		return _tab;
	}

	public void tab(int tab) {
		this._tab = tab;
	}

	public int asInt(Map<String, Object> m, Object key, int dv) {
		return cast.asInt(m.get(key), dv);
	}

	public boolean asBool(Map<String, Object> m, Object key, boolean dv) {
		return cast.asBool(m.get(key), dv);
	}

	public String base() {
		return this.base;
	}

	@Override
	public String toString() {
		return "===== ip:" + _ip;
	}

	public void proc_bean(CCConfig cfg, DBSchema dm, String table, String alias) throws Exception {
		long ts = System.nanoTime();
		try {
			String path = cfg.str("path_bean");
			set_out(new File(path,alias+".java"));
			ICCMap m = new CCObject();
			m.set("pkg_mapper", cfg.obj("pkg_mapper"));
			m.set("pkg_bean", cfg.obj("pkg_bean"));
			m.set("classId",alias);
			ICCMap meta = dm.tb_meta(table);
			System.out.println(meta.json());
			m.set("meta", meta);
			m.set("$cr", "\r\n");
			exec("my/bean", (Map) m);
			out().flush();
			System.out.println();
		} finally {
			out().close();
			long te = System.nanoTime();
			System.out.println("===== time : " + (te - ts) / 1E9);
		}
	}

	public void proc_mapper(CCConfig cfg, DBSchema dm, String table , String alias) throws Exception {
		long ts = System.nanoTime();
		try {
			String path = cfg.str("path_mapper");
			set_out(new File(path,alias+"Mapper.xml"));
			ICCMap m = new CCObject();
			m.set("pkg_mapper", cfg.obj("pkg_mapper"));
			m.set("pkg_bean", cfg.obj("pkg_bean"));
			m.set("classId",alias);
			ICCMap meta = dm.tb_meta(table);
			System.out.println(meta.json());
			m.set("meta", meta);
			m.set("$cr", "\r\n");
			exec("my/mapper", (Map) m);
			out().flush();
			System.out.println();
		} finally {
			out().close();
			long te = System.nanoTime();
			System.out.println("===== time : " + (te - ts) / 1E9);
		}
	}

	public static void main(String[] args) throws Exception {
		// D:\will\work\mcc\prj\baphiq\cfg.json
		System.out.println("===== start ");
		CCConfig cfg = new CCConfig("prj/baphiq");
		DBSchema dm = new DBSchema(cfg);
		CCEL el = new CCEL("prj/mvel/ht", "mvel");
		String table = "psTestResultView" ; 
		String alias = "TestResult";
		el.proc_bean(cfg, dm, table, alias);
		el.proc_mapper(cfg, dm, table, alias);
		//el.set_out(new PrintWriter(new FileWriter("test.txt")));
	}
}

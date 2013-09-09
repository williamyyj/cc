/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.tml;

import java.util.Map;

/**
 *
 * @author william
 */
public class el_indent implements ICCEL {

	protected int tabId;

	public el_indent(int tabId) {
		this.tabId = tabId;
	}

	public void processTemplate(ICCVM vm, Map<String, Object> m) throws Exception {
		Boolean indent = (Boolean) m.get("$indent");
		if (indent != null && indent) {
			String cr = (String) m.get("$cr");
			cr = (cr == null) ? "\r\n" : cr;
			for (int i = 0; tabId > i; i++) {
				cr += "\t";
			}
			vm.print(cr);
		}
		vm.next();
	}

	public String source() {
		return el_prefix + "indent" + el_suffix;
	}

	public void set_address(int adderss) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String toString() {
		return "#indent:" + tabId;
	}
}

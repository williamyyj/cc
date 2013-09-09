/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.tml;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 *
 * @author william
 */
public class el_import implements ICCEL {
	
	protected String id;
	protected int tabId;

	public el_import(String id, int tabId){
		this.id = id;
	}

	public void processTemplate(ICCVM vm, Map<String, Object> m) throws Exception {
		//System.out.println("===== tabId : " + tabId);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		PrintWriter old_pw = vm.out();
		int old_tab = vm.tab();
		vm.tab(vm.tab()+tabId);
		vm.set_out(pw);
		vm.exec(id,m);
		pw.flush();
		pw.close();
		vm.tab(old_tab);
		vm.set_out(old_pw);
		vm.print(sw.toString(), vm.tab()+tabId);
		vm.next();
	}

	public String source() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void set_address(int adderss) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	

}

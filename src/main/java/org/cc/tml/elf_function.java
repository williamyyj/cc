/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.tml;

import java.util.Map;

public class elf_function implements ICCEL {
	
	private Object[] args ;
	
	private ICCVMFunction fun ;
	
	private static String  pkg_name = "org.cc.tml.";
	
	public elf_function(Object ... args){
		this.args = args ;
		fun = new_instance(args[0]);
	}

	public void processTemplate(ICCVM vm, Map<String, Object> m) throws Exception {
		fun.exec(vm, m, args);
		vm.next();
	}

	public String source() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void set_address(int adderss) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	private ICCVMFunction new_instance(Object fId) {
		try {
			String classId = pkg_name+fId;
			//System.out.print("===== classId : " + classId);
			return (ICCVMFunction) Class.forName(classId).newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
}

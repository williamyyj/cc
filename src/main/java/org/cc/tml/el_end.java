/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.tml;

import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author william
 */
public class el_end implements ICCEL {

	public el_end(){

	}
	
	public void processTemplate(ICCVM vm, Map<String, Object> m) throws Exception {
		vm.next();
	}

	public String source() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public void set_address(int adderss) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}

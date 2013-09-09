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
public class elf_up0 implements ICCVMFunction {

	public void exec(ICCVM vm, Map<String, Object> m,Object ... args) throws Exception {
		String text = (String) vm.eval(m, args[1]);
		vm.print(Character.toUpperCase(text.charAt(0))+text.substring(1));
	}
	
}

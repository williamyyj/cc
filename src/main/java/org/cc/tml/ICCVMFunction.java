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
public interface ICCVMFunction {
	
	public void exec(ICCVM vm, Map<String,Object> m, Object ... args) throws Exception ; 
	
}

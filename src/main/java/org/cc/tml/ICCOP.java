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
public interface ICCOP extends ICCAST {
	
	public void processTemplate(ICCVM vm, Map<String,Object> m) throws Exception ;

    public String source();
	
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.tml;

import java.util.Map;

/**
 * @author william
 */

public interface ICCEL extends ICCAST {
    
    public final static String el_prefix = "@{" ; 
    
    public final static String el_suffix = "}" ; 
 
    public void processTemplate(ICCVM vm, Map<String,Object> m) throws Exception ;

    public String source();
    
    public void set_address(int adderss);
     
}

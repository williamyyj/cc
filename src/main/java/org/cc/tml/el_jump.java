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
public class el_jump implements ICCEL {
    
    protected int address=-1 ; 
    
    public el_jump(){
        
    }
    
    public el_jump(int address){
        this.address = address ; 
    } 
    
    public void processTemplate(ICCVM el, Map<String, Object> m) throws Exception {
        el.next(address);
    }

    public String source() {
        return el_prefix+"jump:"+address+el_suffix ;
    }

    public void set_address(int address) {
        this.address = address ; 
    }
    
}

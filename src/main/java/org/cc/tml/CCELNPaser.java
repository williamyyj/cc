/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.tml;

import java.util.Map;
import org.cc.util.CCJSONParser;


/**
 * @author william
 * 與 客制化 parser 的結點 未支援
 */

public class CCELNPaser implements ICCEL {

    protected CCJSONParser p ; 
    
    protected String express ; 
    
    protected int address=-1 ; 
    
    public CCELNPaser(String express){
        this.express = express ; 
    }
    
    public void processTemplate(ICCVM el, Map<String,Object> m) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String source() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void set_address(int address) {
        this.address = address ; 
    }
    
}

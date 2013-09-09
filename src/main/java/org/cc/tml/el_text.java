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
public class el_text implements ICCEL {
    
    protected String text ; 
    
    public el_text(String text){
        this.text = text;
    }
    
    public void processTemplate(ICCVM vm, Map<String,Object> m) throws Exception {
        vm.print(text);
        vm.next();
    }

    public String source() {
        return "#text:"+text;
    }

    public void set_address(int adderss) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String toString(){
        return "#text:"+text;
    }
    
}

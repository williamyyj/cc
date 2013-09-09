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
public class el_elseif extends CCELNPaser{
    
    public el_elseif(String express){
        super(express);
    }
    
    @Override
    public void processTemplate(ICCVM vm, Map<String,Object> m) throws Exception {
        Boolean flag = (Boolean) vm.eval(m,express);
        if (flag) {
            vm.next();
        } else {
            vm.next(address);
        }
    }

    @Override
    public String source() {
        return el_prefix +"elseif \""+express+"\""+  el_suffix;
    }
}

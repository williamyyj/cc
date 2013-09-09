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
public class el_var implements ICCEL {
    
    protected Object vid;
    protected Object dv;
    
    public el_var(Object vid) {
        this.vid = vid;
    }
    
    public el_var(Object vid,Object dv) {
        this.vid = vid;
        this.dv = dv;
    }

    @Override
    public void processTemplate(ICCVM vm, Map<String,Object> m) throws Exception {
        Object o = vm.eval(m,vid);
        if (vm.not_null(o)) {
            vm.print(o);
        }else if(dv!=null){
            vm.print(dv);
        }
        vm.next();
    }

    public String source() {
        return el_prefix+vid+" "+((dv!=null)?dv:"")+el_suffix; 
    }

    public void set_address(int adderss) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

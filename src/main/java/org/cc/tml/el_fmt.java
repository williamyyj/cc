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
public class el_fmt extends CCELNExpr {
    
    public el_fmt(String ... args){
        super(args);
    }
    
    @Override
    public void processTemplate(ICCVM vm, Map<String,Object> m) throws Exception {
        // ${fmt format_string var}
        if(args.length==3){
            Object o = vm.eval(m,args[2]);
            if(vm.not_null(o)){
                vm.print(String.format(args[1],o));
            }
        }
        vm.next();
    }
   
}

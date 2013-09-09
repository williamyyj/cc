/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.tml;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import static org.cc.tml.ICCEL.el_prefix;
import static org.cc.tml.ICCEL.el_suffix;

/**
 *
 * @author william
 */
public class el_each implements ICCEL {

    protected Object[] args;
    protected int address;
	protected int tabId;
	protected String varId ; 
	protected Object listId ; 
	protected Object cond ; 
	private String delimited;
	private int num ; 

    public el_each(Object[] args, int tabId) {
        this.args = args;
		this.tabId = tabId;
		varId = (String) args[1];
		listId =  args[2];
		cond = (args.length>=4) ? args[3] : null ;
		delimited = (String) ((args.length>=5) ? args[4] : null) ;
		num = (args.length>=6) ? ((Number)args[5]).intValue() : 0 ;
    }
/**
    public void processTemplate(IXOVM vm, Map<String, Object> m) throws Exception {
        // ${each vid items (cond) } .... ${end}
		String cond = (args.length>=4) ? args[3] : null ; 
        Collection items = (Collection) vm.eval(m, args[2]); // 取出Collection
        if (items != null) {
            int idx=0;
			int len = items.size();
			int ip_start = (Integer) m.get("$vm_ip")+1;
			m.put("$indent", false);
			m.put("$delimited", true);
            for (Object item : items) {
				m.put("$i", idx++);
                m.put(args[1], item);
				if(idx>=len){
					m.put("$delimited", false);
				}
				if( cond!=null){
					Boolean flag = (Boolean) vm.eval(m, cond);
					if(flag) {
						vm.exec(ip_start,address,m);
						m.put("$indent", true);
					} else{
						// 該行是空
						m.put("$indent", false);
					}
					//System.out.println("====== exclude : " + flag);
				} else {
					vm.exec(ip_start,address,m);
					m.put("$indent", true);
				}         
                m.remove(args[1]);
                m.remove("$i");

            }
        }
        vm.next(address);
    }
*/ 
	 
	// ${each vid items  ',' } .... ${end 5}
	public void processTemplate(ICCVM vm, Map<String, Object> m) throws Exception {
	
		//System.out.print("===== each varId : " + varId +" , lstId : " + listId + " , cond : "+ cond);
		Collection items = check_collection(vm,m);
			

        if (items != null) {
   
			int idx = 0 ; 
			int len = items.size();
			int ip_start = (Integer) m.get("$vm_ip")+1;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			PrintWriter old = vm.out();
			vm.set_out(pw);
		
            for (Object item : items) {
				m.put("$i", idx++);
				m.put(varId, item);
				vm.exec(ip_start,address,m);
				if(idx<len){
					if(delimited!=null){
						vm.print(delimited);
					}
					if(num>0 && idx % num==0){
						vm.println();
					}
				}
            }
			m.remove(varId);
            m.remove("$i");
			vm.set_out(old);
			vm.print(sw.toString(), tabId);
        }
        vm.next(address);
    }
	
	protected Collection check_collection(ICCVM vm, Map<String, Object> m){
        Collection items =(Collection) vm.eval(m, listId);
		if(cond==null){
			return items;
		}
		List<Object> list = new ArrayList<Object>();
		for (Object item : items) {
			m.put(varId, item);
			Boolean flag = (Boolean) vm.eval(m, cond);
			if(flag){
				list.add(item);
			}
			
		}
		m.remove(varId);
		return list;
	}

    public String source() {
        return el_prefix +"each "+  el_suffix + " : " + address ; 
    }

    public void set_address(int adderss) {
        this.address = adderss;
    }
}

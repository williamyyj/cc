package org.cc.tml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.cc.util.CCJsonParser;



/**
 * @author william
 *
 * ${} mvel ${if expr } ${else}| ${elseif expr} ${then} ${each tb tbs} ......${break ......} ${end}
 *
 * 改用
 */
public class CCELParser extends CCJsonParser  {

	//private String tag_import = IXOELAST.el_prefix+"import"+IXOELAST.el_suffix;
	protected ICCEL[] RS = new ICCEL[1024];
	protected int rp;
	protected int tabId = 0;

	public CCELParser(File f, String enc) {
		super(f, enc);
	}

	public CCELParser(String text) {
		super(text);
	}

	public ICCEL[] parser() throws Exception {
		List<ICCEL> list = new ArrayList<ICCEL>();
		parser_el(list);
		ICCEL[] nodes = new ICCEL[list.size()];
		list.toArray(nodes);
		return nodes;
	}

	protected void parser_el(List<ICCEL> list) throws Exception {
		tabId = 0;
		rp = 0 ; 
		this.tk_init();
		this.next();
		while (ch != 0) {
			if(m(ICCEL.el_prefix)){
				ICCEL el = tk_el(list.size());
				if(el!=null){
					list.add(el);
				}
			} else {
				list.add(new el_text(tk_text()));
			}
		}
	}

	public ICCEL tk_el(int address) throws Exception {
		ICCEL ast = null;
		Object[] args = tk_items();
		String op = (String) args[0];
		//System.out.println("====== el : "  + Arrays.toString(args));
		//System.out.println("====== op : "  + op+"::::");
		if("end".equals(op)){
			rs_pop(address+1);
			return new el_end();			
		} else if ("else".equals(op)){
			rs_pop(address + 1);
			return rs_push(new el_jump());
		} else if("elseif".equals(op)){
			rs_pop(address);
			return rs_push(new el_elseif(args[1].toString()));
		} else if ("if".equals(op)){
			return rs_push(new el_if(args[1].toString()));
		} else if ("each".equals(op)){
			//System.out.println("====== el : "  + Arrays.toString(args));
			return rs_push(new el_each(args, tabId));
		} else if ("import".equals(op)){
			String fid = (String) args[1];
			return new el_import(fid,tabId);
		} else if(op.startsWith("$")){
			args[0] = op.replace("$", "elf_");
			return new elf_function(args);
		}
		return new el_var(op);
		
	}
	
	public Object[] tk_items() throws Exception {
		List<Object> list = new ArrayList<Object>();
		tk_m(ICCEL.el_prefix) ; 
		for (;;) {
            tk_csp();
			list.add(cc_next(wordPattern));
            tk_csp();
            if (tk_m(ICCEL.el_suffix) || ch==0) {
                break;
            }
        }
		return list.toArray();
	}

	public String tk_text() {
		start = ps;
		while (ch != 0 & !m(ICCEL.el_prefix)) {
			if (data[ps] == 9) {
				tabId++;
			}
			if (data[ps] == 10 || data[ps] == 13) {
				tabId = 0;
			}
			next();
		}
		return subString(start, ps);
	}

	public ICCEL rs_push(ICCEL node) {
		RS[rp++] = node;
		return node;
	}

	public ICCEL rs_pop(int address) {
		//  因為pop 才能決定 address 
		ICCEL node = RS[--rp];
		node.set_address(address);
		return node;
	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.tml;

import java.io.PrintWriter;
import java.util.Map;
import org.slf4j.Logger;

public interface ICCVM {
	
	public String base();
    public void next();
    public void next(int pos);
    public PrintWriter out();
    public void set_out(PrintWriter out);
	public int tab();
	public void tab(int tabId);
    public void print(Object o);
    public void print(String text, int tabId);
	public void println();
    public Logger log();
    public void exec(int start, int end, Map<String,Object> m) throws Exception ; 
	public void exec(String id, Map<String, Object> m) throws Exception ;
    public Object eval(Map<String,Object> m, Object expr);
    public Object[] to_params(Map<String,Object> m, String args, int start);
    public boolean not_null(Object o);
	public int asInt(Map<String,Object> m, Object key, int dv);
	public boolean asBool(Map<String,Object> m, Object key, boolean dv);
	
}

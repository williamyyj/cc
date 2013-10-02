/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.db;

import java.util.ArrayList;
import java.util.List;

/**
 * @author william
 */

public class DBParams {
	
	public StringBuilder sql ; 
	
	public List<Object> params ; 
	
	public DBParams(){
		this.sql = new StringBuilder();
		this.params = new ArrayList<Object>();
	}
		
}

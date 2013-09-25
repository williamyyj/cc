/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import org.cc.ICCFunction;
import org.cc.db.IDBSchema;

/**
 * @author william
 */
public class DBFBase<E> implements ICCFunction<E> {
	
	protected IDBSchema schema ; 
	
	public DBFBase(IDBSchema schema){
		this.schema = schema ; 
	}

	public E execute(Object... params) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import org.cc.CC;
import org.cc.ICCMap;
import org.cc.algorithm.ICCMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author william not Thred Safe
 */

public abstract class CCMBase<E> implements ICCMethod<E> {
	
	protected static Logger log = LoggerFactory.getLogger(CCMBase.class);
	
	public boolean exclude(String exclude, String id) {
		String alias = CC.to_alias(id);
		return (exclude != null && exclude.indexOf(id) >= 0) ? true : false;
	}

	public boolean is_null(ICCMap cc, String var) {
		return (!cc.has(var) || cc.obj(var)==null) ? true : false ;  
	}
	
	public Object get_param(int idx, Object ... params){
		return  (params!=null && params.length> idx) ? params[idx] : null ; 
	}
	
}

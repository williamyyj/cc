/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import org.cc.ICCMap;
import org.cc.db.IDB;

/**
 *
 * @author william
 */
public class CCMDBAction extends CCMBase<Integer> {

	public Integer execute(Object... params) throws Exception {
		IDB db = (IDB) get_param(0,params);
		String tableId = (String) get_param(1,params);
		String actId = (String) get_param(2,params);
		ICCMap row = (ICCMap) get_param(3,params);
		return exec(db,tableId,actId,row);
	}

	public Integer exec(IDB db, String tableId, String actId, ICCMap row) {
		if(IDB.act_add.equals(actId)){
			
		} else if (IDB.act_edit.equals(actId)){
			
		} else if (IDB.act_del.equals(actId)){
			
		} else if (IDB.act_save.equals(actId)){
			
		}
		return -1 ;
	}
	
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.cc.ICCMap;
import org.cc.db.IDB;
import static org.cc.fun.db.CCMBase.log;
import org.cc.meta.ITableMetadata;
import org.cc.type.ICCType;

/**
 * @author william
 * 
 */

public class CCMPSInsert extends CCMBase<Integer> {

	public Integer execute(Object... params) throws Exception {
		IDB db = (IDB) get_param(0, params);
		String tableId = (String) get_param(1, params);
		ICCMap row = (ICCMap) get_param(1, params);
		String exclude = (String) get_param(2, params);
		return exec(db, tableId, row, exclude);
	}
	
	public Integer exec(IDB db, String tableId , ICCMap row, String exclude) throws SQLException {
		ITableMetadata tbmd = db.tb_meta(tableId);
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(tbmd.table());
		proc_cols_name(sql, tbmd, row, exclude);
		sql.append(" values ");
		proc_cols_value(sql, tbmd, row, exclude);
		log.debug("===== sql : " + sql);
		log.debug("===== row : " + row.json());
		PreparedStatement ps = db.connection().prepareStatement(sql.toString());
		int ret = -1;
		try{
			proc_ps_value(ps,tbmd,row,exclude) ; 
			ret = ps.executeUpdate();
		} finally {
			if(ps!=null) ps.close();
		}
		return ret ; 
	}



	public void proc_cols_name(StringBuilder sql, ITableMetadata md, ICCMap row, String exclude) {
		sql.append(" (");
		int len = md.meta_len();
		for (int i = 0; i < len; i++) {
			ICCMap col = md.meta(i);
			String col_name = col.str("name");
			ICCType type = md.type(col);
			if (!is_null(row, col_name) && !exclude(exclude, col_name)) {
				sql.append(col_name).append(',');
			}
		}
		sql.setCharAt(sql.length() - 1, ')');
	}

	public void proc_cols_value(StringBuilder sql, ITableMetadata md, ICCMap row, String exclude) {
		sql.append('(');
		int len = md.meta_len();
		for (int i = 0; i < len; i++) {
			ICCMap col = md.meta(i);
			String col_name = col.str("name");
			if (!is_null(row, col_name) && !exclude(exclude, col_name)) {
				ICCType<?> dt = md.type(col);
				Object v = row.obj(col_name) ;
				sql.append("?,");
			}
		}
		sql.setCharAt(sql.length() - 1, ')');
	}
	
	public void proc_ps_value(PreparedStatement ps, ITableMetadata md, ICCMap row, String exclude) throws SQLException {
		int len = md.meta_len();
		int idx = 1 ; 
		for (int i = 0; i < len; i++) {
			ICCMap col = md.meta(i);
			String col_name = col.str("name");
			if (!is_null(row, col_name) && !exclude(exclude, col_name)) {
				ICCType<?> dt = md.type(col);
				Object v = row.obj(col_name) ;
				dt.setPS(ps, idx++, v);
			}
		}
	}
	
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import org.cc.ICCMap;
import org.cc.meta.ITableMetadata;
import org.cc.type.ICCType;

/**
 * @author william
 */
public class CCMSQLInsert extends CCMBase<String> {

	public String execute(Object... params) throws Exception {
		ITableMetadata md = (ITableMetadata) get_param(0, params);
		ICCMap row = (ICCMap) get_param(1, params);
		String exclude = (String) get_param(2, params);
		return execute(md, row, exclude);
	}
	
	public String exec(ITableMetadata md, ICCMap row, String exclude){
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(md.table());
		proc_cols_name(sql, md, row, exclude);
		sql.append(" values ");
		proc_cols_value(sql, md, row, exclude);
		return sql.toString();
	}



	public void proc_cols_name(StringBuilder sb, ITableMetadata md, ICCMap row, String exclude) {
		sb.append(" (");
		int len = md.meta_len();
		for (int i = 0; i < len; i++) {
			ICCMap col = md.meta(i);
			String col_name = col.str("name");
			ICCType type = md.type(col);
			if (!is_null(row, col_name) && !exclude(exclude, col_name)) {
				sb.append(col_name).append(',');
			}
		}
		sb.setCharAt(sb.length() - 1, ')');
	}

	public void proc_cols_value(StringBuilder sb, ITableMetadata md, ICCMap row, String exclude) {
		sb.append('(');
		int len = md.meta_len();
		for (int i = 0; i < len; i++) {
			ICCMap col = md.meta(i);
			String col_name = col.str("name");
			if (!is_null(row, col_name) && !exclude(exclude, col_name)) {
				ICCType<?> dt = md.type(col);
				Object v = row.obj(col_name) ;
				String value = dt.sql_value(v);
				sb.append(value).append(',');
			}
		}
		sb.setCharAt(sb.length() - 1, ')');
	}
	
}

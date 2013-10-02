/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import org.cc.ICCMap;
import org.cc.db.IDB;
import static org.cc.fun.db.CCMBase.log;
import org.cc.meta.ITableMetadata;
import org.cc.type.ICCType;

/**
 *
 * @author william
 */
public class CCMPSUpdate extends CCMBase<Integer> {

	public Integer execute(Object... params) throws Exception {
		IDB db = (IDB) get_param(0, params);
		String tableId = (String) get_param(1, params);
		ICCMap row = (ICCMap) get_param(1, params);
		String exclude = (String) get_param(2, params);
		return exec(db, tableId, row, exclude);
	}

	private Integer exec(IDB db, String tableId, ICCMap row, String exclude) throws SQLException {
		ITableMetadata tbmd = db.tb_meta(tableId);
		String sql = prepare_sql(tbmd, row);
		log.debug("===== sql : " + sql);
		log.debug("===== row : " + row.json());
		PreparedStatement ps = db.connection().prepareStatement(sql.toString());
		int ret = -1;
		try {
			prepare_ps(ps, tbmd, row);
			ret = ps.executeUpdate();
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
		return ret;
	}

	private String prepare_sql(ITableMetadata tbmd, ICCMap row) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(tbmd.table()).append(" set ");
		Set<String> names = row.names();
		for (String name : names) {
			ICCMap meta = tbmd.meta(name);
			if (meta != null && !meta.bool("pk")) {
				sql.append(' ').append(meta.str("name")).append("= ? ,");
			}
		}
		sql.setLength(sql.length() - 1);
		sql.append(" where "); //  -- 強制要用primary key 
		for (String name : names) {
			ICCMap meta = tbmd.meta(name);
			if (meta != null && meta.bool("pk")) {
				sql.append(' ').append(meta.str("name")).append("= ? and");
			}
		}
		sql.setLength(sql.length() - 3);
		return sql.toString();
	}

	private void prepare_ps(PreparedStatement ps, ITableMetadata tbmd, ICCMap row) throws SQLException {
		Set<String> names = row.names();
		int idx = 1;
		for (String name : names) {
			ICCMap meta = tbmd.meta(name);
			if (meta != null && !meta.bool("pk")) {
				ICCType type = tbmd.type(meta);
				Object value = row.obj(name);
				type.setPS(ps, idx++, value);
			}
		}
		for (String name : names) {
			ICCMap meta = tbmd.meta(name);
			if (meta != null && meta.bool("pk")) {
				ICCType type = tbmd.type(meta);
				Object value = row.obj(name);
				type.setPS(ps, idx++, value);
			}
		}

	}
}

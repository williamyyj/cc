package org.cc.meta;

import org.cc.CC;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.type.CCTypes;
import org.cc.type.ICCType;

/**
 * Created with IntelliJ IDEA. User: william Date: 2013/7/29 Time: 下午 4:53 To change this template use File | Settings | File
 * Templates.
 */
public class DBTableMeta implements ITableMeta {

	private ICCMap tb_meta;
	private ICCList cols;
	private CCTypes types;

	public DBTableMeta(ICCMap tb_meta, CCTypes types) {
		this.types = types;
		this.tb_meta = tb_meta;
		this.cols = tb_meta.list("column");
	}

	public boolean not_null() {
		return (tb_meta != null);
	}

	public ICCMap column(int idx) {
		return (not_null()) ? cols.map(idx) : null;
	}

	public ICCMap column(String name) {
		if (not_null()) {
			String alias = CC.to_alias(name);
			for (int i = 0; i < cols.len(); i++) {
				ICCMap col = cols.map(i);
				if (name.equals(col.obj("alias"))) {
					return col;
				}
			}
		}
		return null;
	}

	public int cols_len() {
		return (not_null()) ? cols.len() : 0;
	}

	public ICCType type(ICCMap column) {
		return types.type(column.toI("dt_sql"));
	}

	public String table() {
		return (not_null()) ? tb_meta.str("table") : null;
	}

	public String toString() {
		return (this.tb_meta != null) ? tb_meta.json("\t") : super.toString();
	}

	public String catalog() {
		return (not_null()) ? tb_meta.str("catalog") : null;
	}

	public String schema() {
		return (not_null()) ? tb_meta.str("schema") : null;
	}

	public ICCList cols() {
		return (not_null()) ? cols : null;
	}
	
	public ICCMap model(){
		return (not_null()) ? tb_meta : null;
	}
}

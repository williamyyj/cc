package org.cc.meta;

import java.util.HashMap;
import java.util.Map;
import org.cc.CC;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.type.CCTypes;
import org.cc.type.ICCType;

/**
 * Created with IntelliJ IDEA. User: william Date: 2013/7/29 Time: 下午 4:53 To change this template use File | Settings | File
 * Templates.
 */
public class DBTableMetadata implements ITableMetadata {

	private ICCMap tb_meta;
	private ICCList cols;
	private CCTypes types;
	private Map<Object,ICCMap> cm ; 

	public DBTableMetadata(ICCMap tb_meta, CCTypes types) {
		this.types = types;
		this.tb_meta = tb_meta;
		cm = new HashMap<Object,ICCMap>();
		if(tb_meta!=null){
			this.cols = tb_meta.list("column");
			init_cm(this.cols);
		}
	}
	
	private void init_cm(ICCList cols) {
		int len = cols.len();
		for(int i=0; i<len ; i++){
			ICCMap m = cols.map(i);
			cm.put(m.str("name"), m);
			cm.put(m.str("alias"), m);
			cm.put(i, m);
		}
	}

	public boolean not_null() {
		return (tb_meta != null);
	}

	public ICCMap meta(int idx) {
		return (not_null()) ? cols.map(idx) : null;
	}

	public ICCMap meta(String name) {
		if (not_null()) {
			String sn = CC.to_short(name);
			return cm.get(sn);
		}
		return null;
	}

	public int meta_len() {
		return (not_null()) ? cols.len() : 0;
	}

	public String table() {
		return (not_null()) ? tb_meta.str("table") : null;
	}

	@Override
	public String toString() {
		return (this.tb_meta != null) ? tb_meta.json("\t") : super.toString();
	}

	public String catalog() {
		return (not_null()) ? tb_meta.str("catalog") : null;
	}

	public String schema() {
		return (not_null()) ? tb_meta.str("schema") : null;
	}

	public ICCList meta_list() {
		return (not_null()) ? cols : null;
	}

	public ICCMap model() {
		return (not_null()) ? tb_meta : null;
	}

	public ICCType type(Object dt) {
		if (dt instanceof ICCMap) {
			ICCMap m = (ICCMap) dt;
			return (m.has("dt_sql")) ? types.type(m.toI("dt_sql")) : null;
		} else if (dt instanceof Number) {
			return types.type(((Number) dt).intValue());
		} else {
			return types.type(dt);
		}
	}

	public Object field(ICCMap m, String name) {
		String sn = CC.to_short(name);
		ICCMap meta = cm.get(sn) ; 
		return (meta!=null ) ? m.obj(meta.str("name")) : null ; 
	}


}

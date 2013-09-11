package org.cc.meta;

import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.db.IDBSchema;
import org.cc.type.CCTypes;
import org.cc.type.ICCType;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/29
 * Time: 下午 4:53
 * To change this template use File | Settings | File Templates.
 */
public class MetaTable {

    private ICCMap tb_meta;
    private ICCList cols;
    private IDBSchema schema;
    private CCTypes types;

    public MetaTable(IDBSchema schema, String table) {
        this.schema = schema;
        try {
            types = schema.types();
            tb_meta = schema.load_tb_meta(table);
            if (tb_meta != null) {
                cols = tb_meta.list("column");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean not_null() {
        return (tb_meta != null);
    }

    public ICCMap cols(int idx) {
        return (not_null()) ? cols.map(idx) : null;
    }

    public int cols_len() {
        return (not_null()) ? cols.len() : 0;
    }

    public ICCType type(ICCMap column) {
        return types.type(column.asInt("dt_sql"));
    }

    public String table() {
        return (not_null()) ? tb_meta.asString("table") : null;
    }

    public ICCMap indexOf(String name) {
        if (not_null()) {
            for (int i = 0; i < cols.len(); i++) {
                ICCMap col = cols.map(i);
                if (name.equals(col.obj("name"))) {
                    return col;
                }
            }
        }
        return null;
    }

    public String toString(){
        return (this.tb_meta!=null) ?  tb_meta.json("\t") : super.toString();
    }


}

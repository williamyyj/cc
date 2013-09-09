/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import org.cc.*;
import org.cc.db.IDBSchema;
import org.cc.meta.MetaTable;
import org.cc.type.CCTypes;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;


/**
 * @author william
 */
public class DBFColMeta extends FunBase implements IFunction<ICCList, IDBSchema> {

    private CCTypes types;
	//private boolean jdbc_type_upper_case = false ;

    public DBFColMeta() {
        this(new CCTypes());
    }

    public DBFColMeta(CCTypes types) {
        this.types = types;
    }

    public ICCList exec(IDBSchema dm, Object... args) throws Exception {
        CCList list = new CCList();
        String catalog = (String) args[0];
        String schema = (String) args[1];
        String table = (String) args[2];
        DatabaseMetaData dbmd = dm.db_meta();
        MetaTable mt = new  MetaTable(dm,table);
        ResultSet rs = null;
        try {
            rs = dbmd.getColumns(catalog, schema, table, null);
            while (rs.next()) {
                list.set(get_meta(rs,mt));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return (list.len() > 0) ? list : null;
    }



    public ICCMap get_meta(ResultSet rs, MetaTable mt) throws Exception {
        // 改用SYS_MAP去決定型別
        String name =    rs.getString("COLUMN_NAME") ;
        ICCMap info = mt.indexOf(name);
        info = (info==null) ? new CCMap() : info ;
        info.setIsIndent(false);
        info.set("name", name);
        info.set("alias", CC.to_alias(name));
        int dt_sql = rs.getInt("DATA_TYPE");
        info.set("dt", types.type(dt_sql).dt());
        info.set("ord", rs.getInt("ORDINAL_POSITION"));
        info.set("dt_size", rs.getInt("COLUMN_SIZE"));
        info.set("dt_sql", dt_sql);
        info.set("dt_name", rs.getString("TYPE_NAME"));
        info.set("not_null", get_is_null(rs));
        return info;
    }

    public boolean get_is_null(ResultSet rs) throws Exception {
        String is_null = rs.getString("IS_NULLABLE");
        return ("Y".equalsIgnoreCase(is_null) || "YES".equalsIgnoreCase(is_null)) ? true : false;
    }
}

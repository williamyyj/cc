/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import java.sql.DatabaseMetaData;
import org.cc.CCMap;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.IFunction;
import org.cc.db.IDBSchema;


/**
 *
 * @author william
 */
public class DBFTableMeta implements IFunction<ICCMap, IDBSchema> {

    public ICCMap exec(IDBSchema dm, Object... args) throws Exception {
        String catalog = (String) args[0];
        String schema = (String) args[1];
        String table = (String) args[2];
        ICCMap m = new CCMap();
        DatabaseMetaData dbmd = dm.db_meta();
        IFunction<ICCList, IDBSchema> fun_col_meta = new DBFColMeta(dm.types());
        ICCList cols = fun_col_meta.exec(dm,catalog,schema,table);
        String[] pks = dm.pk(table);
        if (cols != null) {
            for (int i = 0; i < cols.len(); i++) {
                ICCMap col = cols.map(i);
                col.set("pk", pk(pks, col.asString("name")));
            }
        }
        m.set("catalog", catalog);
        m.set("schema", schema);
        m.set("table", table);
        m.set("column", cols);
        return m;
    }

    public ICCMap find_info(ICCList infos, String name, Object value) {
        if (infos != null && infos.len() > 0) {
            for (int i = 0; i < infos.len(); i++) {
                ICCMap info = infos.map(i);
                if (info.has(name) && info.obj(name).equals(value)) {
                    return info;
                }
            }
        }
        return null;
    }

    public boolean pk(String[] pks, String name) {
        if (pks == null) {
            return false;
        }
        for (String pk : pks) {
            if (pk.equals(name)) {
                return true;
            }
        }
        return false;
    }
}

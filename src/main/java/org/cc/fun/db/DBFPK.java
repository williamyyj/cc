/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.cc.IFunction;

/**
 * @author william
 */
public class DBFPK implements IFunction<String[], DatabaseMetaData> {

    public String[] exec(DatabaseMetaData meta, Object... args) throws Exception {
        String catalog = (String) args[0];
        String schema = (String) args[1];
        String table = (String) args[2];
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs = null;
        try {
            rs = meta.getPrimaryKeys(catalog, schema, table);
            while (rs.next()) {
                String name = rs.getString("COLUMN_NAME");
                list.add(name);
            }
            return list.toArray(new String[list.size()]);
        } finally {
            // 使用sqlite statment 沒有 close 
            if (rs != null) {
                rs.close();
            }
        }
    }
}

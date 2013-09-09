/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.cc.IFunction;


/**
 * @author william
 * 解決DB 功能程式碼太過複雜問題，統一使用IFuction來解決問題
 */
public class DBFColumn extends FunBase implements IFunction<String[], DatabaseMetaData> {

    public String[] exec(DatabaseMetaData dbmd, Object... args) throws Exception {
        String catalog = (String) args[0];
        String schema = (String) args[1];
        String table = (String) args[2];
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs = null;
        try {
            rs = dbmd.getColumns(catalog, schema, table, null);
            while (rs.next()) {
                String name = rs.getString("COLUMN_NAME");
                list.add(name);
            }

            return list.toArray(new String[list.size()]);
        } catch (SQLException e) {
            log.error("XODBMeta : ", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.error("XODBMeta : ", e);
                }
            }
        }
        return null;
    }
    
}

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author william
 */

public class DBFTable implements IFunction<String[], DatabaseMetaData> {
    
    private static Logger log = LoggerFactory.getLogger(DBFTable.class);
    
    public String[] exec(DatabaseMetaData dbmd, Object... args) throws Exception {
        String[] DBTypes = {"TABLE"};
        String catalog = (String) args[0];
        String schema = (String) args[1];
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs = null;
        try {
            rs = dbmd.getTables(catalog, schema, null, DBTypes);
            while (rs.next()) {
                String tbName = rs.getString("TABLE_NAME");
                // FIX oracle 
                if (!tbName.startsWith("BIN$")) {
                    list.add(tbName);
                }
            }
            return list.toArray(new String[list.size()]);
        } catch (SQLException e) {
            log.error("DBFunTables : ", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.error("DBFunTables : ", e);
                }
            }
        }
        return null;
    }
}

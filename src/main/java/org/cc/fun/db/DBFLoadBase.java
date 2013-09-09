/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import org.cc.CCMap;
import org.cc.ICCMap;



/**
 * @author william
 */

public class DBFLoadBase {

    public Object get_value(ResultSet rs, String name, int dt) throws SQLException {
        // System.out.println("===== dt : " + dt + " nam : " + name);
        switch (dt) {
            case Types.CHAR:
            case Types.LONGVARCHAR:
            case Types.VARCHAR:
                String ret = rs.getString(name);
                return (ret != null) ? ret.trim() : ret;
            case Types.CLOB:
                return rs.getString(name);
            case Types.DATE:
                return rs.getDate(name);
            case Types.TIME:
                return rs.getTime(name);
            case Types.TIMESTAMP:
                java.sql.Timestamp fld = rs.getTimestamp(name);
                return (fld != null) ? new Date(fld.getTime()) : null;
            default:
                return rs.getObject(name);
        }
    }
    
    public ICCMap get_row(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
        CCMap m  = new CCMap();
        int len = rsmd.getColumnCount();
        for(int i=1;i<=len;i++){
            String column = rsmd.getColumnName(i);
            int dt_sql = rsmd.getColumnType(i);
            m.set(column, get_value(rs, column, dt_sql));
        }
        return m;
    }
    
    
}

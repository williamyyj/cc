/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import org.cc.IFunction;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



public class DBFColCSV extends FunBase implements IFunction<List<String>, DatabaseMetaData> {


     
    public List<String> exec(DatabaseMetaData dbmd, Object... args) throws Exception {
        List<String> list = new ArrayList<String>();
        String catalog = (String) args[0];
        String schema = (String) args[1];
        String table = (String) args[2];
        ResultSet rs = null;
        try{
            rs = dbmd.getColumns(catalog, schema, table, null);
            int idx=1;
            while(rs.next()){
                list.add(get_meta(idx++,table,rs));
            }
        } finally {
            if(rs!=null) {
                rs.close();
            }
        }
        return (list.size()>0) ? list : null ; 
    }
    
    public String get_meta(int idx,String table,ResultSet rs) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(idx).append(',');
        sb.append(table).append(',');
        sb.append(rs.getString("COLUMN_NAME")).append(',');
        sb.append("   ").append(',');
        sb.append(rs.getString("TYPE_NAME")).append('(').append(rs.getInt("COLUMN_SIZE")).append("),");
        sb.append(get_is_null(rs));
        System.out.println(sb.toString());
        return sb.toString();
    }
    
    public String get_is_null(ResultSet rs) throws Exception {
        String is_null = rs.getString("IS_NULLABLE");
        return ("Y".equalsIgnoreCase(is_null) || "YES".equalsIgnoreCase(is_null)) ? "" : "NN" ;
    }


}

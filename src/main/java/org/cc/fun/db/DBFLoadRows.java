/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import org.cc.ICCMap;
import org.cc.IFunction;


/**
 *
 * @author william
 */
public class DBFLoadRows extends DBFLoadBase implements IFunction<List<ICCMap>,ResultSet> {

    public List<ICCMap> exec(ResultSet rs, Object... args) throws Exception {
        List<ICCMap> list = new ArrayList<ICCMap>();
        ResultSetMetaData rsmd = rs.getMetaData();
        while(rs.next()){
            list.add(get_row(rs,rsmd));
        }
        return (list.size()>0) ? list : null;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.cc.ICCMap;
import org.cc.IFunction;



/**
 * @author william
 */

public class DBFLoadRow extends DBFLoadBase implements IFunction<ICCMap, ResultSet> {

    public ICCMap exec(ResultSet rs, Object... args) throws Exception {
        ResultSetMetaData rsmd = rs.getMetaData();
        return (rs.next()) ? get_row(rs,rsmd) : null;        
    }
    
}

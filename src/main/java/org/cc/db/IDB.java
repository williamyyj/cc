/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.cc.ICCMap;
import org.cc.type.CCTypes;


/**
 *
 * @author william
 */
public interface IDB {
   
    public String database();   
    public String catalog();
    public String schema();
    public String base();
    public String path_meta();
    public String path_data();
    public String pkg();
    public Connection connection() throws SQLException;
    public DataSource datasource() throws SQLException;
    public ICCMap row(String sql, Object... params) throws SQLException;
    public List<ICCMap> rows(String sql, Object... params) throws SQLException;
    public int exec(String sql, Object... params) throws SQLException;
    public int action(ICCMap row) throws SQLException;
    public void close() throws Exception ; 
    public CCTypes types();
    public ICCMap load_tb_meta(String tb) throws Exception ;
    public void __release(PreparedStatement ps, ResultSet rs) throws SQLException ;
    
}

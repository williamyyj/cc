/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


public class CCDoubleType extends CCBaseType<Double> {

    public String dt() {
        return dt_double;
    }

    public Double value(Object o, Double dv) {
        try {
            if (o instanceof Number) {
                return ((Number) o).doubleValue();
            } else if (o instanceof String) {
                String str = ((String)o).trim();
                return str.length() > 0 ? Double.parseDouble(str) : dv;
            }
        } catch (Exception e) {
            log.warn("Check " + this);
        }
        return dv;
    }

    public Double getRS(ResultSet rs, String name) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Class<?> nativeClass() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public int dt_sql(){
        return Types.DOUBLE;
    }
    
}

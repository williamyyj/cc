/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.db;

import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import org.cc.ICC;
import org.cc.IFunction;


/**
 *
 * @author william
 */
public class DBFFillRow implements IFunction<Object,PreparedStatement> {
    
    private boolean pmd_support = true;
    
    public Object exec(PreparedStatement ps, Object... params) throws Exception {
          ParameterMetaData pmd = null;
        if (!pmd_support) {
            pmd = ps.getParameterMetaData();
            int stmtCount = pmd.getParameterCount();
            int paramsCount = params == null ? 0 : params.length;

            if (stmtCount != paramsCount) {
                throw new SQLException("Wrong number of parameters: expected "+ stmtCount + ", was given " + paramsCount);
            }
        }

        // nothing to do here
        if (params == null) {
            return null;
        }

        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                Object value = params[i];
                if (value instanceof Date) {
                    // System.out.print("===== set datetime : " + value);
                    ps.setTimestamp(i + 1, new Timestamp(((Date) value).getTime()));
                } else if (value instanceof ICC) {
                    value = value.toString();
                    ps.setObject(i + 1, value);
                } else {
                    ps.setObject(i + 1, value);
                }
            } else {
                int sqlType = Types.VARCHAR;
                if (!pmd_support) {
                    try {
                        sqlType = pmd.getParameterType(i + 1);
                    } catch (SQLException e) {
                        pmd_support = true;
                    }
                }
                ps.setNull(i + 1, sqlType);
            }
        }
        return null;
    }
            
    
    
    
    
}

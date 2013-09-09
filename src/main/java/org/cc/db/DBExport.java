package org.cc.db;

import org.cc.ICCMap;
import org.cc.fun.db.SQLFInsert;
import org.cc.meta.MetaTable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/29
 * Time: 下午 4:22
 * To change this template use File | Settings | File Templates.
 */
public class DBExport {
    public static void main(String[] args) throws Exception {
        DBSchema meta = new DBSchema("prj/hycms");
        try{
            List<ICCMap> rows = meta.rows("select * from CodeMetaDef");
            MetaTable mt = new MetaTable(meta,"CodeMetaDef");
            SQLFInsert fins = new SQLFInsert();
            for(ICCMap row : rows){
                String sql = fins.exec(mt,row) ;
                System.out.println(sql+";");
            }
        } finally {
            meta.close();
        }
    }
}

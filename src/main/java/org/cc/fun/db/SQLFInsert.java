package org.cc.fun.db;


import org.cc.ICCMap;
import org.cc.meta.MetaTable;
import org.cc.type.ICCType;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/29
 * Time: 下午 4:28
 * To change this template use File | Settings | File Templates.
 */
public class SQLFInsert extends SQLFBase  {


    @Override
    public String exec(MetaTable mt, Object... args) throws Exception {
        ICCMap row = (ICCMap) args[0];


        StringBuilder sb = new StringBuilder();
        sb.append("insert into ").append(mt.table()).append(" (");
        int len = mt.cols_len();
        int idx = 0;
        for (int i=0; i<len ; i++) {
            ICCMap col = mt.cols(i);
            String col_name = col.str("name");
            ICCType type = mt.type(col);
            if ( !is_null(row,col_name)) {
                sb.append(col_name).append(',');
                idx++;
            }
        }
        if (idx > 0) {
            sb.setCharAt(sb.length() - 1, ')');
        }
        sb.append(" values (");
        for (int i=0; i<len ; i++) {
            ICCMap col = mt.cols(i);
            String col_name = col.str("name");
            if ( !is_null(row,col_name)) {
                ICCType<?> dt =  mt.type(col);
                String value = dt.sql_value(row.obj(col_name));
                sb.append(value).append(',');
            }
        }
        if (idx > 0) {
            sb.setCharAt(sb.length() - 1, ')');
        }
        return sb.toString();
    }

    // Fix isEmpty
    public boolean is_null(ICCMap jo, String var){
        if(jo.has(var)){
            Object v = jo.obj(var);
            return (v==null);
        }
        return true;
    }

}

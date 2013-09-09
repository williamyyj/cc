package org.cc.db;

import org.cc.ICCMap;
import org.cc.IFunction;
import org.cc.fun.db.DBFColumn;
import org.cc.fun.db.DBFPK;
import org.cc.fun.db.DBFTable;
import org.cc.fun.db.DBFTableMeta;
import org.cc.util.CCConfig;

import java.sql.DatabaseMetaData;


/**
 *  1. Dynamic Metadata
 */

public class DBSchema extends DB implements IDBSchema {

    private IFunction<String[], DatabaseMetaData> fun_table = new DBFTable();
    private IFunction<String[], DatabaseMetaData> fun_column = new DBFColumn();
    private IFunction<String[], DatabaseMetaData> fun_pk_meta = new DBFPK();
    private IFunction<ICCMap, IDBSchema> fun_tab_meta = new DBFTableMeta();
    private DatabaseMetaData dbmd = null;
    
    public DBSchema() {
        super();
    }

    public DBSchema(String base) {
        super(new CCConfig(base));
    }

    public DBSchema(CCConfig cfg){
        super(cfg);
    }

    public String[] tables() throws Exception {
        return fun_table.exec(db_meta(), catalog(), schema());
    }

    public String[] tables(String catalog, String schema) throws Exception {
        return fun_table.exec(db_meta(), catalog, schema);
    }

    public String[] columns(String table) throws Exception {
        return fun_column.exec(db_meta(), catalog(), schema(), table);
    }

    public String[] columns(String catalog, String schema, String table) throws Exception {
        return fun_table.exec(db_meta(), catalog, schema, table);
    }

    public String[] pk(String catalog, String schema, String table) throws Exception {
        return fun_pk_meta.exec(db_meta(), catalog, schema, table);
    }

    public String[] pk(String table) throws Exception {
        return fun_pk_meta.exec(db_meta(), catalog(), schema(),table);
    }

    public String[] indeies(String catalog, String schema, String table) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fk(String catalog, String schema, String table) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public DatabaseMetaData db_meta() throws Exception {
        if(dbmd==null){
            dbmd = connection().getMetaData();
        }
        return dbmd;
    }
    
    public ICCMap tb_meta(String table) throws Exception {
        return fun_tab_meta.exec(this, catalog(), schema(), table);
    }

    public ICCMap tb_meta(String catalog, String schema, String table) throws Exception {
        return fun_tab_meta.exec(this, catalog, schema, table);
    }


}

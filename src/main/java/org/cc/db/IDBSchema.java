package org.cc.db;

import org.cc.ICCMap;

import java.sql.DatabaseMetaData;



public interface IDBSchema extends IDB {
    
    public String[] tables() throws Exception ; 

    public String[] tables(String catalog, String schema) throws Exception ; 
    
    public String[] columns(String table) throws Exception ; 

    public String[] columns(String catalog, String schema, String table) throws Exception ; 

    public String[] pk(String catalog, String schema, String table) throws Exception ; 
    
     public String[] pk(String table) throws Exception ; 

    public String[] indeies(String catalog, String schema, String table) throws Exception ;

    public ICCMap tb_meta(String catalog, String schema, String table) throws Exception ;

    public void fk(String catalog, String schema, String table) throws Exception ; 

    public DatabaseMetaData db_meta() throws Exception;
    

     
}

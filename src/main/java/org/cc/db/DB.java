package org.cc.db;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.cc.CC;
import org.cc.ICCMap;
import org.cc.IFunction;
import org.cc.fun.db.DBFFillRow;
import org.cc.fun.db.DBFLoadRows;
import org.cc.meta.DBTableMetadata;
import org.cc.meta.ITableMetadata;
import org.cc.type.CCTypes;
import org.cc.util.CCConfig;
import xo.org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DB implements IDB {
	
    protected Logger log = LoggerFactory.getLogger(DB.class);
    protected CCConfig cfg;
    protected ICCMap res ;
    protected Connection conn;
    protected static Map<String, DataSource> mds;
    protected CCTypes cc_types;
    protected String id ; 
    protected File path_meta ;
    protected IFunction<List<ICCMap>,ResultSet> db_rows ;
    protected IFunction<Object,PreparedStatement> db_fill ;

    public DB(){
        this(new CCConfig());
    }
    
    public DB(String base) {
        this(new CCConfig(base));
    }
    
    public DB(CCConfig cfg){
        this.cfg = cfg ;
        res = cfg.map("db");
        this.id = res.str("id");
        cc_types = new CCTypes(res.str("database"));
        path_meta = new File(CC.ref(cfg,res.str("meta"),null));
        if(!path_meta.exists()){
            path_meta.mkdirs();
        }
        db_rows = new DBFLoadRows();
        db_fill = new DBFFillRow();
    }
          
    @Override
    public Connection connection() throws SQLException {
        if (conn == null) {
            conn = getDataSource(id).getConnection();
        }
        return conn;
    }
    
    @Override
    public void close() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void __release(PreparedStatement ps, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
            rs = null;
        }
        if (ps != null) {
            ps.close();
            ps = null;
        }
    }
    
    protected Map<String, DataSource> mds() {
        if (mds == null) {
            mds = new HashMap<String, DataSource>();
        }
        return mds;
    }
    
    protected DataSource getDataSource(String id) {
        DataSource old = mds().get(id);
        if (old == null && res!=null) {
            try {            
                PoolProperties p = new PoolProperties();
                p.setUrl(res.str("url"));
                p.setDriverClassName(res.str("driver"));
                p.setUsername(res.str("user"));
                p.setPassword(res.str("password"));
                p.setJmxEnabled(true);
                p.setTestWhileIdle(false);
                p.setTestOnBorrow(true);
                p.setValidationQuery("SELECT 1");
                p.setTestOnReturn(false);
                p.setValidationInterval(30000);
                p.setTimeBetweenEvictionRunsMillis(30000);
                p.setMaxActive(100);
                p.setInitialSize(3);
                p.setMaxWait(10000);
                p.setRemoveAbandonedTimeout(60);
                p.setMinEvictableIdleTimeMillis(30000);
                p.setMinIdle(10);
                p.setLogAbandoned(true);
                p.setRemoveAbandoned(true);
                p.setJdbcInterceptors("xo.org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                        + "xo.org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
                
                xo.org.apache.tomcat.jdbc.pool.DataSource ds = new xo.org.apache.tomcat.jdbc.pool.DataSource();
                ds.setPoolProperties(p);
                mds().put(id, ds);
                old = ds;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return old;
    }
    
    protected void addDataSource(String id, DataSource ds) {
        System.out.println(id);
        System.out.println(ds);
        DataSource old = mds.put(id, ds);
        if (old instanceof xo.org.apache.tomcat.jdbc.pool.DataSource) {
            ((xo.org.apache.tomcat.jdbc.pool.DataSource) old).close();
        }
    }
    
    @Override
    public DataSource datasource() throws SQLException {
        return getDataSource(id);
    }
    
    @Override
    public String catalog() {
        return res.str("catalog");
    }
    
    @Override
    public String schema() {
        return res.str("schema");
    }
    
    @Override
    public String base() {
        return cfg.base();
    }
    
    @Override
    public String pkg() {
        return res.str("pkg");
    }
    
    @Override
    public String database() {
        return res.str("database");
    }
    

    
    public ICCMap row(String sql, Object... params) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<ICCMap> rows(String sql, Object... params) throws SQLException {
        PreparedStatement ps = this.connection().prepareStatement(sql);
        ResultSet rs = null;
        try {
            db_fill.exec(ps,params);
            rs = ps.executeQuery();
            return db_rows.exec(rs);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
           rs.close();
           ps.close();
        }
        return new ArrayList<ICCMap>();
    }
    
    public int exec(String sql, Object... params) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public int action(ICCMap row) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public CCTypes types() {
        return cc_types;
    }


    protected ICCMap load_tb_meta(String tb) throws Exception {
        String alias = CC.to_alias(tb);
        File f = new File(path_meta(),alias+".json");
        if(f.exists()){
            return CC.load(f,"UTF-8");
        }
        return null;
    }
	
	public ITableMetadata tb_meta(String tb){
		try {
			ICCMap meta = load_tb_meta(tb);
			return new DBTableMetadata(meta,this.types());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

    public String path_meta() {
       return  path_meta.getAbsolutePath();
    }
    
    public String path_data() {
       String path = res.str("data");
       return (path!=null) ? path : base()+"/data" ;
    }

 
}

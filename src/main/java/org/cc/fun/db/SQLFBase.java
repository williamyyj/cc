package org.cc.fun.db;

import org.cc.IFunction;
import org.cc.meta.ITableMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/29
 * Time: 下午 4:30
 * To change this template use File | Settings | File Templates.
 */
public class SQLFBase implements IFunction<String, ITableMetadata> {

    protected static Logger log = LoggerFactory.getLogger(SQLFBase.class);

    @Override
    public String exec(ITableMetadata mt, Object... args) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}

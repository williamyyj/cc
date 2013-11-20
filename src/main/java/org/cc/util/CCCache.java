package org.cc.util;

import org.cc.util.CCJSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.cc.ICCMap;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/30
 * Time: 上午 11:06
 * To change this template use File | Settings | File Templates.
 */

public class CCCache {

    private static Logger log = LoggerFactory.getLogger(CCCache.class);

    private static ConcurrentHashMap<String,CCCacheItem> cache ;

    public static ConcurrentHashMap cache(){
        if(cache==null){
            cache = new ConcurrentHashMap<String,CCCacheItem>();
        }
        return cache ;
    }

    public static ICCMap load_map(File f, String enc){
        if(f==null){
            return null;
        }
        try {
            ICCMap ret = null;
            String id = f.getCanonicalPath() ;
            CCCacheItem<ICCMap> item = (CCCacheItem<ICCMap>) cache().get(id);
            if(item!=null && item.lastModified>= f.lastModified()){
                log.debug("using cache " + item.id +" lastload : " + item.lastModified);
                return item.value;
            } else if(item!=null){
				return reload_map(f,enc);
            } else if(item==null && f.exists()){
                return reload_map(f,enc);
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }
	
    private static synchronized ICCMap reload_map(File f ,String enc){
        CCCacheItem<ICCMap> item = new  CCCacheItem<ICCMap>();
        try {
            item.id = f.getCanonicalPath();
            item.value = new CCJSONParser(f,enc).parser_obj();
            item.lastModified = System.currentTimeMillis();		
            cache().put(item.id,item);
            return item.value;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class CCCacheItem<E> {
        String id ;
        long lastModified ;
        E value;
    }
	

	
}

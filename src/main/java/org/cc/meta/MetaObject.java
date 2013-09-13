/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.meta;

import java.io.File;
import org.cc.CCCache;
import org.cc.CCMap;
import org.cc.ICCMap;
import org.cc.IFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MetaObject extends CCMap {
	
	protected static Logger log = LoggerFactory.getLogger(MetaObject.class);

	
	public MetaObject(String base , String id){
		super();
		init_meta(base,id);
	}
		
	protected void init_meta(String base, String id){
		load_meta(base,id);
		prepare_meta();
	}
	
	protected void load_meta(String base, String id){
		String path = id.replaceAll(".", "/")+".json";
		log.debug(" path : " + path);
		File src = new File(base,path);
		ICCMap m =  CCCache.load_map(null, "UTF-8");
		super.putAll(m.model());
	}

	private void prepare_meta() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	
}

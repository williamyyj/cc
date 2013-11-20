/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.meta;

import java.io.File;
import org.cc.util.CCCache;
import org.cc.ICCMap;

/**
 *
 * @author william
 */
public class ActLoad {
	
	protected ICCMap meta;
	
	public ActLoad(String base, String id){
		File f = new File(base,id+".act");
		meta = CCCache.load_map(f, "UTF-8");
	}
	
}

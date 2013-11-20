/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.meta;

import java.io.File;
import org.cc.util.CCCache;
import org.cc.ICCMap;
import org.cc.util.CCConfig;

/**
 * @author william
 *  處理 Meta actino 類
 * 
 */
public class MetaAction {
	
	protected CCConfig cfg ;
	protected ICCMap act ; 
	protected String id ; 
	
	public MetaAction(CCConfig cfg , String id){
		this.cfg = cfg ; 
	}
	
	public ICCMap process(){
		load_meta();
		return null ; 
	}
	
	public void load_meta(){
		File f = cfg.file("meta_act", id);
		act = CCCache.load_map(f , "UTF-8" );
	}
	
	public void include(String ... params){
		
	}
	
	public void exclude(String ... params){
		
	}
	
	public void mix(ICCMap m){
		
	}
}

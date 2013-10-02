/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.meta;


import org.cc.ICCMap;

/**
 *
 * @author william
 */
public interface ITableMetadata extends ICCMetadata<ICCMap>  {
	public String catalog();
	public String schema();
	public String table();	
	public Object field(ICCMap m, String name);
}

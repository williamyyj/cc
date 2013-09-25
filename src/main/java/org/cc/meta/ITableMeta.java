/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.meta;

import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.type.ICCType;

/**
 *
 * @author william
 */
public interface ITableMeta {
	public String catalog();
	public String schema();
	public String table();
	public ICCMap column(int idx);
	public ICCMap column(String name);
	public int cols_len();
	public ICCList cols();
	public ICCType<?> type(ICCMap column);
	public ICCMap model();
}

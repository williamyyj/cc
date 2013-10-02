/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.meta;

import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.type.ICCType ; 

/**
 *
 * @author william
 */
public interface ICCMetadata<E> {
	public int meta_len();
	public ICCList meta_list();
	public E meta(int idx);
	public E meta(String name);
	public ICCMap model();
	public ICCType type(Object dt) ; 
}

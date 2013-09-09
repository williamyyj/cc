/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.data;

import org.cc.CCException;

/**
 *
 * @author william
 */
public interface ICCDataProcess <SRC,DEST> {
	public DEST process(SRC src, Object ... params) throws CCException ; 
}

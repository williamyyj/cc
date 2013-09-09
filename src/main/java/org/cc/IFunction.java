/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc;

/**
 *
 * @author william
 */
public interface IFunction<E,CTRL> {
   public E exec(CTRL ctrl, Object... args) throws Exception ;
}

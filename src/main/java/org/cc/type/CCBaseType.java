/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CCBaseType<E> implements ICCType<E> {

    protected Logger log = LoggerFactory.getLogger(CCBaseType.class);

    public E value(Object o){
        return value(o,null);
    }
    
    public String json_value(Object value) {
        return String.valueOf(value);
    }

    @Override
    public String sql_value(Object value) {
        return (value!=null) ? String.valueOf(value(value)) : "NULL";
    }

}

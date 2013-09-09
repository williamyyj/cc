package org.cc;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: william
 * Date: 2013/7/2
 * Time: 上午 11:26
 * To change this template use File | Settings | File Templates.
 */
public class CC {

    public static <E> Number num(ICC<E> cc , E id) {
        return num(cc,id,0);
    }

    public static <E> Number num(ICC<E> cc , E id , Number dv) {
        Object o = cc.obj(id);
        return (o instanceof Number) ? (Number) o : dv ;
    }

    public static <E> String str(ICC<E> cc , E id ) {
        return str(cc,id,"");
    }

    public static <E> String str(ICC<E> cc , E id , String dv) {
        Object o = cc.obj(id);
        return (o != null) ? o.toString() : dv ;
    }

    public static <E> boolean bool(ICC<E> cc , E id ) {
        return bool(cc,id,false);
    }

    public static <E> boolean bool(ICC<E> cc , E id , boolean dv) {
        Object o = cc.obj(id);
        return (o instanceof Boolean) ? (Boolean) o : dv ;
    }

    public static <E> Date date(ICC<E> cc , E id ) {
        return date(cc,id,new Date());
    }

    public static <E> Date date(ICC<E> cc , E id , Date dv) {
        Object o = cc.obj(id);
        return (o instanceof Date) ? (Date) o : dv ;
    }
	
    public static <E> int asInt(ICC<E> cc , E id) {
        return asInt(cc,id,0);
    }

    public static <E> int asInt(ICC<E> cc , E id , int dv) {
        Object o = cc.obj(id);
        return (o instanceof Number) ? ((Number)o).intValue() : dv ;
    }
	
	public static <E> long asLong(ICC<E> cc , E id) {
        return asLong(cc,id,0L);
    }

    public static <E> long asLong(ICC<E> cc , E id , long dv) {
        Object o = cc.obj(id);
        return (o instanceof Number) ? ((Number)o).longValue() : dv ;
    }
	
	public static <E> double asDouble(ICC<E> cc , E id) {
        return asDouble(cc,id,0.0);
    }

    public static <E> double asDouble(ICC<E> cc , E id , double dv) {
        Object o = cc.obj(id);
        return (o instanceof Number) ? ((Number)o).doubleValue() : dv ;
    }

}